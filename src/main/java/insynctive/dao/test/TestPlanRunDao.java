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

import insynctive.model.test.run.TestPlanRun;

@Repository
@Transactional
public class TestPlanRunDao {

private final SessionFactory sessionFactory;
	
	@Inject
	public TestPlanRunDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public TestPlanRun getTestPlanRunByID(int id){
		return (TestPlanRun) openSession().get(TestPlanRun.class, id);
	}
	
	public Integer save(TestPlanRun testPlanRun){
		return (Integer) openSession().save(testPlanRun);
	}
	
	public void saveOrUpdate(TestPlanRun testPlanRun){
		openSession().saveOrUpdate(testPlanRun);
	}

	public List<TestPlanRun> getAllTestPlanRuns() {
		return openSession().createCriteria(TestPlanRun.class)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.addOrder(Order.desc("TestPlanRunID"))
				.list();
	}

	public List<TestPlanRun> getTestPlanRuns(Integer page, Integer count) {
		return openSession().createCriteria(TestPlanRun.class)
				.setMaxResults(count)
				.setFirstResult((page-1)*count)
				.addOrder(Order.desc("TestPlanRunID"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.setFetchMode("tests", FetchMode.SELECT)
				.list();
	}
	
	public TestPlanRun getTestPlanRunByName(String name){
		List list = openSession().createCriteria(TestPlanRun.class)
				.add(Restrictions.eq("name", name)).list();
		return list.size() > 0 ? (TestPlanRun)list.get(0) : null;
	}

	public Long countTestSuitesRuns() {
		Criteria criteriaCount = openSession().createCriteria(TestPlanRun.class);
		criteriaCount.setProjection(Projections.rowCount());
		return (Long) criteriaCount.uniqueResult();
	}

	public void merge(TestPlanRun testSuite) {
		openSession().merge(testSuite);
	}
	
	public void replicate(TestPlanRun testSuite){
		openSession().replicate(testSuite, ReplicationMode.EXCEPTION);
	}
	
	public void evict(TestPlanRun testSuite){
		openSession().evict(testSuite);
	}
	
}
