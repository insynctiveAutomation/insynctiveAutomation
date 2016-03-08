package insynctive.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import insynctive.dao.AccountDao;
import insynctive.utils.form.LoginForm;

@Controller
@Transactional
public class LoginController {
	
	//DB Connections.
	private final AccountDao accDao;

	@Inject
	public LoginController(AccountDao accDao) {
		this.accDao = accDao;
	}
	
	@RequestMapping(value = "/login" ,method = RequestMethod.POST)
	@ResponseBody
	public String loginPost(@RequestBody LoginForm form) throws Exception{
		SessionController.account = accDao.getAccountLogin(form.getUsername(), form.getPassword());
		if(SessionController.account != null){
			return "{\"accID\" : \""+(SessionController.account.getAccountID())+"\"}";
		}
		throw new Exception("Account not found");
	}

	@RequestMapping(value = "/logout" ,method = RequestMethod.POST)
	public ModelAndView logout() throws Exception{
		SessionController.account = null;
		ModelAndView model = new ModelAndView();
		model.setViewName("/login");
		return model;
	}
}
