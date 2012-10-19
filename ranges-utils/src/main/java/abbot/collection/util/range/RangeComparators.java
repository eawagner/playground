package abbot.collection.util.range;

import com.google.common.collect.Range;

import java.util.Comparator;

public class RangeComparators {


    public static <T extends Comparable> Comparator<Range<T>> getLowerBoundComparator() {
        return new LowerBoundComparator<T>();
    }

    private static class LowerBoundComparator<T extends Comparable> implements Comparator<Range<T>> {

        private int compareLower(Range<T> range1, Range<T> range2) {
            if (range1.hasLowerBound()) {
                if (range2.hasLowerBound())
                    return range1.lowerEndpoint().compareTo(range2.lowerEndpoint());
                else
                    return 1;
            } else {
                if (range2.hasLowerBound())
                    return -1;
                else
                    return 0;
            }
        }

        private int compareUpper(Range<T> range1, Range<T> range2) {
            if (range1.hasUpperBound()) {
                if (range2.hasUpperBound())
                    return range1.upperEndpoint().compareTo(range2.upperEndpoint());
                else
                    return -1;
            } else {
                if (range2.hasUpperBound())
                    return 1;
                else
                    return 0;
            }
        }

        @Override
        public int compare(Range<T> range1, Range<T> range2) {

            int lowerCompare = compareLower(range1, range2);

            if (lowerCompare == 0)
                return compareUpper(range1, range2);

            return lowerCompare;
        }
    }



}
