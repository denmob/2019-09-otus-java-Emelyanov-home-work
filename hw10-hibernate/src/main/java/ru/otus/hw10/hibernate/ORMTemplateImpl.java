package ru.otus.hw10.hibernate;

import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Objects;

public class ORMTemplateImpl<T> implements ORMTemplate<T> {

	private long savedId = 0;
	
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
			
			selectedEntity = session.get(entityClass, id);
			if (selectedEntity == null)
				throw new ORMTemplateException(String.format("Not found entity with id %s",id));

			transaction.commit();
        }
		catch (Exception ex) {
			throw new ORMTemplateException(ex);
		}
		
		return selectedEntity;
	}

	@Override
	public long saveEntity(T entity) {
		if (entity == null) throw new IllegalArgumentException("Entity is null!");

		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();

			savedId = (long)session.save(entity);

			transaction.commit();	
			return  savedId;
        }
		catch (Exception ex) {
			Objects.requireNonNull(transaction).rollback();
			throw new ORMTemplateException(ex);
		}

	}
	


}
