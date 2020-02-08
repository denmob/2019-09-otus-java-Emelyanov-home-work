package ru.otus.hw16.msclient;


import ru.otus.hw16.mesages.Message;

import java.util.Optional;

public interface RequestHandler {
  Optional<Message> handle(Message msg);
}
