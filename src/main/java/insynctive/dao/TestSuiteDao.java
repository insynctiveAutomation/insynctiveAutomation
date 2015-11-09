package insynctive.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.Test;
import insynctive.model.TestSuite;

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
	
	public TestSuite getTestSuiteByID(int id){
		return (TestSuite) openSession().get(TestSuite.class, id);
	}
	
	public Integer save(TestSuite testSuite){
		return (Integer) openSession().save(testSuite);
	}
	
	public void saveOrUpdate(TestSuite testSuite){
		openSession().saveOrUpdate(testSuite);
	}

	public List<TestSuite> getAllTestSuite() {
		return openSession().createCriteria(TestSuite.class).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
	}

	public void merge(TestSuite testSuite) {
		openSession().merge(testSuite);
	}
	
	public void replicate(TestSuite testSuite){
		openSession().replicate(testSuite, ReplicationMode.EXCEPTION);
	}
	
	public void evict(TestSuite testSuite){
		openSession().evict(testSuite);
	}
	
}
