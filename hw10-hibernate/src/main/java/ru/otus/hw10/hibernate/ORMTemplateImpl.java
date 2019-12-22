package ru.otus.hw10.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

public class ORMTemplateImpl<T> implements ORMTemplate<T> {


	private SessionFactory sessionFactory;
	
	public ORMTemplateImpl(SessionFactory sessionFactory) {
		if (sessionFactory == null) throw new IllegalArgumentException("SessionFactory is null!");
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public T getEntity(Class<T> entityClass, long id) {
		if (entityClass == null) throw new IllegalArgumentException("EntityClass is null!");
		if (id <=0 ) throw new IllegalArgumentException("Incorrect id for get object!");

		T selectedEntity;
		Transaction transaction;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			 selectedEntity = Optional.ofNullable(session.get(entityClass, id)).orElseThrow(ORMTemplateException::new);
			transaction.commit();
        }
		catch (Exception ex) {
			throw new ORMTemplateException(ex);
		}
		return selectedEntity;
	}

	@Override
	public void saveEntity(T entity) {
		if (entity == null) throw new IllegalArgumentException("Entity is null!");

		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();

			session.persist(entity);

			transaction.commit();	

        }
		catch (Exception ex) {
			if (transaction == null)
				throw new ORMTemplateException("Transaction is null! Rollback is missing! ",ex);
			else if (transaction.isActive()) transaction.rollback();

				throw new ORMTemplateException(ex);

		}

	}
	


}
