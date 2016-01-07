package insynctive.dao.test;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
	
	public List<TestSuite> getAllTestSuite() {
		return openSession().createCriteria(TestSuite.class)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.addOrder(Order.desc("testSuiteID"))
				.list();
	}

	public List<TestSuite> getTestSuite(Integer page, Integer count) {
		return openSession().createCriteria(TestSuite.class)
				.setMaxResults(count)
				.setFirstResult((page-1)*count)
				.addOrder(Order.desc("testSuiteID"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.setFetchMode("tests", FetchMode.SELECT)
				.list();
	}
	
	public TestSuite getTestSuiteByName(String name){
		List list = openSession().createCriteria(TestSuite.class)
				.add(Restrictions.eq("testSuiteName", name)).list();
		return list.size() > 0 ? (TestSuite)list.get(0) : null;
	}
}
