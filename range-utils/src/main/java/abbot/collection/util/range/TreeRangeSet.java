package abbot.collection.util.range;


import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

import java.io.Serializable;
import java.util.*;

import static com.google.common.collect.BoundType.CLOSED;
import static com.google.common.collect.BoundType.OPEN;

/**
 * Implementation of {@link RangeSet} backed by an {@link java.util.TreeSet}.
 *
 * The {@link java.util.TreeSet} enables this implementation to have a O(log N) lookup within the set via the contains operation.
 *
 * This class will also combine ranges that overlap to minimize the total ranges with the set.  For example, integer ranges
 * [1..6] and [4..20] will be combined into a single range [1..20] to minimize storage and lookup speed. Because of this
 * add and removes are generally O(log N) with worse case of O(N)
 *
 * @param <T>
 */
public class TreeRangeSet<T extends Comparable<T>> implements RangeSet<T>, Serializable {

    private static final TreeSet emptyTreeSet = new TreeSet();

    @SuppressWarnings("unchecked")
    private final TreeSet<Range<T>> treeSet = new TreeSet<Range<T>>(RangeComparators.lowerOnlyComparator());

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return treeSet.size();
    }

    @Override
    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    private Range<T> lowerBound(Range<T> tRange) {

        if (!tRange.hasLowerBound())
            return treeSet.first();

        Range<T> endrange = treeSet.floor(Ranges.singleton(tRange.lowerEndpoint()));
        return (endrange == null ? treeSet.first() : endrange);
    }

    private Range<T> upperBound(Range<T> tRange) {

        if (!tRange.hasUpperBound())
            return treeSet.last();

        Range<T> endrange = treeSet.floor(Ranges.singleton(tRange.upperEndpoint()));
        return (endrange == null ? treeSet.first() : endrange);
    }

    @SuppressWarnings("unchecked")
    private NavigableSet<Range<T>> intersectingRanges(Range<T> tRange) {

        if (treeSet.isEmpty())
            return emptyTreeSet;

        Range<T> lowerBound = lowerBound(tRange);
        Range<T> upperBound = upperBound(tRange);

        /** note this will still return ranges that are not really intersecting due to the behaviour of isConnected.
         * For example [1,4] will be included if the range given is (4,6].  This should be accounted for when using this
         * method.
         */
        return treeSet.subSet(lowerBound, lowerBound.isConnected(tRange), upperBound, upperBound.isConnected(tRange));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(Range<T> tRange) {
        if (tRange == null || tRange.isEmpty())
            return false;

        for (Iterator<Range<T>> i = intersectingRanges(tRange).iterator(); i.hasNext(); ) {
            tRange = tRange.span(i.next());
            i.remove();
        }

        return treeSet.add(tRange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(RangeSet<T> tRangeSet) {
        boolean modified = false;
        for (Range<T> tRange : tRangeSet)
            modified |= add(tRange);

        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Iterable<? extends Range<T>> tRanges) {
        boolean modified = false;
        for (Range<T> tRange : tRanges)
            modified |= add(tRange);

        return modified;
    }

    @SuppressWarnings("unchecked")
    private void addLowerRemainder(Range<T> current, Range<T> toRemove) {
        if (RangeComparators.lowerOnlyComparator().compare(current, toRemove) >= 0)
            return;

        if (current.hasLowerBound()) {
            add(Ranges.range(
                    current.lowerEndpoint(),
                    current.lowerBoundType(),
                    toRemove.lowerEndpoint(),
                    toRemove.lowerBoundType() == CLOSED ? OPEN : CLOSED
            ));
        } else {
            add(Ranges.upTo(
                    toRemove.lowerEndpoint(),
                    toRemove.lowerBoundType() == CLOSED ? OPEN : CLOSED
            ));
        }
    }

    @SuppressWarnings("unchecked")
    private void addUpperRemainder(Range<T> current, Range<T> toRemove) {
        if (RangeComparators.upperOnlyComparator().compare(current, toRemove) <= 0)
            return;

        if (current.hasUpperBound()) {
            add(Ranges.range(
                    toRemove.upperEndpoint(),
                    toRemove.upperBoundType() == CLOSED ? OPEN : CLOSED,
                    current.upperEndpoint(),
                    current.upperBoundType()
            ));

        } else {
            add(Ranges.downTo(
                    toRemove.upperEndpoint(),
                    toRemove.upperBoundType() == CLOSED ? OPEN : CLOSED
            ));
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Range<T> tRange) {

        if (tRange == null || tRange.isEmpty())
            return false;

        boolean modified = false;
        //accounts for 2;

        Range<T> lower = null;
        Range<T> upper = null;
        NavigableSet<Range<T>> intersecting = intersectingRanges(tRange);

        if (intersecting.size() == 1) {
            lower = upper = intersecting.pollFirst();
            modified = true;
        } else if (intersecting.size() > 1) {
            lower = intersecting.pollFirst();
            upper = intersecting.pollLast();
            for (Iterator<Range<T>> i = intersecting.iterator(); i.hasNext(); ) {
                i.next();
                i.remove();
            }
            modified = true;
        }

        //Add back any remaining portions of the outlying ranges back to the set.
        if (lower != null && upper != null) {
            addLowerRemainder(lower, tRange);
            addUpperRemainder(upper, tRange);
        }

        return modified;
    }

    @Override
    public boolean remove(RangeSet<T> rangeSet) {
        boolean modified = false;
        for (Range<T> tRange : rangeSet)
            modified |= remove(tRange);

        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Iterable<? extends Range<T>> tRanges) {
        boolean modified = false;
        for (Range<T> tRange : tRanges)
            modified |= remove(tRange);

        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T item) {
        Range<T> lowerEndpoint = treeSet.floor(Ranges.singleton(item));
        return lowerEndpoint != null && lowerEndpoint.contains(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Iterable<? extends T> items) {
        for (T item : items)
            if (!contains(item))
                return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean encloses(Range<T> range) {
        Range<T> lowerEndpoint = treeSet.floor(range);
        return lowerEndpoint != null && lowerEndpoint.encloses(range);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeRangeSet<T> complement() {
        TreeRangeSet<T> complement = new TreeRangeSet<T>();
        complement.add(Ranges.<T>all());
        complement.remove(this);
        return complement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        treeSet.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Range<T>> iterator() {
        return treeSet.iterator();
    }

    public Iterator<Range<T>> descendingIterator() {
        return treeSet.descendingIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<Range<T>> asSet() {
        return Collections.unmodifiableSortedSet(treeSet);
    }

    public SortedSet<Range<T>> asDescendingSet() {
        return Collections.unmodifiableSortedSet(treeSet.descendingSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Range<T> range : this)
            sb.append(range);

        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeRangeSet that = (TreeRangeSet) o;

        return treeSet.equals(that.treeSet);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return treeSet.hashCode();
    }
}
