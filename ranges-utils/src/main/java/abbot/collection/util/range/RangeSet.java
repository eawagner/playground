package abbot.collection.util.range;

import com.google.common.collect.Range;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


public interface RangeSet<T extends Comparable> {
    int size();

    boolean add(Range<T> tRange);

    boolean addAll(Collection<? extends Range<T>> tRanges);

    boolean remove(Range<T> tRange);

    boolean removeAll(Collection<? extends Range<T>> tRanges);

    boolean contains(T item);

    void clear();

    Iterator<Range<T>> iterator();

    Set<Range<T>> asSet();

}
