package ru.otus.hw15.db.handlers;

import ru.otus.hw15.domain.User;
import ru.otus.hw15.messagesystem.Message;
import ru.otus.hw15.messagesystem.RequestHandler;
import ru.otus.hw15.db.DBService;

import java.util.Optional;


public class GetUserDataRequestHandler implements RequestHandler {

  private final DBService dbService;

  public GetUserDataRequestHandler(DBService dbService) {
    this.dbService = dbService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
   if  (msg.getCommand().equals("findByUserLogin")) {
       User user = (User) msg.getObject();
       Optional<User> user1 = dbService.findByUserLogin(user.getLogin());
      return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getCommand(), user1));
    }
   return Optional.empty();

  }
}
