package ru.otus.hw16.msclient;


import ru.otus.hw16.shared.mesages.MessageTransport;

import java.util.Optional;

public interface RequestHandler {
  Optional<MessageTransport> handle(MessageTransport msg);
}
