package abbot.common.util;

import java.util.Iterator;
import java.util.Queue;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;

public class Iterables2 {

    /**
     * Wraps any iterable into a generic iterable object.  This is useful for using complex iterables such as FluentIterable
     * with libraries that use reflection to determine bean definitions such as Jackson.
     * @param iterable
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> simpleIterable(final Iterable<T> iterable) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return iterable.iterator();
            }
        };
    }

    /**
     * Simple method to return an empty iterable.
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> emptyIterable() {
        return emptyList();
    }

    /**
     * Creates an iterable with a single value.
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> singletonIterable(T data) {
        return singleton(data);
    }

    /**
     * Generates an iterable that will drain a queue by consistently polling the latest item.
     * @param queue
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> drainingIterable(final Queue<T> queue) {
        return fromIterator(
                new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        return queue.size() != 0;
                    }

                    @Override
                    public T next() {
                        return queue.poll();
                    }

                    @Override
                    public void remove() {
                        queue.remove();
                    }
                }
        );
    }

    /**
     * Creates an iterable from an iterator.
     * @param iterator
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> fromIterator(final Iterator<T> iterator) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return iterator;
            }
        };
    }
}
