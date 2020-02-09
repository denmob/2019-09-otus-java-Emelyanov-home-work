package ru.otus.hw16.service.handlers;


import com.google.gson.Gson;
import ru.otus.hw16.domain.ChatMessage;
import ru.otus.hw16.domain.User;
import ru.otus.hw16.mesages.Message;
import ru.otus.hw16.msclient.RequestHandler;
import ru.otus.hw16.service.DBService;

import java.util.List;


public class GetDataRequestHandler implements RequestHandler {

  private final DBService dbService;

  public GetDataRequestHandler(DBService dbService) {
    this.dbService = dbService;
  }

  @Override
  public Message handle(Message msg) {
      switch (msg.getCommand()) {
          case SAVE_USER: {
              User user = (User) msg.getObject();
              return new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(),  dbService.saveUser(user));
          }
          case GET_ALL_USERS: {
              List<User> users = dbService.getAllUsers();
              return new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(), users);
          }
          case GET_USER_WITH_LOGIN:{
              String login = (String) msg.getObject();
              User user = dbService.findByUserLogin(login);
              String jsonUser = new Gson().toJson(user);
              return new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(),jsonUser);
          }
          case SAVE_CHAT_MESSAGE:{
              ChatMessage chatMessage = (ChatMessage) msg.getObject();
              return new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(),  dbService.saveChatMessage(chatMessage));
          }
          case GET_All_CHAT_MESSAGES:{
              List<ChatMessage> chatMessages = dbService.getHistoryChatMessage();
              return new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(), chatMessages);
          }
          case VOID_TECHNICAL_MESSAGE:{
              return new Message();
          }
      }
   return null;
  }
}
