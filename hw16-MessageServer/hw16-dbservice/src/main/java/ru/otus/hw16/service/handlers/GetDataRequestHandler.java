package ru.otus.hw16.service.handlers;

import com.google.gson.Gson;
import ru.otus.hw16.msclient.RequestHandler;
import ru.otus.hw16.service.DBService;
import ru.otus.hw16.shared.domain.ChatMessage;
import ru.otus.hw16.shared.domain.User;
import ru.otus.hw16.shared.mesages.MessageTransport;

import java.util.List;
import java.util.Optional;

public class GetDataRequestHandler implements RequestHandler {

  private final DBService dbService;

  public GetDataRequestHandler(DBService dbService) {
    this.dbService = dbService;
  }

  @Override
  public MessageTransport handle(MessageTransport msg) {
      switch (msg.getCommand()) {
          case SAVE_USER: {
              User user = new Gson().fromJson((String) msg.getObject(), User.class);
              return new MessageTransport(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(),  new Gson().toJson(dbService.saveUser(user)));
          }
          case GET_ALL_USERS: {
              List<User> users = dbService.getAllUsers();
              String jsonUsers = new Gson().toJson(users);
              return new MessageTransport(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(), jsonUsers);
          }
          case GET_USER_WITH_LOGIN:{
              String login = (String) msg.getObject();
              Optional<User> user = dbService.findByUserLogin(login);
              String jsonUser = new Gson().toJson(user);
              return new MessageTransport(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(),jsonUser);
          }
          case SAVE_CHAT_MESSAGE:{
              ChatMessage chatMessage = new Gson().fromJson((String) msg.getObject(), ChatMessage.class);
              return new MessageTransport(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(),  new Gson().toJson(dbService.saveChatMessage(chatMessage)));
          }
          case GET_ALL_CHAT_MESSAGES:{
              List<ChatMessage> chatMessages = dbService.getHistoryChatMessage();
              String jsonChatMessages = new Gson().toJson(chatMessages);
              return new MessageTransport(msg.getTo(), msg.getFrom(),msg.getId(), msg.getCommand(), jsonChatMessages);
          }
          case VOID_TECHNICAL_MESSAGE:{
              return new MessageTransport();
          }
          default:
              throw new IllegalStateException("Unexpected value: " + msg.getCommand());
      }
  }
}
