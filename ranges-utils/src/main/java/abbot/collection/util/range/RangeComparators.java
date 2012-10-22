package abbot.collection.util.range;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

import java.util.Comparator;

public class RangeComparators {

    private static final Comparator<Range<? extends Comparable>> lowerOnly = new Comparator<Range<? extends Comparable>>() {
        @Override
        public int compare(Range<? extends Comparable> range1, Range<? extends Comparable> range2) {
            return compareLower(range1, range2);
        }
    };

    private static final Comparator<Range<? extends Comparable>> upperOnly = new Comparator<Range<? extends Comparable>>() {
        @Override
        public int compare(Range<? extends Comparable> range1, Range<? extends Comparable> range2) {
            return compareUpper(range1, range2);
        }
    };

    private static final Comparator<Range<? extends Comparable>> lowerBiased = new Comparator<Range<? extends Comparable>>() {
        @Override
        public int compare(Range<? extends Comparable> range1, Range<? extends Comparable> range2) {
            int initial = compareLower(range1, range2);
            if (initial != 0)
                return initial;

            return compareUpper(range1, range2);
        }
    };

    private static final Comparator<Range<? extends Comparable>> upperBiased = new Comparator<Range<? extends Comparable>>() {
        @Override
        public int compare(Range<? extends Comparable> range1, Range<? extends Comparable> range2) {
            int initial = compareUpper(range1, range2);
            if (initial != 0)
                return initial;

            return compareLower(range1, range2);
        }
    };

    private static int compareLower(Range<? extends Comparable> range1, Range<? extends Comparable> range2){
        if (range1.hasLowerBound()) {
            if (range2.hasLowerBound()) {
                int compare = range1.lowerEndpoint().compareTo(range2.lowerEndpoint());
                if (compare != 0)
                    return compare;

                //If values are equal then use bound type.  Closed will always be less than open.
                if (range1.lowerBoundType() == range2.lowerBoundType())
                    return 0;
                else if (range1.lowerBoundType() == BoundType.CLOSED)
                    return -1;
                else
                    return 1;
            } else
                return 1;
        } else {
            if (range2.hasLowerBound())
                return -1;
            else
                return 0;
        }
    }

    private static int compareUpper(Range<? extends Comparable> range1, Range<? extends Comparable> range2){
        if (range1.hasUpperBound()) {
            if (range2.hasUpperBound()){
                int compare = range1.upperEndpoint().compareTo(range2.upperEndpoint());
                if (compare != 0)
                    return compare;

                //If values are equal then use bound type.  Closed will always be greater than open.
                if (range1.upperBoundType() == range2.upperBoundType())
                    return 0;
                else if (range1.upperBoundType() == BoundType.CLOSED)
                    return 1;
                else
                    return -1;
            } else
                return -1;
        } else {
            if (range2.hasUpperBound())
                return 1;
            else
                return 0;
        }
    }

    /**
     * Compares only the lower bound of the ranges, ignoring the upper bound.
     * @return -1 (less), 0 (equal), 1 (greater)
     */
    public static Comparator<Range<? extends Comparable>> lowerOnlyComparator() {
        return lowerOnly;
    }

    /**
     * Compares only the upper bound of the ranges, ignoring the lower bound.
     * @return -1 (less), 0 (equal), 1 (greater)
     */
    public static Comparator<Range<? extends Comparable>> upperOnlyComparator() {
        return upperOnly;
    }

    /**
     * Compares the lower bound of the ranges and if equal then uses the upper bound.
     * @return -1 (less), 0 (equal), 1 (greater)
     */
    public static Comparator<Range<? extends Comparable>> lowerBiasedComparator() {
        return lowerBiased;
    }

    /**
     * Compares the upper bound of the ranges and if equal then uses the lower bound.
     * @return -1 (less), 0 (equal), 1 (greater)
     */
    public static Comparator<Range<? extends Comparable>> upperBiasedComparator() {
        return upperBiased;
    }

}
