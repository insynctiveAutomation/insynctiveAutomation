package insynctive.dao.test;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.test.TestPlan;
import insynctive.model.test.run.TestPlanRun;

@Repository
@Transactional
public class TestPlanDao {

	private final SessionFactory sessionFactory;
	
	@Inject
	public TestPlanDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public TestPlan getTestPlanByID(int id){
		return (TestPlan) openSession().get(TestPlan.class, id);
	}
	
	public TestPlan getTestPlanRunByID(int id){
		return (TestPlan) openSession().get(TestPlanRun.class, id);
	}
	
	public void saveOrUpdate(TestPlan testPlan){
		openSession().saveOrUpdate(testPlan);
	}
	
	public void merge(TestPlan testPlan){
		openSession().merge(testPlan);
	}

	public void remove(TestPlan testPlan) {
		openSession().delete(testPlan);
	}
	
	public void update(TestPlan TestPlan) {
		openSession().update(TestPlan);
	}

	public TestPlan getTestPlanByName(String name) {
		Criteria testPlansCriteria = openSession().createCriteria(TestPlan.class).add(Restrictions.eq("name", name));
		List<TestPlan> testPlans = testPlansCriteria.list();
		if(testPlans.size() >= 1){
			return testPlans.get(0);
		} else {
			return null;
		}
	}
	
	public List<TestPlan> getAllTestPlans() {
		return openSession().createCriteria(TestPlan.class)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.addOrder(Order.desc("name"))
				.list();
	}
	
}
