package insynctive.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.Account;
import insynctive.model.CrossBrowserAccount;

@Repository
@Transactional
public class CrossBrowserAccountDao {

	private final SessionFactory sessionFactory;
	
	@Inject
	public CrossBrowserAccountDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Account getAccountByID(int id){
		return (Account) openSession().get(CrossBrowserAccount.class, id);
	}
}
