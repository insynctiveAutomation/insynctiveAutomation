package insynctive.dao.test;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.test.Test;

@Repository
@Transactional
public class TestDao {

	private final SessionFactory sessionFactory;
	
	@Inject
	public TestDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Test getTestByID(int id){
		return (Test) openSession().get(Test.class, id);
	}
	
	public Integer save(Test test){
		return (Integer) openSession().save(test);
	}
	
	public void saveOrUpdate(Test test){
		openSession().saveOrUpdate(test);
	}

	public void update(Test test) {
		openSession().update(test);
	}
}
 