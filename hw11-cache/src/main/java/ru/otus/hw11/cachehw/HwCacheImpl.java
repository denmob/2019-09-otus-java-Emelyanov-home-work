package ru.otus.hw11.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;


public class HwCacheImpl<K, V> implements HwCache<K, V> {

  private Logger logger = LoggerFactory.getLogger(HwCacheImpl.class);

  private final int maxElements;
  private final int maxListeners;
  private List<HwListener> hwListeners;

  private WeakHashMap<K, V> elements;


  public HwCacheImpl(int maxElements, int maxListeners) {
    this.maxElements = maxElements;
    this.maxListeners = maxListeners;
    this.elements = new WeakHashMap<>(maxElements);
    this.hwListeners = new ArrayList<>(maxListeners);
  }

  @Override
  public void put(K key, V value) {
    if (elements.size() != maxElements) {
      listenerNotify(key, value, "put");
      elements.put(key, value);
    } else logger.error("Cache space full");
  }

  @Override
  public void remove(K key) {
    listenerNotify(key,null,"remove");
    elements.remove(key);
  }

  private void listenerNotify(K key, V value, String action) {
    if (!hwListeners.isEmpty()) {
      value = (value == null) ? get(key):value;
      for (HwListener hwListener:hwListeners)
        try {
          hwListener.notify(key, value, action);
        }catch (Exception e) {
          logger.error("hwListener.notify exception key: {}, value: {}, action: {}",key,value,action);
          logger.error("cause exception",e);
        }
      }
  }

  @Override
  public V get(K key) {
      V value = elements.get(key);
      if (value != null)
        listenerNotify(key, value, "get");
      else logger.error("Element not found");
      return value;
  }

  @Override
  public void addListener(HwListener listener) {
    if ((hwListeners.size() != this.maxListeners) && (listener != null))
      this.hwListeners.add(listener);
  }


  @Override
  public void removeListener(HwListener listener) {
    if (!hwListeners.isEmpty()&& (listener != null)) {
      this.hwListeners.remove(listener);
    }
  }


}
