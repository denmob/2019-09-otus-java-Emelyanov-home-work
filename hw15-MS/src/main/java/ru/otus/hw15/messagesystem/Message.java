package ru.otus.hw15.messagesystem;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Message {

  static final Message VOID_MESSAGE = new Message();

  private final UUID id = UUID.randomUUID();
  private final String from;
  private final String to;
  private final UUID sourceMessageId;
  private final CommandType command;
  private final Object object;


  public Message() {
    this.from = null;
    this.to = null;
    this.sourceMessageId = null;
    this.command = CommandType.VOID_TECHNICAL_MESSAGE;
    this.object = null;
  }

  public Message(String from, String to, UUID sourceMessageId,  CommandType command, Object object ) {
    this.from = from;
    this.to = to;
    this.sourceMessageId = sourceMessageId;
    this.command = command;
    this.object = object;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Message message = (Message) o;
    return id == message.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Message{" +
        "id=" + id +
        ", from='" + from + '\'' +
        ", to='" + to + '\'' +
            ", sourceMessageId=" + sourceMessageId +
        ", command=" + command +
        ", object='" + object +
        '}';
  }

  public UUID getId() {
    return id;
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public CommandType getCommand() {
    return command;
  }

  public Object getObject() {
    return object;
  }

  public Optional<UUID> getSourceMessageId() {
    return Optional.ofNullable(sourceMessageId);
  }

}
