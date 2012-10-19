package abbot.collection.util.range;


import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

import java.util.*;

public class TreeRangeSet<T extends Comparable> implements RangeSet<T> {

    TreeSet<Range<T>> intervalSet;

    public TreeRangeSet() {
        this(RangeComparators.<T>getLowerBoundComparator());
    }

    public TreeRangeSet(Comparator<Range<T>> comparator) {
        intervalSet = new TreeSet<Range<T>>(comparator);
    }

    @Override
    public int size() {
        return intervalSet.size();
    }

    private Range<T> lowerBound(Range<T> tRange) {

        if (!tRange.hasLowerBound())
            return intervalSet.first();

        Range<T> endrange = intervalSet.floor(Ranges.singleton(tRange.lowerEndpoint()));
        return (endrange == null ? intervalSet.first() : endrange);
    }

    private Range<T> upperBound(Range<T> tRange) {

        if (!tRange.hasUpperBound())
            return intervalSet.last();

        Range<T> endrange = intervalSet.ceiling(Ranges.singleton(tRange.upperEndpoint()));
        return (endrange == null ? intervalSet.last() : endrange);
    }

    private Set<Range<T>> intersectingRanges(Range<T> tRange) {

        if (intervalSet.isEmpty())
            return Collections.emptySet();

        Range<T> lowerBound = lowerBound(tRange);
        Range<T> upperBound = upperBound(tRange);

        return intervalSet.subSet(lowerBound, lowerBound.isConnected(tRange), upperBound, upperBound.isConnected(tRange));
    }

    @Override
    public boolean add(Range<T> tRange) {
        if (tRange == null || tRange.isEmpty())
            return false;

        for (Iterator<Range<T>> i = intersectingRanges(tRange).iterator(); i.hasNext(); ) {
            tRange = tRange.span(i.next());
            i.remove();
        }

        return intervalSet.add(tRange);
    }

    @Override
    public boolean addAll(Collection<? extends Range<T>> tRanges) {
        boolean modified = false;
        for (Range<T> tRange : tRanges)
            modified |= add(tRange);

        return modified;
    }

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
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    public boolean removeAll(Collection<? extends Range<T>> tRanges) {
        boolean modified = false;
        for (Range<T> tRange : tRanges)
            modified |= remove(tRange);

        return modified;
    }

    @Override
    public boolean contains(T item) {
        Range<T> range = intervalSet.lower(Ranges.atLeast(item));
        if (range == null)
            return false;

        return range.contains(item);
    }

    @Override
    public void clear() {
        intervalSet.clear();
    }

    @Override
    public Iterator<Range<T>> iterator() {
        return intervalSet.iterator();
    }

    public Iterator<Range<T>> descendingIterator() {
        return intervalSet.descendingIterator();
    }

    @Override
    public SortedSet<Range<T>> asSet() {
        return Collections.unmodifiableSortedSet(intervalSet.descendingSet());
    }

    public SortedSet<Range<T>> asDescendingSet() {
        return Collections.unmodifiableSortedSet(intervalSet.descendingSet());
    }

}
