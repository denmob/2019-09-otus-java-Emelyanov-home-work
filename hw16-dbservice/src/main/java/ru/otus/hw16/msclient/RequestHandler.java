package ru.otus.hw16.msclient;


import ru.otus.hw16.mesages.Message;

public interface RequestHandler {
  Message handle(Message msg);
}
