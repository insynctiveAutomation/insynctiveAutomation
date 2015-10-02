package insynctive.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.InsynctiveProperty;

@Repository
@Transactional
public class InsynctivePropertyDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public InsynctiveProperty getPropertybyID(int id){
		return (InsynctiveProperty) openSession().get(InsynctiveProperty.class, id);
	}

	public void update(InsynctiveProperty properties) {
		openSession().update(properties);
	}
}
