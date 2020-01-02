package ru.otus.hw10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw10.config.HibernateConfig;
import ru.otus.hw10.config.HibernateConfigDefault1;
import ru.otus.hw10.config.HibernateConfigImpl;
import ru.otus.hw10.dao.UserDao;
import ru.otus.hw10.dao.UserDaoHibernate;
import ru.otus.hw10.dao.UserDaoJdbc;
import ru.otus.hw10.model.Address;
import ru.otus.hw10.model.Phone;
import ru.otus.hw10.model.User;
import ru.otus.hw10.service.ORMServiceUser;
import ru.otus.hw10.service.ORMServiceUserImpl;
import ru.otus.hw10.sessionmanager.SessionManager;
import ru.otus.hw10.sessionmanager.SessionManagerHibernate;
import ru.otus.hw10.sessionmanager.SessionManagerJdbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class Demo {

	private static final Logger logger = LoggerFactory.getLogger(Demo.class);

	public static void main(String[] args) {

		logger.info("hw10-service");
		testJdbc();
		testHibernate();

	}

	private static void testHibernate() {

		HibernateConfig hibernateConfig  = new HibernateConfigDefault1();
		SessionManager sessionManager = new SessionManagerHibernate(hibernateConfig.getSessionFactory());
		UserDao userDao  = new UserDaoHibernate(sessionManager);
		ORMServiceUser ormServiceUser = new ORMServiceUserImpl(userDao);

		User user = new User();
		user.setName("Den");
		user.setAge(31);
		Address address = new Address("user_address_street", user);
		user.setAddress(address);
		List<Phone> listPhone = new ArrayList<>();
		listPhone.add(new Phone("user_number_phone  123", user));
		listPhone.add(new Phone("user_number_phone  456", user));
		listPhone.add(new Phone("user_number_phone  789", user));
		user.setPhoneDataSet(listPhone);

		logger.info("user before save: {}", user);
		ormServiceUser.saveEntity(user);
		logger.info("user after save: {}", user);

		Optional<User> optionalUser = ormServiceUser.getEntity(user.getId());
		if (optionalUser.isPresent()){
			User selectedUser = optionalUser.get();
			logger.info("user selected: {}", selectedUser);
			logger.info("user PhoneDataSet: {}", Collections.singletonList(selectedUser.getPhoneDataSet()));
			logger.info("user PhoneDataSet: {}", Collections.singletonList(user.getPhoneDataSet()));
		}
	}

	private static void testJdbc() {

		SessionManager sessionManager = new SessionManagerJdbc();
		UserDao userDao = new UserDaoJdbc(sessionManager);
		ORMServiceUser ormServiceUser = new ORMServiceUserImpl(userDao);

		User user = new User();
		user.setName("Den");
		user.setAge(31);

		logger.info("user before save: {}", user);
		ormServiceUser.saveEntity(user);
		logger.info("user after save: {}", user);

		Optional<User> optionalUser = ormServiceUser.getEntity(user.getId());
		if (optionalUser.isPresent()) {

			User selectedUser = optionalUser.get();
			logger.info("user selected: {}", selectedUser);

			selectedUser.setName("Max");
			selectedUser.setAge(66);

			ormServiceUser.saveEntity(user);
			optionalUser = ormServiceUser.getEntity(user.getId());
			if (optionalUser.isPresent()) {
				User selectedUser1 = optionalUser.get();
				logger.info("user selected: {}", selectedUser1);
			}
		}
	}

}

