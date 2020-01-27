package ru.otus.hw15.db.handlers;


import ru.otus.hw15.common.Serializers;
import ru.otus.hw15.domain.User;
import ru.otus.hw15.messagesystem.Message;
import ru.otus.hw15.messagesystem.MessageType;
import ru.otus.hw15.messagesystem.RequestHandler;
import ru.otus.hw15.repostory.UserRepository;

import java.util.Optional;


public class GetUserDataRequestHandler implements RequestHandler {

  private final UserRepository repository;

  public GetUserDataRequestHandler(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    String login  = Serializers.deserialize(msg.getPayload(), String.class);
    Optional<User> user = repository.findByUserLogin(login);
    return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(), MessageType.USER_DATA.getValue(), Serializers.serialize(user)));
  }
}
