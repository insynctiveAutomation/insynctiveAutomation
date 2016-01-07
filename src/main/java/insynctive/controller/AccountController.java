package insynctive.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import insynctive.dao.AccountDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.exception.ConfigurationException;
import insynctive.model.Account;
import insynctive.model.InsynctiveProperty;

@Controller
@Transactional
public class AccountController {

	//DB Connections.
	private final AccountDao accDao;
	private final InsynctivePropertyDao propertyDao;
	
	@Inject
	public AccountController(AccountDao accDao, InsynctivePropertyDao propertyDao) {
		this.accDao = accDao;
		this.propertyDao = propertyDao;
	}

	@RequestMapping(value = "/configuration/{accID}", method = RequestMethod.GET)
	@ResponseBody
	public Account configuration(@PathVariable("accID") Integer accID){
		return accDao.getAccountByID(accID);
	}
	
	@ResponseBody
	@RequestMapping(value = "/configuration/save", method = RequestMethod.POST)
	public String save(@RequestBody Account formAcc){
		accDao.save(formAcc);
		return "{\"status\" : 200}";
	}

	@RequestMapping(value = "/accountProperties" ,method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Account getAccountProperties() throws ConfigurationException {
		return accDao.getAccountByID(SessionController.account.getAccountID());
	}
	
	@RequestMapping(value = "/saveAccountConfig" ,method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String saveAccountConfig(@RequestBody InsynctiveProperty properties) throws ConfigurationException{
		propertyDao.update(properties);
		return "Done!";
	}
}
