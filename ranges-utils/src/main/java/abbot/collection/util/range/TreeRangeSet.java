package abbot.collection.util.range;


import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

import java.io.Serializable;
import java.util.*;

public class TreeRangeSet<T extends Comparable> implements RangeSet<T>, Serializable {

    private TreeSet<Range<T>> treeSet = new TreeSet<Range<T>>(RangeComparators.<T>lowerOnlyComparator());

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return treeSet.size();
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

    private Set<Range<T>> intersectingRanges(Range<T> tRange) {

        if (treeSet.isEmpty())
            return Collections.emptySet();

        Range<T> lowerBound = lowerBound(tRange);
        Range<T> upperBound = upperBound(tRange);

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
    public boolean addAll(Collection<? extends Range<T>> tRanges) {
        boolean modified = false;
        for (Range<T> tRange : tRanges)
            modified |= add(tRange);

        return modified;
    }

    private void addLowerRemainder(Range<T> current, Range<T> toRemove) {
        if (RangeComparators.<T>lowerOnlyComparator().compare(current, toRemove) >= 0)
            return;

        if (current.hasLowerBound()) {
            add(Ranges.range(
                    current.lowerEndpoint(),
                    current.lowerBoundType(),
                    toRemove.lowerEndpoint(),
                    toRemove.lowerBoundType() == BoundType.CLOSED ? BoundType.OPEN : BoundType.CLOSED
            ));
        } else {
            if (toRemove.lowerBoundType() == BoundType.CLOSED)
                add(Ranges.lessThan(toRemove.lowerEndpoint()));
            else
                add(Ranges.atMost(toRemove.lowerEndpoint()));
        }
    }
    private void addUpperRemainder(Range<T> current, Range<T> toRemove) {
        if (RangeComparators.<T>upperOnlyComparator().compare(current, toRemove) <= 0)
            return;

        if (current.hasUpperBound()) {
            add(Ranges.range(
                    toRemove.upperEndpoint(),
                    toRemove.lowerBoundType() == BoundType.CLOSED ? BoundType.OPEN : BoundType.CLOSED,
                    current.upperEndpoint(),
                    current.upperBoundType()
            ));

        } else {
            if (toRemove.upperBoundType() == BoundType.CLOSED)
                add(Ranges.greaterThan(toRemove.upperEndpoint()));
            else
                add(Ranges.atLeast(toRemove.upperEndpoint()));
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Range<T> tRange) {

        /**
         * Need to handle 4 situations. "< >" represents range being removed
         *
         * 1.  < {} {} {} > easy
         * 2.  {} <> {} easy
         * 3.  {<} {} {>} not too bad
         * 4.  { <> } difficult with current Range api.
         *
         */

//        if (tRange == null || tRange.isEmpty())
//            return false;
//
//        boolean modified = false;
//        //accounts for 2;
//        for (Iterator<Range<T>> i = intersectingRanges(tRange).iterator(); i.hasNext(); ) {
//            Range<T> current = i.next();
//            modified = true;
//            i.remove();
//            //accounts for 1
//            if (!tRange.encloses(current)) {
//                //accounts for 3-4
//                addLowerRemainder(current, tRange);
//                addUpperRemainder(current, tRange);
//            }
//
//        }
//
//        return modified;

        throw new UnsupportedOperationException("Not yet supported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<? extends Range<T>> tRanges) {
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
        if (lowerEndpoint == null)
            return false;

        return lowerEndpoint.contains(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<? extends T> items) {
        for (T item : items)
            if (!contains(item))
                return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Range<T> range) {
        Range<T> lowerEndpoint = treeSet.floor(range);
        if (lowerEndpoint == null)
            return false;

        return lowerEndpoint.encloses(range);
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
        return Collections.unmodifiableSortedSet(treeSet.descendingSet());
    }

    public SortedSet<Range<T>> asDescendingSet() {
        return Collections.unmodifiableSortedSet(treeSet.descendingSet());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Range<T> range : this)
            sb.append(range);

        return sb.toString();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
