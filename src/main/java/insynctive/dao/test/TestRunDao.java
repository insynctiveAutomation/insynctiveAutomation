package insynctive.dao.test;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.test.run.TestRun;

@Repository
@Transactional
public class TestRunDao {

	private final SessionFactory sessionFactory;
		
	@Inject
	public TestRunDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public TestRun getTestByID(int id){
		return (TestRun) openSession().get(TestRun.class, id);
	}
	
	public Integer save(TestRun TestRun){
		return (Integer) openSession().save(TestRun);
	}
	
	public void saveOrUpdate(TestRun TestRun){
		openSession().saveOrUpdate(TestRun);
	}
	
	public void update(TestRun TestRun) {
		openSession().update(TestRun);
	}
	
}
