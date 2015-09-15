package insynctive.controller;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.xml.sax.SAXException;

@Controller
@EnableAutoConfiguration
public class TestController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String runTest() {
		String xmlFileName = "testRun.xml";
		TestNG testNG = new TestNG();

		List<XmlSuite> suite = getSuite(xmlFileName);

		testNG.setXmlSuites(suite);
		testNG.run();

		return "Finish()";
	}

	private List<XmlSuite> getSuite(String xmlFileName) {
		List<XmlSuite> suite = null;
		try {
			suite = (List<XmlSuite>) (new Parser(xmlFileName).parse());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return suite;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(TestController.class, args);
	}
}
