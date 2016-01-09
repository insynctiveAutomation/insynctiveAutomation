package insynctive.dao.test;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.test.Test;
import insynctive.model.test.TestPlan;

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
	
	public void remove(Test test){
		openSession().delete(test);
	}

	public void update(Test test) {
		openSession().update(test);
	}
	
	public List<Test> getAllTestPlans() {
		return openSession().createCriteria(Test.class)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.addOrder(Order.desc("testName"))
				.list();
	}
}
 