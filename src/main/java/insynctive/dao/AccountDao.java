package insynctive.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.Account;

@Repository
@Transactional
public class AccountDao {

	private final SessionFactory sessionFactory;
	
	@Inject
	public AccountDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Account getAccountByID(int id){
		return (Account) openSession().get(Account.class, id);
	}
	
	public int incrementRunID(int accID){
		Account acc = (Account) openSession().get(Account.class, accID);
		int newID = acc.getRunID()+1;
		acc.setRunID(newID);
		openSession().update(acc);
		return newID;
	}
	
	public Account incrementRunIDAndGetAcc(int accID){
		Account acc = (Account) openSession().get(Account.class, accID);
		int newID = acc.getRunID()+1;
		acc.setRunID(newID);
		openSession().update(acc);
		return acc;
	}
	
	public Account getAccountLogin(String username, String password){
		Criteria criteria = openSession().createCriteria(Account.class)
				.add(Restrictions.eq("username", username).ignoreCase())
				.add(Restrictions.eq("password", password));
		
		List<Account> accounts = criteria.list();
		Account acc;
		if(accounts.size() > 0){
			acc = accounts.get(0);
		} else {
			return null;
		}
		
		return acc;
	}
	
	
}
