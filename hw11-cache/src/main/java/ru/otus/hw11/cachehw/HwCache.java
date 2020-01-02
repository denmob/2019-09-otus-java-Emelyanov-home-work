package ru.otus.hw11.cachehw;


import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public interface HwCache<K, V> {

  void put(K key, V value);

  void remove(K key);

  V get(K key);

  void addListener(HwListener  listener);
  void addListenerWeak(WeakReference<HwListener>  listener);
  void addListenerSoft(SoftReference<HwListener>  listener);

  void removeListener(HwListener listener);
  void removeListenerWeak(WeakReference<HwListener> listener);
  void removeListenerSoft(SoftReference<HwListener> listener);

}
