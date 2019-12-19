package ru.otus.hw10.hibernate;

import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Objects;

public class ORMTemplateImpl<T> implements ORMTemplate<T> {

	private long savedId = 0;
	
	private SessionFactory sessionFactory;
	
	public ORMTemplateImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public T getEntity(Class<T> entityClass, long id) {
		T selectedEntity;
		
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			
			selectedEntity = session.get(entityClass, id);
			
			transaction.commit();
        }
		catch (Exception ex) {
			Objects.requireNonNull(transaction).rollback();
			throw new ORMTemplateException(ex);
		}
		
		return selectedEntity;
	}

	@Override
	public boolean saveEntity(T entity) {

		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();

			savedId = (long)session.save(entity);

			transaction.commit();	
			return  true;
        }
		catch (Exception ex) {
			Objects.requireNonNull(transaction).rollback();
			throw new ORMTemplateException(ex);
		}

	}
	
	public long getSavedId() {
		return savedId;
	}

}
