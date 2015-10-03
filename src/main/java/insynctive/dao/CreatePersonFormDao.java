package insynctive.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.CreatePersonForm;

@Repository
@Transactional
public class CreatePersonFormDao {

		private final SessionFactory sessionFactory;
		
		@Inject
		public CreatePersonFormDao(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
		
		private Session openSession() {
			return sessionFactory.getCurrentSession();
		}
		
		public CreatePersonForm getAccountByID(int id){
			return (CreatePersonForm) openSession().get(CreatePersonForm.class, id);
		}
		
		public Integer saveCreatePersonForm(CreatePersonForm form){
			return (Integer) openSession().save(form);
		}
}
