package ru.otus.hw10.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw10.hibernate.dao.UserDao;
import ru.otus.hw10.hibernate.dao.UserDaoImpl;
import ru.otus.hw10.model.User;

import java.util.Optional;

public class ORMTemplateImpl implements ORMTemplate {

	private static final Logger logger = LoggerFactory.getLogger(ORMTemplateImpl.class);
	private final UserDao userDao = new UserDaoImpl();

	private final SessionFactory sessionFactory;
	
	public ORMTemplateImpl(SessionFactory sessionFactory) {
		if (sessionFactory == null) throw new IllegalArgumentException("SessionFactory is null!");
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Optional<User> getEntity(long id) {
		if (id <=0 ) throw new IllegalArgumentException("Incorrect id for get object!");
		try (Session session = sessionFactory.openSession()) {
			Optional<User> userOptional = userDao.findById(id,session);
			logger.debug("user: {}", userOptional.orElse(null));
			return userOptional;
        }
		catch (Exception ex) {
			throw new ORMTemplateException(ex);
		}
	}

	@Override
	public void saveEntity(User user) {
		if (user == null) throw new IllegalArgumentException("Entity is null!");

		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			userDao.saveUser(user,session);
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
