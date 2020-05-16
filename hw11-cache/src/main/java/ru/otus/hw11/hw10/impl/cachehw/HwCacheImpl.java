package ru.otus.hw11.hw10.impl.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.hw10.api.cachehw.HwCache;
import ru.otus.hw11.hw10.api.cachehw.HwListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;


public class HwCacheImpl<K, V> implements HwCache<K, V> {

  private Logger logger = LoggerFactory.getLogger(HwCacheImpl.class);

  private final int maxElements;
  private final int maxListeners;
  private List<WeakReference<HwListener>> hwListeners;

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
    listenerNotify(key, null, "remove");
    elements.remove(key);
  }

  private void listenerNotify(K key, V value, String action) {
    if (!hwListeners.isEmpty()) {
      for (WeakReference<HwListener> hwListener : hwListeners) {
        HwListener hwListenerWeakReference = hwListener.get();
        if (hwListenerWeakReference != null) {
          value = (value == null) ? get(key) : value;
          try {
            hwListenerWeakReference.myNotify(key, value, action);
          } catch (Exception e) {
            logger.debug("hwListener.notify exception key: {}, value: {}, action: {}", key, value, action);
            logger.error(e.getMessage(), e);
          }
        }
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
      this.hwListeners.add(new WeakReference<>(listener));
  }

  @Override
  public HwListener getFirstListener() {
    if (!hwListeners.isEmpty()) {
      for (WeakReference<HwListener> hwListener : hwListeners) {
        if (hwListener.get() != null) {
          return hwListener.get();
        }
      }
    }
    return null;
  }

  @Override
  public int getSizeListener() {
    int count = 0;
    if (!hwListeners.isEmpty()) {
      for (WeakReference<HwListener> hwListener : hwListeners) {
        if (hwListener.get() != null) {
          count++;
        }
      }
    }
    return count;
  }


  @Override
  public void removeListener(HwListener listener) {
    if (!hwListeners.isEmpty() && (listener != null)) {
      for (WeakReference<HwListener> hwListener : hwListeners) {
        HwListener hwListener1 = hwListener.get();
        if (hwListener1 != null && hwListener1.equals(listener)) {
          this.hwListeners.remove(hwListener);
          return;
        }
      }
    }
  }


}
