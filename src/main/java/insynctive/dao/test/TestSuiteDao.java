package insynctive.dao.test;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.test.TestSuite;

@Repository
@Transactional
public class TestSuiteDao {

	private final SessionFactory sessionFactory;
	
	@Inject
	public TestSuiteDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public TestSuite getTestByID(int id){
		return (TestSuite) openSession().get(TestSuite.class, id);
	}
	
	public Integer save(TestSuite TestSuite){
		return (Integer) openSession().save(TestSuite);
	}
	
	public void saveOrUpdate(TestSuite TestSuite){
		openSession().saveOrUpdate(TestSuite);
	}

	public void update(TestSuite TestSuite) {
		openSession().update(TestSuite);
	}
}
