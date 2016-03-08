package insynctive.controller;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.reflections.Reflections;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import insynctive.annotation.ParametersFront;
import insynctive.dao.AccountDao;
import insynctive.dao.CreatePersonFormDao;
import insynctive.dao.test.TestDao;
import insynctive.dao.test.TestPlanDao;
import insynctive.dao.test.TestPlanRunDao;
import insynctive.dao.test.TestRunDao;
import insynctive.dao.test.TestSuiteDao;
import insynctive.dao.test.TestSuiteRunDao;
import insynctive.exception.ConfigurationException;
import insynctive.model.Account;
import insynctive.model.CreatePersonForm;
import insynctive.model.test.Test;
import insynctive.model.test.TestPlan;
import insynctive.model.test.TestSuite;
import insynctive.model.test.TestSuiteRunner;
import insynctive.model.test.run.TestPlanRun;
import insynctive.model.test.run.TestRun;
import insynctive.model.test.run.TestSuiteRun;
import insynctive.results.TestResult;
import insynctive.results.TestResultsTestNG;
import insynctive.tests.TestMachine;
import insynctive.utils.ParamObjectField;
import insynctive.utils.ParametersFrontObject;
import insynctive.utils.TestResults;
import insynctive.utils.TestWebRunner;

@Controller
@Transactional
public class TestController {
 
//	How to return error codes
//	return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	
	private final String TEST_PACKAGE = "insynctive.tests";
	
	//DB Connections.
	private final AccountDao accDao;
	
	private final TestDao testDao;
	private final TestSuiteDao testSuiteDao;
	private final TestPlanDao testPlanDao;
	
	private final TestRunDao testRunDao;
	private final TestSuiteRunDao testSuiteRunDao;
	private final TestPlanRunDao testPlanRunDao;
	
	private final CreatePersonFormDao createPersonFormDao;

	//Test Runner
	private final TestWebRunner testRunner;
	
	@Inject
	public TestController(TestDao testDao, TestSuiteDao testSuiteDao, TestPlanDao testPlanDao, 
			TestRunDao testRunDao, TestSuiteRunDao testSuiteRunDao, TestPlanRunDao testPlanRunDao, 
			 ServletContext servletContext, AccountDao accDao, CreatePersonFormDao createPersonFormDao) {

		this.testDao = testDao;
		this.testSuiteDao = testSuiteDao;
		this.testPlanDao = testPlanDao;
		
		this.testRunDao = testRunDao;
		this.testSuiteRunDao = testSuiteRunDao;
		this.testPlanRunDao = testPlanRunDao;
		
		this.accDao = accDao;
		this.createPersonFormDao = createPersonFormDao;
		this.testRunner = new TestWebRunner();
	}
	
	@RequestMapping(value = "/test/{testID}" ,method = RequestMethod.GET)
	@ResponseBody
	public Test getTest(@PathVariable("testID") Integer testID) throws Exception{
		Test test = testDao.getTestByID(testID);
		return test;
	}
	
	@RequestMapping(value = "/test" ,method = RequestMethod.POST)
	@ResponseBody
	public Test saveTest(@RequestBody Test test) throws Exception{
		testDao.saveOrUpdate(test);
		return test;
	}
	
	@RequestMapping(value = "/testSuite/{testSuiteID}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestSuite getTestSuite(@PathVariable Integer testSuiteID) throws ConfigurationException {
		TestSuite testByID = testSuiteDao.getTestByID(testSuiteID);
		return testByID;
	}
	
	@RequestMapping(value = "/testSuite" ,method = RequestMethod.POST)
	@ResponseBody
	public TestSuite saveTestSuite(@RequestBody TestSuite testSuite) throws Exception{
		testSuiteDao.saveOrUpdate(testSuite);
		return testSuite;
	}
	
	@RequestMapping(value = "/testPlan/{testPlanID}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestPlan getTestPlan(@PathVariable Integer testPlanID) throws ConfigurationException {
		TestPlan testPlanByID = testPlanDao.getTestPlanByID(testPlanID);
		return testPlanByID;
	}
	
	@RequestMapping(value = "/testPlan" ,method = RequestMethod.POST)
	@ResponseBody
	public TestPlan saveTestPlan(@RequestBody TestPlan testPlan) throws Exception{
		testPlanDao.saveOrUpdate(testPlan);
		return testPlan;
	} 
	
	@RequestMapping(value = "/remove/testPlan" ,method = RequestMethod.POST)
	@ResponseBody
	public String removeTestPlan(@RequestBody TestPlan testPlan) throws Exception{
		testPlanDao.remove(testPlan);
		return "{\"statut\" : 200}";
	}
	
	@RequestMapping(value = "/testRun/{testRunID}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestRun getTestRun(@PathVariable("testRunID") Integer testRunID) {
		return testRunDao.getTestRunByID(testRunID);
	}
	
	@RequestMapping(value = "/testRun" ,method = RequestMethod.POST)
	@ResponseBody
	public String saveTestRun(@RequestBody TestRun testRun) throws Exception{
		testRunDao.saveOrUpdate(testRun);
		return "{\"statut\" : 200}";
	} 
	
	@RequestMapping(value = "/testSuiteRun/{testSuiteRunID}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestSuiteRun getTestSuiteRun(@PathVariable("testSuiteRunID") Integer testSuiteRunID) {
		return testSuiteRunDao.getTestSuiteRunByID(testSuiteRunID);
	}
	
	@RequestMapping(value = "/testSuiteRun" ,method = RequestMethod.POST)
	@ResponseBody
	public String saveTestSuiteRun(@RequestBody TestSuiteRun testSuiteRun) throws Exception{
		testSuiteRunDao.saveOrUpdate(testSuiteRun);
		return "{\"statut\" : 200}";
	} 
	
	@RequestMapping(value = "/testPlanRun/{testPlanRunID}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestPlanRun getTestPlanRun(@PathVariable("testPlanRunID") Integer testPlanRunID) {
		return testPlanRunDao.getTestPlanRunByID(testPlanRunID);
	}
	
	@RequestMapping(value = "/testPlanRun" ,method = RequestMethod.POST)
	@ResponseBody
	public String saveTestSuiteRun(@RequestBody TestPlanRun testPlanRun) throws Exception{
		testPlanRunDao.saveOrUpdate(testPlanRun);
		return "{\"statut\" : 200}";
	}
	
	@RequestMapping(value = "/tests" ,method = RequestMethod.GET)
	@ResponseBody
	public List<Test> getAllTests() throws MalformedURLException, URISyntaxException{
		return testDao.getAllTestPlans();
	}
	
	@RequestMapping(value = "/testSuites" ,method = RequestMethod.GET)
	@ResponseBody
	public List<TestSuite> getAllTestSuites() throws MalformedURLException, URISyntaxException{
		return testSuiteDao.getAllTestSuite();
	}
	
	@RequestMapping(value = "/testPlans" ,method = RequestMethod.GET)
	@ResponseBody
	public List<TestPlan> getAllTestsPlans() throws MalformedURLException, URISyntaxException{
		return testPlanDao.getAllTestPlans();
	}
	
	
	@RequestMapping(value = "/testSuitesNames" ,method = RequestMethod.GET)
	@ResponseBody
	public Object[] getAllTestsSutiesName() throws MalformedURLException, URISyntaxException{
		List<TestSuite> allTestSuite = testSuiteDao.getAllTestSuite();
		return allTestSuite.stream().map(ts -> ts.getTestSuiteName()).toArray();
	}
	
	@RequestMapping(value = "/parameter/{className}/{testName}" ,method = RequestMethod.GET)
	@ResponseBody
	public ParametersFrontObject modelParameters(@PathVariable("className") String className, @PathVariable("testName") String testName) throws ConfigurationException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		return getParams(className, testName);
	}

	@RequestMapping(value = "/video/{testListenenerIndex}" ,method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getVideo(@PathVariable("testListenenerIndex") Integer testListenenerIndex) throws InterruptedException, ConfigurationException{
		if (SessionController.account != null && SessionController.account.getAccountProperty().isRemote()) {
			int times = 1;
			int sleep = 2000;
			
			while (times <= 30) {
				String videoLink = insynctive.utils.TestResults.getVideo(testListenenerIndex);
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
	
	@RequestMapping(value = "/status/{index}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestResultsTestNG getStatus(@PathVariable("index") Integer index) throws InterruptedException{ 
		Map<Integer, TestListenerAdapter> tla = TestResults.listeners;
		TestListenerAdapter assignedListener = tla.get(index);
		
		List<TestResult> resultsAux = new ArrayList<TestResult>();
		TestResultsTestNG testResults = new TestResultsTestNG();
		
		for(ITestResult testResult : assignedListener.getPassedTests()){
			resultsAux.add(new TestResult(testResult.getName()));
		}
		testResults.setPassedTests(resultsAux);
		
		resultsAux = new ArrayList<TestResult>();
		for(ITestResult testResult : assignedListener.getFailedTests()){
			String airbrakeLink = TestResults.getAirbrakeLinkOrDefault(index.toString(), testResult.getName(), "FAILURE");
			resultsAux.add(new TestResult(testResult.getName(), airbrakeLink));
		}
		testResults.setFailedTests(resultsAux);

		resultsAux = new ArrayList<TestResult>();
		for(ITestResult testResult : assignedListener.getSkippedTests()){
			resultsAux.add(new TestResult(testResult.getName()));
		}
		testResults.setSkipedTests(resultsAux);
		
		return testResults;
	}
	
	@RequestMapping(value = "/get/{xmlName}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestSuite getTestsRuns(@PathVariable("xmlName") String name) {
		TestSuite testSuite = testSuiteDao.getTestSuiteByName(name);
		
		return testSuite;
	}
	
	@RequestMapping(value = "/countTestSuites" ,method = RequestMethod.GET)
	@ResponseBody
	public Long getTestsSuites() {
		return testSuiteRunDao.countTestSuitesRuns();
	}
	
	@RequestMapping(value = "/getTestsSuites/{page}/{count}" ,method = RequestMethod.GET)
	@ResponseBody
	public List<TestSuiteRun> getTestsSuites(@PathVariable("page") Integer page, @PathVariable("count") Integer count) {
		return testSuiteRunDao.getTestSuiteRuns(page, count);
	}
	
	@RequestMapping(value = "/getAllTestsSuites" ,method = RequestMethod.GET)
	@ResponseBody
	public List<TestSuiteRun> getAllTestsSuites() {
		return testSuiteRunDao.getAllTestSuiteRuns();
	}
	
	@RequestMapping(value = "/run/testPlan/{tpID}/{isNotification}/{remote}", method = RequestMethod.POST)
	@ResponseBody
	public String runTestPlanByID(@PathVariable("tpID") Integer tpID, @PathVariable("isNotification") Boolean isNotification, @PathVariable("remote") Boolean isRemote) throws Exception{
		
		TestPlan tp = testPlanDao.getTestPlanByID(tpID);
		testRunner.runTest(tp, isNotification, isRemote, Account.getAccountUsername(SessionController.account));
		
		return "{\"status\" : 200}";
	}
	
	@RequestMapping(value = "/run/name/testPlan/{tpName}/{isNotification}/{remote}", method = RequestMethod.POST)
	@ResponseBody
	public String runTestPlanByName(@PathVariable("tpName") String name, @PathVariable("isNotification") Boolean isNotification, @PathVariable("remote") Boolean isRemote) throws Exception{
		
		TestPlan tp = testPlanDao.getTestPlanByName(name);
		testRunner.runTest(tp, isNotification, isRemote, Account.getAccountUsername(SessionController.account));
		
		return "{\"status\" : 200}";
	}
	
	@RequestMapping(value = "/run/testSuite/{tsID}/{environment}/{browser}/{isNotification}/{remote}", method = RequestMethod.POST)
	@ResponseBody
	public String runTestSuiteByID(@PathVariable("tsID") Integer tsID, @PathVariable("environment") String environment, @PathVariable("browser") String browser, @PathVariable("isNotification") Boolean isNotification, @PathVariable("remote") Boolean isRemote) throws Exception{
		
		TestSuite ts = testSuiteDao.getTestByID(tsID);

		TestSuiteRunner tsRunner = new TestSuiteRunner(ts, environment, browser);
		testRunner.runTest(tsRunner, isNotification, isRemote, Account.getAccountUsername(SessionController.account));
		
		return "{\"status\" : 200}";
	}
	
	@RequestMapping(value = "/run/name/testSuite/{tsName}/{environment}/{browser}/{isNotification}/{remote}", method = RequestMethod.POST)
	@ResponseBody
	public String runTestSuiteByName(@PathVariable("tsName") String tsName, @PathVariable("environment") String environment, @PathVariable("browser") String browser, @PathVariable("isNotification") Boolean isNotification, @PathVariable("remote") Boolean isRemote) throws Exception{
		
		TestSuite ts = testSuiteDao.getTestSuiteByName(tsName);

		TestSuiteRunner tsRunner = new TestSuiteRunner(ts, environment, browser);
		testRunner.runTest(tsRunner, isNotification, isRemote, Account.getAccountUsername(SessionController.account));
		
		return "{\"status\" : 200}";
	}
	
	@RequestMapping(value = "/run/testSuite/{environment}/{browser}/{isNotification}/{remote}", method = RequestMethod.POST)
	@ResponseBody
	public String runTestSuite(@RequestBody TestSuite ts, @PathVariable("environment") String environment, @PathVariable("browser") String browser, @PathVariable("isNotification") Boolean isNotification, @PathVariable("remote") Boolean isRemote) throws Exception{
		
		TestSuiteRunner tsRunner = new TestSuiteRunner(ts, environment, browser);
		
		return "{\"index\" : \""+(testRunner.runTest(tsRunner, isNotification, isRemote, Account.getAccountUsername(SessionController.account)))+"\"}";
	}
	
	@RequestMapping(value = "/retry/{ID}", method = RequestMethod.GET)
	@ResponseBody
	public String retry(@PathVariable("ID") Integer testSuiteID) throws Exception {
		
		return "{\"error\" : \"Do not working\"}";
	}
	
	@RequestMapping(value = "/test/{testName}/{index}" ,method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getTest(@PathVariable("testName") String testName, @PathVariable("index") Integer index) throws ConfigurationException{
		Map<Integer, TestListenerAdapter> tla = TestResults.listeners;
		
		tla.get(index).getPassedTests().forEach(failTest -> System.out.println(failTest));
		tla.get(index).getSkippedTests().forEach(failTest -> System.out.println(failTest));
		tla.get(index).getFailedTests().forEach(failTest -> System.out.println(failTest));
		tla.get(index).getTestContexts().forEach(failTest -> System.out.println(failTest));
		return null;
	}
	
	@RequestMapping(value = "/start/OE" ,method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String startCreatePersonOpenEnrollment(@RequestBody CreatePersonForm form) throws Exception {
		Integer newPersonID = createPersonFormDao.saveCreatePersonForm(form);
		
		String tester = "External User";
		Boolean isRemote = true;

		TestPlan tp = testPlanDao.getTestPlanByName("Create Person - Open Enrollment");
		
		for(TestSuiteRunner tsRunner : tp.getTestSuiteRunners()){
			testRunner.runExternalCreatePerson(tsRunner.run(isRemote, tester), newPersonID);
		}
		
		return "{\"status\" : 200}";
	}
	
	@RequestMapping(value = "/start/OB" ,method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String startCreatePersonOnBoarding(@RequestBody CreatePersonForm form) throws Exception{
		Integer newPersonID = createPersonFormDao.saveCreatePersonForm(form);
		
		String tester = "External User";
		Boolean isRemote = true;

		TestPlan tp = testPlanDao.getTestPlanByName("Create Person - On Boarding");
		
		for(TestSuiteRunner tsRunner : tp.getTestSuiteRunners()){
			testRunner.runExternalCreatePerson(tsRunner.run(isRemote, tester), newPersonID);
		}
		
		return "{\"status\" : 200}";
	}

	@RequestMapping(value = "/clearTest/{index}" ,method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public void clearTestResult(@PathVariable("index") Integer index) throws ConfigurationException, InterruptedException {
		TestResults.removeListener(index);
	}

	@RequestMapping(value = "/testclasses" ,method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object[] getClasses() throws ConfigurationException, ClassNotFoundException, IOException {
		  Reflections reflections = new Reflections(TEST_PACKAGE);
		  Set<Class<? extends TestMachine>> testsClasses =  reflections.getSubTypesOf(TestMachine.class);
		  return testsClasses.stream().map(test -> TEST_PACKAGE+"."+test.getSimpleName()).toArray();
		  
	}

	@RequestMapping(value = "/view/test" ,method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object[] getTestsInClass(@RequestParam("className") String className) throws ConfigurationException, ClassNotFoundException, IOException {
		Object[] testsMethods = null;  
		Reflections reflections = new Reflections(TEST_PACKAGE);
		Set<Class<? extends TestMachine>> testsClasses = reflections.getSubTypesOf(TestMachine.class);
		for (Class<? extends TestMachine> testClass : testsClasses) {
			String[] split = className.split("\\.");
			if (testClass.getSimpleName().equals(split[split.length-1])) {
				testsMethods = Arrays.asList(testClass.getDeclaredMethods()).stream().filter(meth -> meth.isAnnotationPresent(org.testng.annotations.Test.class)).map(meth -> meth.getName()).toArray();
			}
		}
		return testsMethods;
	}
	
	private String checkStatusOfMethodInTLA(Integer tlaIndex, String nameOfTest) throws Exception {
		List<ITestResult> passedTests = TestResults.listeners.get(tlaIndex).getPassedTests();
		for(ITestResult test : passedTests){
			if(test.getMethod().getMethodName().equals("createPersonTest")){
				return "{\"status\": true}";
			}
		}
		List<ITestResult> failedTests = TestResults.listeners.get(tlaIndex).getFailedTests();
		for(ITestResult test : failedTests){
			if(test.getMethod().getMethodName().equals("createPersonTest")){
				throw new Exception("The Methods Create Job Fails");
			}
		}
		return "{\"status\": 200}";
	}
	
	private ParametersFrontObject getParams(String className, String testName) throws ClassNotFoundException, NoSuchMethodException, SecurityException{
		Class<?> aClass = Class.forName(className);
		Method testMethod = aClass.getMethod(testName, Integer.class);
		Annotation[] annotations = testMethod.getAnnotationsByType(ParametersFront.class);
		List<ParamObjectField> params = new ArrayList<>();
		List<String> labels = new ArrayList<>();;
		ParametersFrontObject parametersFrontObject = new ParametersFrontObject();
		
		for(Annotation annotation : annotations){
		    	ParametersFront parameters = (ParametersFront) annotation;
		    	
		    	Collections.addAll(params, parameters.attrs()); 
		    	Collections.addAll(labels, parameters.labels()); 
		    	
		    	parametersFrontObject.setParams(params.stream().map(param -> param.getValue()).collect(Collectors.toList()));
		    	parametersFrontObject.setLabels(labels);
		}
		return parametersFrontObject;
	}

}
