package abbot.common.util.concurrency;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.newSetFromMap;

/**
 * This is a simple thread-safe, non-blocking {@link Collection}.
 *
 * This simply wraps the {@link java.util.Collections#newSetFromMap} method in an easy to use class implementation.
 *
 * @param <E>
 */
public class ConcurrentHashSet<E> implements Set<E>{

    private Set<E> internal;

    public ConcurrentHashSet() {
        internal = newSetFromMap(new ConcurrentHashMap<E, Boolean>());
    }

    public ConcurrentHashSet(int initialCapacity) {
        internal = newSetFromMap(new ConcurrentHashMap<E, Boolean>(initialCapacity));
    }

    public ConcurrentHashSet(int initialCapacity, float loadFactor, int concurrencyLevel) {
        internal = newSetFromMap(new ConcurrentHashMap<E, Boolean>(initialCapacity, loadFactor, concurrencyLevel));
    }

    public ConcurrentHashSet(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return internal.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return internal.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {
        return internal.contains(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return internal.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        return internal.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T[] toArray(T[] ts) {
        return internal.toArray(ts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E e) {
        return internal.add(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        return internal.remove(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<?> objects) {
        return internal.containsAll(objects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends E> es) {
        return internal.addAll(es);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> objects) {
        return internal.retainAll(objects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> objects) {
        return internal.removeAll(objects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        internal.clear();
    }
}
