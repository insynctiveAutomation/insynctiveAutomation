package insynctive.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import insynctive.exception.ConfigurationException;

@Controller
@Transactional
public class ViewsController {
	
	@RequestMapping(value = "/" ,method = RequestMethod.GET)
	public ModelAndView root(HttpSession session){
		ModelAndView model = new ModelAndView();
		if(SessionController.account != null){
			model.setViewName("/home");
			SessionController.refreshAccount();
		} else {
			model.setViewName("/login");
		}
		return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginGet(HttpSession session){
		ModelAndView model = new ModelAndView();
		model.setViewName("/login");
		return model;
	}
	
	@RequestMapping(value = "/dashboard" ,method = RequestMethod.GET)
	public ModelAndView dashboard() throws ConfigurationException {
		ModelAndView model = new ModelAndView();;
		if(SessionController.account != null){
			model.setViewName("/dashboard");
		} else {
			model.setViewName("/login");
		}
		return model;
	}
	
	@RequestMapping(value = "/configuration", method = RequestMethod.GET)
	public ModelAndView configuration(HttpSession session){
		ModelAndView model = new ModelAndView();
		model.setViewName("/advancedConfiguration");
		return model;
	}

	@RequestMapping(value = "/gender_template", method = RequestMethod.GET)
	public ModelAndView getGender() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/template/gender");
		return model;
	}
	
	@RequestMapping(value = "/marital_status", method = RequestMethod.GET)
	public ModelAndView getMaritalStatus() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/template/maritalStatus");
		return model;
	}
	
	@RequestMapping(value = "/us_address", method = RequestMethod.GET)
	public ModelAndView getUsAddress() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/template/us_address");
		return model;
	}
	
	@RequestMapping(value = "/benefit_company" ,method = RequestMethod.GET)
	public ModelAndView modelBenefitCompany() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("/template/benefit_company");
		return model;
	}
	
	@RequestMapping(value = "/yes_no" ,method = RequestMethod.GET)
	public ModelAndView getYesNo() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("/template/yes_no");
		return model;
	}
	
	@RequestMapping(value = "/main" ,method = RequestMethod.GET)
	public ModelAndView main(){
		ModelAndView model = new ModelAndView();
		model.setViewName("/main");
		return model;
	}
	
	@RequestMapping(value = "/accountConfigContent" ,method = RequestMethod.GET)
	public ModelAndView goAccountConfigModel() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("/accountConfigModel");
		return model;
	}
	
	@RequestMapping(value = "/editParameters" ,method = RequestMethod.GET)
	public ModelAndView modelParameters() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("/parametersModel");
		return model;
	}
	
	@RequestMapping(value = "/viewParameters" ,method = RequestMethod.GET)
	public ModelAndView viewParameters() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("/parametersNoEditable");
		return model;
	}

	@RequestMapping(value = "/testSuiteHome" ,method = RequestMethod.GET)
	public ModelAndView testSuite() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("/template/testSuite");
		return model;
	}
	
	@RequestMapping(value = "/test" ,method = RequestMethod.GET)
	public ModelAndView test() throws Exception{
		ModelAndView model = new ModelAndView();
		model.setViewName("/test/test");
		return model;
	}
	
	@RequestMapping(value = "/testSuite" ,method = RequestMethod.GET)
	public ModelAndView testSuiteView() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("/test/testSuite");
		return model;
	}
	
	@RequestMapping(value = "/testPlan" ,method = RequestMethod.GET)
	public ModelAndView testPlanView() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("/test/testPlan");
		return model;
	}
	
	@RequestMapping(value = "/testTemplate" ,method = RequestMethod.GET)
	public ModelAndView testTemplateView() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("/test/template/testTemplate");
		return model;
	}
	
	@RequestMapping(value = "/testSuiteTemplate" ,method = RequestMethod.GET)
	public ModelAndView testSuiteTemplateView() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("/test/template/testSuiteTemplate");
		return model;
	}
	
	@RequestMapping(value = "/testPlanTemplate" ,method = RequestMethod.GET)
	public ModelAndView testPlanTemplateView() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("/test/template/testPlanTemplate");
		return model;
	}

	@RequestMapping(value = "/testRunTemplate" ,method = RequestMethod.GET)
	public ModelAndView testRunTemplate() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("/template/testRunTemplate");
		return model;
	}
	
}
