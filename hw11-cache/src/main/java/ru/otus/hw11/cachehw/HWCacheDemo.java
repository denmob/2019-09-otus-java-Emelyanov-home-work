package ru.otus.hw11.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class HWCacheDemo {
  private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

  public static void main(String[] args) throws InterruptedException {
    new HWCacheDemo().demo();
  }

  private void demo() throws InterruptedException {
    HwCache<Integer, String> cache = new HwCacheImpl<>();

    HwListener<Integer, String> listener =
            (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);
    cache.addListener(listener);

    for (int i=0;i<100;i++) {
      cache.put(i, ("value"+i));
    }

    Thread.sleep(1000);
    System.gc();

    logger.info("getValue:{}", cache.get(1));
    cache.remove(1);

    logger.info("getValue:{}", cache.get(10));
    cache.remove(10);

    cache.removeListener(listener);

  }
}
