package abbot.collection.util.range;

import com.google.common.collect.Range;

import java.util.Collection;
import java.util.Set;


public interface RangeSet<T extends Comparable> extends Iterable<Range<T>> {

    /**
     * Retrieves the size of the range set
     * @return size of the range set.
     */
    int size();

    /**
     * Adds a range to the range set.  If there are any overlapping ranges caused by the newly added range, they will
     * be consolidated into a single range.
     * @param tRange
     * @return true if this range set was modified, false otherwise.
     */
    boolean add(Range<T> tRange);

    /**
     * Adds a collections of ranges to the range set.  If there are any overlapping ranges caused by any of the newly
     * added ranges, they will be consolidated.
     * @param tRanges
     * @return true if this range set was modified, false otherwise.
     */
    boolean addAll(Collection<? extends Range<T>> tRanges);

    /**
     * Removes a range from this range set.
     * @param tRange
     * @return true if this range set was modified, false otherwise.
     */
    boolean remove(Range<T> tRange);

    /**
     * Removes all of the ranges from this range set.
     * @param tRanges
     * @return true if this range set was modified, false otherwise.
     */
    boolean removeAll(Collection<? extends Range<T>> tRanges);

    /**
     * Determines if this range set contains the provided item.
     * @param item
     * @return True if any range contains the item, false otherwise.
     */
    boolean contains(T item);

    /**
     * Clears all ranges from the range set.
     */
    void clear();

    /**
     * Retrieve the range set as a {@link Set} of {@link Range}s
     * @return A set of {@link Range}s
     */
    Set<Range<T>> asSet();

}
