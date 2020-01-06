package hw11.cachehw;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.cachehw.HwCache;
import ru.otus.hw11.cachehw.HwCacheImpl;
import ru.otus.hw11.cachehw.HwListener;

import static org.junit.jupiter.api.Assertions.*;

public class HwCacheTest {

    private static final Logger logger = LoggerFactory.getLogger(HwCacheTest.class);

    static class BigObject {
        final byte[] array = new byte[1024 * 1024];

        public byte[] getArray() {
            return array;
        }

        @Override
        public String toString() {
            return "BigObject";
        }
    }

    @Test
    void hwCacheWithoutGCString () throws InterruptedException {
        HwCache<Integer, String> cache = new HwCacheImpl<>(100, 2);

        HwListener<Integer, String> listener =
                (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);

        cache.addListener(listener);

        for (int i=0;i<100;i++) {
            cache.put(i, ("value"+i));
        }

        Thread.sleep(100);

        assertEquals("value1",cache.get(1));
        cache.remove(1);
        assertNull(cache.get(1));

        assertEquals("value10",cache.get(10));
        cache.remove(10);
        assertNull(cache.get(10));

        cache.removeListener(listener);
    }

    @Test
    void hwCacheWithoutGCBigObject () throws InterruptedException {
        int size = 10;
        HwCache<Integer, BigObject> cache = new HwCacheImpl<>(100, 2);

        HwListener<Integer, BigObject> listener =
                (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);

        cache.addListener(listener);

        for (int k = 0; k < size; k++) {
            cache.put(k,new BigObject());
        }

        Thread.sleep(100);

        assertNotNull(cache.get(1));
        cache.remove(1);
        assertNull(cache.get(1));

        cache.removeListener(listener);
    }


    @Test
    void hwCacheWithGC () throws InterruptedException {
        int size = 100;
        HwCache<Integer, String> cache = new HwCacheImpl<>(100, 2);

        HwListener<Integer, String> listener =
                (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);

        cache.addListener(listener);

        for (int i=0;i<size;i++) {
            cache.put(i, ("value"+i));
        }

        Thread.sleep(1000);
        System.gc();
        Thread.sleep(1000);

        assertNotNull(cache.get(1));
        assertNotNull(cache.get(10));

        cache.removeListener(listener);
    }

    @Test
    void hwCacheListenersWithGC () throws InterruptedException {
        int size = 3;
        HwCache<Integer, String> cache = new HwCacheImpl<>(100, 2);
        HwListener<Integer, String> listener1 =
                (key, value, action) -> logger.info("listener1 key:{}, value:{}, action: {}", key, value, action);
        HwListener<Integer, String> listener2 =
                (key, value, action) -> logger.info("listener2 key:{}, value:{}, action: {}", key, value, action);
        HwListener<Integer, String> listener3 =
                (key, value, action) -> logger.info("listener3 key:{}, value:{}, action: {}", key, value, action);


        logger.info("Log put before System.gc()");
        for (int i=0;i<size;i++) {
            cache.put(i, ("value"+i));
        }

        cache.addListener(listener1);
        cache.addListener(listener2);
        cache.addListener(listener3);


        Thread.sleep(1000);
        System.gc();
        Thread.sleep(1000);

        logger.info("Log put after System.gc()");
        for (int i=0;i<size;i++) {
            cache.put(i, ("value"+i));
        }

        cache.removeListener(listener1);
        cache.removeListener(listener2);

        logger.info("Log put after removeListener 1");
        for (int i=0;i<size;i++) {
            cache.put(i, ("value"+i));
        }

        cache.removeListener(listener3);

        logger.info("Log put after removeListener 2");
        for (int i=0;i<size;i++) {
            cache.put(i, ("value"+i));
        }
    }

    @Test
    void hwCacheListenerFail()   {

        HwCache<Integer, String> cache = new HwCacheImpl<>(100, 2);
        HwListener<Integer, String> listener1 =
                (key, value, action) -> {throw new RuntimeException("hwCacheListenerFail");};
        cache.addListener(listener1);
        cache.put(1, "value");

    }

    @Test
    void hwCacheListenerFail1()   {
        HwCache<Integer, String> cache = new HwCacheImpl<>(100, 2);
        cache.addListener(null);
        cache.put(1, "value");
        cache.addListener(null);
    }

    @Test
    void hwCacheListenerFail2()   {
        HwCache<Integer, String> cache = new HwCacheImpl<>(100, 2);
        cache.removeListener(null);
        cache.put(1, "value");
    }


}
