package ru.otus.hw11.cachehw;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class HwCacheImpl<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы
  private  Map<K, V> cache = new WeakHashMap<>();

  private List<WeakReference<HwListener>> hwListener = new ArrayList<>();

  @Override
  public void put(K key, V value) {
    if(!hwListener.isEmpty()) {
      WeakReference<HwListener>  hwListenerWeakReference = hwListener.get(hwListener.size() - 1);
      hwListenerWeakReference.get().notify(key, value, "put");
    }
    cache.put(key,value);
  }

  @Override
  public void remove(K key) {
    if (!hwListener.isEmpty()) {
      WeakReference<HwListener> hwListenerWeakReference = hwListener.get(hwListener.size() - 1);
      hwListenerWeakReference.get().notify(key, cache.get(key), "remove");
    }
    cache.remove(key);
  }

  @Override
  public V get(K key) {
   return cache.get(key);
  }

  @Override
  public void addListener(HwListener listener) {
    this.hwListener.add(new WeakReference<>(listener));
  }

  @Override
  public void removeListener(HwListener listener) {
    this.hwListener.remove( listener);
  }
}
