package insynctive.dao.test;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.test.run.TestSuiteRun;

@Repository
@Transactional
public class TestSuiteRunDao {

private final SessionFactory sessionFactory;
	
	@Inject
	public TestSuiteRunDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public TestSuiteRun getTestSuiteRunByID(int id){
		return (TestSuiteRun) openSession().get(TestSuiteRun.class, id);
	}
	
	public Integer save(TestSuiteRun testSuite){
		return (Integer) openSession().save(testSuite);
	}
	
	public void saveOrUpdate(TestSuiteRun testSuite){
		openSession().saveOrUpdate(testSuite);
	}

	public List<TestSuiteRun> getAllTestSuiteRuns() {
		return openSession().createCriteria(TestSuiteRun.class)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.addOrder(Order.desc("testSuiteRunID"))
				.list();
	}

	public List<TestSuiteRun> getTestSuiteRuns(Integer page, Integer count) {
		return openSession().createCriteria(TestSuiteRun.class)
				.setMaxResults(count)
				.setFirstResult((page-1)*count)
				.addOrder(Order.desc("testSuiteRunID"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.setFetchMode("tests", FetchMode.SELECT)
				.list();
	}
	
	public TestSuiteRun getTestSuiteRunByName(String name){
		List list = openSession().createCriteria(TestSuiteRun.class)
				.add(Restrictions.eq("name", name)).list();
		return list.size() > 0 ? (TestSuiteRun)list.get(0) : null;
	}

	public Long countTestSuitesRuns() {
		Criteria criteriaCount = openSession().createCriteria(TestSuiteRun.class);
		criteriaCount.setProjection(Projections.rowCount());
		return (Long) criteriaCount.uniqueResult();
	}

	public void merge(TestSuiteRun testSuite) {
		openSession().merge(testSuite);
	}
	
	public void replicate(TestSuiteRun testSuite){
		openSession().replicate(testSuite, ReplicationMode.EXCEPTION);
	}
	
	public void evict(TestSuiteRun testSuite){
		openSession().evict(testSuite);
	}
	
}
