package hw11.cachehw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.hw10.api.cachehw.HwCache;
import ru.otus.hw11.hw10.impl.cachehw.HwCacheImpl;
import ru.otus.hw11.hw10.api.cachehw.HwListener;

import static org.junit.jupiter.api.Assertions.*;

public class HwCacheTest {

  private static final Logger logger = LoggerFactory.getLogger(HwCacheTest.class);

  static class BigObject {
    final byte[] array = new byte[1024 * 1024];

    @Override
    public String toString() {
      return "BigObject";
    }
  }

  @BeforeEach
  void before() {
    System.gc();
  }

  @Test
  void hwCacheWithoutGCString() throws InterruptedException {
    HwCache<String, String> cache = new HwCacheImpl<>(100, 2);

    HwListener listener = (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);

    cache.addListener(listener);

    for (int i = 1000; i < 1100; i++) {
      cache.put(String.valueOf(i), ("value" + i));
    }

    Thread.sleep(100);

    assertEquals("value1000", cache.get("1000"));
    cache.remove("1");
    assertNull(cache.get("1"));

    assertEquals("value1010", cache.get("1010"));
    cache.remove("10");
    assertNull(cache.get("10"));

    cache.removeListener(listener);
  }

  @Test
  void hwCacheWithListenerAsLambda() throws InterruptedException {
    int size = 10;
    HwCache<Integer, BigObject> cache = new HwCacheImpl<>(100, 2);

    HwListener listener = (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);

    cache.addListener(listener);
    BigObject bigObject = new BigObject();
    for (int k = 1000; k < 1000 + size; k++) {
      cache.put(k, bigObject);
    }
    listener = null;
    bigObject = null;

    Thread.sleep(1000);
    System.gc();

    assertNull(cache.get(1000));
    assertNotNull(cache.getFirstListener());
    assertEquals(1, cache.getSizeListener());
  }

  @Test
  void hwCacheWithListenerAsAnonymous() throws InterruptedException {
    int size = 10;
    HwCache<Integer, BigObject> cache = new HwCacheImpl<>(100, 2);

    HwListener listener = new HwListener() {
      @Override
      public void myNotify(Object key, Object value, String action) {
        logger.info("key:{}, value:{}, action: {}", key, value, action);
      }
    };

    cache.addListener(listener);
    BigObject bigObject = new BigObject();
    for (int k = 1000; k < 1000 + size; k++) {
      cache.put(k, bigObject);
    }
    listener = null;
    bigObject = null;

    Thread.sleep(1000);
    System.gc();

    assertNull(cache.get(1000));
    assertNull(cache.getFirstListener());
    assertEquals(0, cache.getSizeListener());
  }


  @Test
  void hwCacheWithGC() throws InterruptedException {
    int size = 100;
    HwCache<Integer, String> cache = new HwCacheImpl<>(100, 2);

    HwListener listener = (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);

    cache.addListener(listener);

    for (int i = 1000; i < 1000 + size; i++) {
      cache.put(i, ("value" + i));
    }

    Thread.sleep(1000);
    System.gc();

    assertNull(cache.get(1000));
    assertNull(cache.get(1010));

    cache.removeListener(listener);
  }
}
