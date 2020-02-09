package ru.otus.hw16.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw16.ms.MessageSystem;

@RestController
public class ServiceController {

	@Autowired
	private MessageSystem messageSystem;

	@RequestMapping("/db")
	public void createMessageForDB() {
		//messageSystem.createMessageForDatabase()
	}

}
