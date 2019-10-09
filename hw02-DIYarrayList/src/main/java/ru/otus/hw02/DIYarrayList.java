package ru.otus.hw02;

import java.util.*;

public class DIYarrayList<T> implements List<T> {

    private Object[] elementData;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 100;

    public DIYarrayList(int initialCapacity, boolean setNull) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
            // init array
            if (setNull) {
                for (int i = 0; i < initialCapacity - 1; i++)
                    elementData[i] = null;
                size = initialCapacity;
            }
        } else if (initialCapacity == 0) {
            this.elementData = new Object[DEFAULT_CAPACITY];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);
        }
    }

    public DIYarrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = new Object[DEFAULT_CAPACITY];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);
        }
    }

    public DIYarrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public int indexOf(Object o) { throw new UnsupportedOperationException(""); }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(""); }

    @Override
    public boolean add(T t) {
        elementData[size] = t;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return addAll(this.size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("");
    }


    @Override
    public void clear() {
        for (int i =0;  i <= size; i++)
            elementData[i] = null;
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return (T) elementData[index];
    }

    @Override
    public T set(int index, T element) {
        Objects.checkIndex(index, size);
        T oldValue = (T) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        elementData[index] = element;
        size++;
    }

    private void fastRemove(Object[] es, int i) {
        final int newSize;
        if ((newSize = size - 1) > i)
            System.arraycopy(es, i + 1, es, i, newSize - i);
        es[size = newSize] = null;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        final Object[] es = elementData;
        T oldValue = (T) es[index];
        fastRemove(es, index);
        return oldValue;
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;
            public boolean hasNext() { return  index != size; }
            public T next() {
                return (T) get(index++);
            }
        };
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIterator<T>() {
            int index;
            int lastRet = -1;

            public boolean hasNext() { return index != size; }

            public T next() {
                int i = index;
                if (i >= size)
                    throw new NoSuchElementException();
                try {
                    T next = get(i);
                    lastRet = i;
                    index = i + 1;
                    return next;
                } catch (IndexOutOfBoundsException e) {
                    throw new NoSuchElementException();
                }
            }

            public boolean hasPrevious() { return index != 0; }

            public T previous() {
                int i = index - 1;
                if (i < 0)
                    throw new NoSuchElementException();
                Object[] elementData = DIYarrayList.this.elementData;
                if (i >= elementData.length)
                    throw new ConcurrentModificationException();
                index = i;
                return (T) elementData[lastRet = i];
            }

            public int nextIndex() { return index; }

            public int previousIndex() { return index - 1; }

            public void remove() {
                throw new UnsupportedOperationException("");
            }

            public void set(T t) {
                if (lastRet < 0)
                    throw new IllegalStateException();
                try {
                    DIYarrayList.this.set(lastRet, t);
                } catch (IndexOutOfBoundsException ex) {
                    throw new ConcurrentModificationException();
                }
            }

            public void add(T t) {
                throw new UnsupportedOperationException("");
            }
        };
    }


    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public void sort(Comparator<? super T> c) {
        Arrays.sort((T[]) elementData, 0, size, c);
    }
}
