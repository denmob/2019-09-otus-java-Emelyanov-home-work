package ru.otus.hw11.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.hw10.service.ORMServiceUserImpl;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;


public class HwCacheImpl<K, V> implements HwCache<K, V> {

  private Logger logger = LoggerFactory.getLogger(HwCacheImpl.class);

  private final int maxElements;
  private final int maxListeners;

  private  Map<K, WeakReference<V>> elements;

  private List<WeakReference<HwListener>> listenersWeak;
  private List<SoftReference<HwListener>> listenersSoft;
  private List<HwListener> listenersRef;

  public HwCacheImpl(int maxElements, int maxListeners) {
    this.maxElements = maxElements;
    this.maxListeners = maxListeners;
    elements = new WeakHashMap<>(maxElements);
    listenersWeak = new ArrayList<>(maxListeners);
    listenersSoft = new ArrayList<>(maxListeners);
    listenersRef = new ArrayList<>(maxListeners);
  }

  @Override
  public void put(K key, V value) {
    if (elements.size() == maxElements) {
      K firstKey = elements.keySet().iterator().next();
      elements.remove(firstKey);
    }

    listenerNotify(key,value,"put");
    elements.put(key,new WeakReference<>(value));
  }

  @Override
  public void remove(K key) {
    listenerNotify(key,null,"remove");
    elements.remove(key);
  }

  private void listenerNotify(K key, V value, String action) {
    if (!listenersWeak.isEmpty()) {
      WeakReference<HwListener> hwListenerWeakReference = listenersWeak.get(listenersWeak.size() - 1);
      value =  (value== null) ? get(key):value;
      hwListenerWeakReference.get().notify(key,value, action);
    }

    if (!listenersSoft.isEmpty()) {
      SoftReference<HwListener> listenerSoftReference = listenersSoft.get(listenersSoft.size() - 1);
      value =  (value== null) ? get(key):value;
      listenerSoftReference.get().notify(key,value, action);
    }

    if (!listenersRef.isEmpty()) {
      HwListener hwListener = listenersRef.get(listenersRef.size() - 1);
      value =  (value== null) ? get(key):value;
      hwListener.notify(key,value, action);
    }
  }

  @Override
  public V get(K key) {
    WeakReference<V> vWeakReference = elements.get(key);
    if (vWeakReference == null) logger.debug("Element {} not found", key);
   return vWeakReference != null ? vWeakReference.get() : null;
  }

  @Override
  public void addListener(HwListener listener) {
    if (listenersRef.size() != maxListeners) {
      this.listenersRef.add(listener);
    }
  }

  @Override
  public void addListenerWeak(WeakReference<HwListener>  listener) {
    if (listenersWeak.size() != maxListeners) {
      this.listenersWeak.add(listener);
    }
  }

  @Override
  public void addListenerSoft(SoftReference<HwListener> listener) {
    if (listenersSoft.size() != maxListeners) {
      this.listenersSoft.add(listener);
    }
  }

  @Override
  public void removeListener(HwListener listener) {
    if (!listenersRef.isEmpty()) {
      this.listenersRef.remove(listener);
    }
  }

  @Override
  public void removeListenerWeak(WeakReference<HwListener>  listener) {
    if (!listenersWeak.isEmpty()) {
      this.listenersWeak.remove(listener);
    }
  }

  @Override
  public void removeListenerSoft(SoftReference<HwListener> listener) {
    if (!listenersSoft.isEmpty()) {
      this.listenersSoft.remove(listener);
    }
  }


}
