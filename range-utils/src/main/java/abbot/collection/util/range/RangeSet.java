package abbot.collection.util.range;

import com.google.common.collect.Range;

import java.util.Set;


public interface RangeSet<T extends Comparable<T>> extends Iterable<Range<T>> {

    /**
     * Retrieves the size of the range set
     * @return size of the range set.
     */
    int size();

    /**
     * Returns true if there are not ranges in the RangeSet
     * @return true if there are no ranges, false otherwise
     */
    boolean isEmpty();

    /**
     * Adds a range to the range set.  If there are any overlapping ranges caused by the newly added range, they will
     * be consolidated into a single range.
     * @param tRange
     * @return true if this range set was modified, false otherwise.
     */
    boolean add(Range<T> tRange);

    /**
     * Adds a range set to the range set.  If there are any overlapping ranges caused by the newly added ranges, they will
     * be consolidated into a single range.
     * @param tRangeSet
     * @return true if this range set was modified, false otherwise.
     */
    boolean add(RangeSet<T> tRangeSet);

    /**
     * Adds a collections of ranges to the range set.  If there are any overlapping ranges caused by any of the newly
     * added ranges, they will be consolidated.
     * @param tRanges
     * @return true if this range set was modified, false otherwise.
     */
    boolean addAll(Iterable<? extends Range<T>> tRanges);

    /**
     * Removes a range from this range set.
     * @param tRange
     * @return true if this range set was modified, false otherwise.
     */
    boolean remove(Range<T> tRange);

    /**
     * Removes a range from this range set.
     * @param tRangeSet
     * @return true if this range set was modified, false otherwise.
     */
    boolean remove(RangeSet<T> tRangeSet);


    /**
     * Removes all of the ranges from this range set.
     * @param tRanges
     * @return true if this range set was modified, false otherwise.
     */
    boolean removeAll(Iterable<? extends Range<T>> tRanges);

    /**
     * Determines if this range set contains the provided item.
     * @param item
     * @return True if any range contains the item, false otherwise.
     */
    boolean contains(T item);

    /**
     * Determines if this range set contains every item.
     * @param items
     * @return True if any range contains each item, false otherwise.
     */
    boolean containsAll(Iterable<? extends T> items);

    /**
     * Determines if this range set contains the provided range.
     * @param range
     * @return True if the range is fully contain within the set, false otherwise.
     */
    boolean encloses(Range<T> range);

    /**
     * Returns the complement of the current range set.
     * @return
     */
    RangeSet<T> complement();

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
