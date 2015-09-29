package insynctive.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.InsynctiveProperty;

@Repository
@Transactional
public class InsynctivePropertyDao {

	private final SessionFactory sessionFactory;
	
	@Inject
	public InsynctivePropertyDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public InsynctiveProperty getPropertybyID(int id){
		return (InsynctiveProperty) openSession().get(InsynctiveProperty.class, id);
	}
}
