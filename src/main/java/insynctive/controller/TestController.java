package insynctive.controller;

import insynctive.exception.ConfigurationException;
import insynctive.results.Result;
import insynctive.results.TestResults;
import insynctive.results.TestSuite;
import insynctive.utils.reader.InsynctivePropertiesReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.xml.sax.SAXException;

@Controller
@EnableAutoConfiguration
@Scope("session")
public class TestController {

	TestListenerAdapter tla = new TestListenerAdapter();
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(TestController.class, args);
	}

	@RequestMapping(value = "/" ,method = RequestMethod.GET)
	public ModelAndView root(){
		ModelAndView model = new ModelAndView();
		model.setViewName("test.html");
		return model;
	}
	
	@RequestMapping(value = "/accountConfigContent" ,method = RequestMethod.GET)
	public ModelAndView goAccountConfigModel() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("accountConfigModel.html");
		return model;
	}

	@RequestMapping(value = "/model/{model}" ,method = RequestMethod.GET)
	public ModelAndView goModel(@PathVariable("model") String modelName) throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName(modelName+".html");
		return model;
	}

	@RequestMapping(value = "/accountProperties" ,method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public InsynctivePropertiesReader getAccountProperties() throws ConfigurationException {
		return InsynctivePropertiesReader.getAllAccountsProperties();
	}

	@RequestMapping(value = "/clearTest" ,method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public void clearTestResult() throws ConfigurationException {
		tla = new TestListenerAdapter();
	}

	@RequestMapping(value = "/video" ,method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getVideo() throws InterruptedException, ConfigurationException{
		if (InsynctivePropertiesReader.IsRemote()) {
			int times = 1;
			int sleep = 2000;
			
			while (times <= 30) {
				String videoLink = insynctive.utils.TestResults.video;
				if (videoLink != null) {
					return videoLink;
				}
				Thread.sleep(sleep);
				times++;
			}
			return "";//TimeOut
		} else {
			return "";//Local Test
		}
	}
	
	@RequestMapping(value = "/testsSuites" ,method = RequestMethod.GET)
	@ResponseBody
	public List<String> getTestsSuites(){
		List<String> testsSuites = getTestSuites();
		return testsSuites;
	}
	
	@RequestMapping(value = "/environments" ,method = RequestMethod.GET)
	@ResponseBody
	public List<String> getEnvironments(){
		List<String> environments = new ArrayList<String>();
		
		return environments;
	}
	
	@RequestMapping(value = "/status" ,method = RequestMethod.GET)
	@ResponseBody
	public TestResults getStatus(){
		List<Result> resultsAux = new ArrayList<Result>();
		TestResults testResults = new TestResults();
		
		for(ITestResult testResult : tla.getPassedTests()){
			resultsAux.add(new Result(testResult.getName(),"SUCCESS"));
		}
		testResults.setPassedTests(resultsAux);
		resultsAux = new ArrayList<Result>();
		
		for(ITestResult testResult : tla.getFailedTests()){
			resultsAux.add(new Result(testResult.getName(),"FAILED"));
		}
		testResults.setFailedTests(resultsAux);
		resultsAux = new ArrayList<Result>();
		
		for(ITestResult testResult : tla.getSkippedTests()){
			resultsAux.add(new Result(testResult.getName(),"SKIPPED"));
		}
		testResults.setSkipedTests(resultsAux);
		resultsAux = new ArrayList<Result>();
		
		return testResults;
	}
	
	@RequestMapping(value = "/get/{xmlName}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestSuite getTestsRuns(@PathVariable("xmlName") String xmlName) {
		TestSuite testSuite = null;
		try{
			List<XmlSuite> suite = getXmlTestSuite(xmlName);
			testSuite = new TestSuite();
			
			for(XmlTest test : suite.get(0).getTests()){
				testSuite.setTestName(test.getName());
				for(XmlClass classes : test.getClasses()){
					testSuite.setClassName(classes.getName());
					for(XmlInclude includeMethods: classes.getIncludedMethods()){
						testSuite.addMethod(new Result(includeMethods.getName(), "-"));
					}
				}
			}
		} catch(Exception ex) {
			
		}
		return testSuite;
	}
	
	@RequestMapping(value = "/test/{xmlName}/{environment}" ,method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String runTest(@PathVariable("xmlName") String xmlName, @PathVariable("environment") String environment) throws ConfigurationException{
		InsynctivePropertiesReader allAccountsProperties = InsynctivePropertiesReader.getAllAccountsProperties();
		allAccountsProperties.setEnvironment(environment);
		
		tla = new TestListenerAdapter();
		insynctive.utils.TestResults.resetResults();
		
		List<XmlSuite> suite = getXmlTestSuite(xmlName);
		
		TestNG testNG = new TestNG();
		
		testNG.setXmlSuites(suite);
		testNG.setPreserveOrder(true);
		testNG.addListener(tla);
		testNG.run();
		
		return "Finish!";
	}
	
	@RequestMapping(value = "/test/{testName}" ,method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getTest(@PathVariable("testName") String testName) throws ConfigurationException{
		tla.getPassedTests().forEach(failTest -> System.out.println(failTest));
		tla.getSkippedTests().forEach(failTest -> System.out.println(failTest));
		tla.getFailedTests().forEach(failTest -> System.out.println(failTest));
		tla.getTestContexts().forEach(failTest -> System.out.println(failTest));
		return null;
	}
	
	@RequestMapping(value = "/saveAccountConfig" ,method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String saveAccountConfig(@RequestBody InsynctivePropertiesReader properties) throws ConfigurationException{
		properties.saveAccConfig();
		
		return "Done!";
	}
	
	/*Private Methods*/
	private List<XmlSuite> getXmlTestSuite(String xmlName) {
		ClassLoader classLoader = getClass().getClassLoader();
		String xmlFileName = classLoader.getResource("testsSuits/"+xmlName+".xml").getPath();
		List<XmlSuite> suite = getSuite(xmlFileName);
		
		return suite;
	}

	private List<XmlSuite> getSuite(String xmlFileName) {
		List<XmlSuite> suite = null;
		try
		{
			suite = (List <XmlSuite>)(new Parser(xmlFileName).parse());
		}
		catch (ParserConfigurationException e)
		{
		    e.printStackTrace();
		}
		catch (SAXException e)
		{
		    e.printStackTrace();
		}
		catch (IOException e)
		{
		    e.printStackTrace();
		}
		return suite;
	}
	
	private List<String> getTestSuites(){
		List<String> results = new ArrayList<String>();
		ClassLoader classLoader = getClass().getClassLoader();
		
		File[] files = new File(classLoader.getResource("testsSuits").getFile()).listFiles();

		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		return results;
	}
}
