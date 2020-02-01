package ru.otus.hw15.db.handlers;

import ru.otus.hw15.db.DBService;
import ru.otus.hw15.domain.ChatMessage;
import ru.otus.hw15.domain.User;
import ru.otus.hw15.messagesystem.Message;
import ru.otus.hw15.messagesystem.RequestHandler;

import java.util.List;
import java.util.Optional;


public class GetDataRequestHandler implements RequestHandler {

  private final DBService dbService;

  public GetDataRequestHandler(DBService dbService) {
    this.dbService = dbService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
      switch (msg.getCommand()) {
          case SAVE_USER: {
              User user = (User) msg.getObject();
              return Optional.of(new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(),  dbService.saveUser(user)));
          }
          case GET_ALL_USERS: {
              List<User> users = dbService.getAllUsers();
              return Optional.of(new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(), users));
          }
          case GET_USER_WITH_LOGIN:{
              String login = (String) msg.getObject();
              Optional<User> user = dbService.findByUserLogin(login);
              return Optional.of(new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(), user));
          }
          case SAVE_CHAT_MESSAGE:{
              ChatMessage chatMessage = (ChatMessage) msg.getObject();
              return Optional.of(new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(),  dbService.saveChatMessage(chatMessage)));
          }
          case GET_All_CHAT_MESSAGES:{
              List<ChatMessage> chatMessages = dbService.getHistoryChatMessage();
              return Optional.of(new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(), chatMessages));
          }
      }
   return Optional.empty();
  }
}
