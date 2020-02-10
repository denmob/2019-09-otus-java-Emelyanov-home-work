package ru.otus.hw16.service.handlers;


import com.google.gson.Gson;
import ru.otus.hw16.domain.ChatMessage;
import ru.otus.hw16.domain.User;
import ru.otus.hw16.mesages.Message;
import ru.otus.hw16.msclient.RequestHandler;
import ru.otus.hw16.service.DBService;

import java.util.List;
import java.util.Optional;


public class GetDataRequestHandler implements RequestHandler {

  private final DBService dbService;

  public GetDataRequestHandler(DBService dbService) {
    this.dbService = dbService;
  }

  @Override
  public Message handle(Message msg) {
      switch (msg.getCommand()) {
          case SAVE_USER: {

              User user = new Gson().fromJson((String) msg.getObject(),User.class);
              return new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(),  new Gson().toJson(dbService.saveUser(user)));
          }
          case GET_ALL_USERS: {
              List<User> users = dbService.getAllUsers();
              String jsonUsers = new Gson().toJson(users);
              return new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(), jsonUsers);
          }
          case GET_USER_WITH_LOGIN:{
              String login = (String) msg.getObject();
              Optional<User> user = dbService.findByUserLogin(login);
              String jsonUser = new Gson().toJson(user);
              return new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(),jsonUser);
          }
          case SAVE_CHAT_MESSAGE:{
              ChatMessage chatMessage = new Gson().fromJson((String) msg.getObject(),ChatMessage.class);
              return new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(),  new Gson().toJson(dbService.saveChatMessage(chatMessage)));
          }
          case GET_All_CHAT_MESSAGES:{
              List<ChatMessage> chatMessages = dbService.getHistoryChatMessage();
              String jsonChatMessages = new Gson().toJson(chatMessages);
              return new Message(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(), jsonChatMessages);
          }
          case VOID_TECHNICAL_MESSAGE:{
              return new Message();
          }
      }
   return null;
  }
}
