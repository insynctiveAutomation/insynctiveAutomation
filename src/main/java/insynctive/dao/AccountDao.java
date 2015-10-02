package insynctive.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
}
