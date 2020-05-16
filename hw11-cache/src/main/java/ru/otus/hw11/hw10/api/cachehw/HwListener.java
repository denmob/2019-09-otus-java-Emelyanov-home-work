package ru.otus.hw11.hw10.api.cachehw;


public interface HwListener {
  void myNotify(Object key, Object value, String action);
}
