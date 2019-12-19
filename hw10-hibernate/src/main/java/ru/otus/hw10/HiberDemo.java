package ru.otus.hw10;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.slf4j.LoggerFactory;
import ru.otus.hw10.hibernate.ORMTemplateImpl;
import ru.otus.hw10.model.Address;
import ru.otus.hw10.model.Phone;
import ru.otus.hw10.model.User;


public class HiberDemo {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HiberDemo.class);
	private final SessionFactory sessionFactory;

	public static void main(String[] args) {

		HiberDemo demo = new HiberDemo();

		ORMTemplateImpl<User> orm = new ORMTemplateImpl<>(demo.sessionFactory);

		User user = new User();
		user.setName("Den");
		user.setAge(31);

		Address address = new Address("user_address_street",user);
		user.setAddress(address);

		List<Phone> listPhone = new ArrayList<>();
		listPhone.add(new Phone("user_number_phone  123", user));
        listPhone.add(new Phone("user_number_phone  456", user));
        listPhone.add(new Phone("user_number_phone  789", user));
		user.setPhoneDataSet(listPhone);

		orm.saveEntity(user);

		User selectedUser = orm.getEntity(User.class, orm.getSavedId());

		logger.info("Selected user {}",selectedUser);

		for (Phone phone : selectedUser.getPhoneDataSet()) {
			logger.info("Selected user phone: {}", phone);
		}

        logger.info("Selected user address: {}",  selectedUser.getAddress());

	}


	private HiberDemo() {
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		Metadata metadata = new MetadataSources(serviceRegistry)
				.addAnnotatedClass(User.class)
                .addAnnotatedClass(Address.class)
				.addAnnotatedClass(Phone.class)
				.getMetadataBuilder()
				.build();

		sessionFactory = metadata.getSessionFactoryBuilder().build();
	}

}

