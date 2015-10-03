package insynctive.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.PersonData;

@Repository
@Transactional
public class PersonDataDao {

private final SessionFactory sessionFactory;
	
	@Inject
	public PersonDataDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public PersonData getAccountByID(int id){
		return (PersonData) openSession().get(PersonData.class, id);
	}
	
	
}
