package ru.otus.hw16.msclient;

import ru.otus.hw16.shared.mesages.MessageTransport;

public interface RequestHandler {
  MessageTransport handle(MessageTransport msg);
}
