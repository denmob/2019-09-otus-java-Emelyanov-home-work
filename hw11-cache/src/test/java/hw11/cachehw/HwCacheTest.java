package hw11.cachehw;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.cachehw.HwCache;
import ru.otus.hw11.cachehw.HwCacheImpl;
import ru.otus.hw11.cachehw.HwListener;
import ru.otus.hw11.hw10.Demo;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

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

        WeakReference<HwListener> listenerWeakReference = new WeakReference<>(listener);
        cache.addListenerWeak(listenerWeakReference);

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

        cache.removeListenerWeak(listenerWeakReference);
    }

    @Test
    void hwCacheWithoutGCBigObject () throws InterruptedException {
        int size = 10;
        HwCache<Integer, BigObject> cache = new HwCacheImpl<>(100, 2);

        HwListener<Integer, BigObject> listener =
                (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);

        WeakReference<HwListener> listenerWeakReference = new WeakReference<>(listener);
        cache.addListenerWeak(listenerWeakReference);

        for (int k = 0; k < size; k++) {
            cache.put(k,new BigObject());
        }

        Thread.sleep(100);

        assertNotNull(cache.get(1));
        cache.remove(1);
        assertNull(cache.get(1));

        cache.removeListenerWeak(listenerWeakReference);
    }


    @Test
    void hwCacheWithGC () throws InterruptedException {
        int size = 10;
        HwCache<Integer, String> cache = new HwCacheImpl<>(100, 2);

        HwListener<Integer, String> listener =
                (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);

        WeakReference<HwListener> listenerWeakReference = new WeakReference<>(listener);
        cache.addListenerWeak(listenerWeakReference);

        for (int i=0;i<size;i++) {
            cache.put(i, ("value"+i));
        }

        Thread.sleep(100);
        System.gc();
        Thread.sleep(100);

        assertNull(cache.get(1));
        assertNull(cache.get(10));

        cache.removeListenerWeak(listenerWeakReference);

    }

    @Test
    void hwCacheListenersWeakWithGC () throws InterruptedException {
        int size = 3;
        HwCache<Integer, String> cache = new HwCacheImpl<>(100, 2);
        HwListener<Integer, String> listener =
                (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);

        WeakReference<HwListener> listenerWeakReference = new WeakReference<>(listener);
        WeakReference<HwListener> listenerWeakReference1 = new WeakReference<>(listener);
        WeakReference<HwListener> listenerWeakReference2 = new WeakReference<>(listener);

        logger.info("Log put before System.gc()");
        for (int i=0;i<size;i++) {
            cache.put(i, ("value"+i));
        }

        cache.addListenerWeak(listenerWeakReference);
        cache.addListenerWeak(listenerWeakReference1);
        cache.addListenerWeak(listenerWeakReference2);


        Thread.sleep(1000);
        System.gc();
        Thread.sleep(1000);

        logger.info("Log put after System.gc()");
        for (int i=0;i<size;i++) {
            cache.put(i, ("value"+i));
        }

        cache.removeListenerWeak(listenerWeakReference);
        cache.removeListenerWeak(listenerWeakReference2);

        logger.info("Log put after removeListener 1");
        for (int i=0;i<size;i++) {
            cache.put(i, ("value"+i));
        }

        cache.removeListenerWeak(listenerWeakReference);

        logger.info("Log put after removeListener 2");
        for (int i=0;i<size;i++) {
            cache.put(i, ("value"+i));
        }
    }

    @Test
    void hwCacheListenersSoftkWithGC () throws InterruptedException {
        int size = 3;
        HwCache<Integer, String> cache = new HwCacheImpl<>(100, 2);
        HwListener<Integer, String> listener =
                (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);

        SoftReference<HwListener> hwListenerSoftReference = new SoftReference<>(listener);

        cache.addListenerSoft(hwListenerSoftReference);

        logger.info("Log put before System.gc()");
        for (int i=0;i<size;i++) {
            cache.put(i, ("value"+i));
        }

        Thread.sleep(100);
        System.gc();
        Thread.sleep(100);

        logger.info("Log put after System.gc()");
        for (int i=0;i<size;i++) {
            cache.put(i, ("value"+i));
        }

        cache.removeListenerSoft(hwListenerSoftReference);

        logger.info("Log put after removeListener 1");
        for (int i=0;i<size;i++) {
            cache.put(i, ("value"+i));
        }

        cache.removeListenerSoft(hwListenerSoftReference);

    }


}
