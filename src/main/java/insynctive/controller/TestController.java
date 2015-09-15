package insynctive.controller;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.testng.ISuite;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.xml.sax.SAXException;

@Controller
public class TestController {

	@RequestMapping(value = "/test" ,method = RequestMethod.POST)
	@ResponseBody
	public void runTest(){
		String xmlFileName = "testRun.xml";
		List<XmlSuite> suite;
		try
		{
		    suite = (List <XmlSuite>)(new Parser(xmlFileName).parse());
			TestNG testNG = new TestNG();
			testNG.setXmlSuites(suite);
			testNG.run();
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
	
		TestNG testNG = new TestNG();
		List<ISuite> runSuitesLocally = testNG.runSuitesLocally();
		System.out.println(runSuitesLocally);
	}
}
