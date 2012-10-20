package abbot.collection.util.range;

import com.google.common.collect.Range;

import java.util.Comparator;

public class RangeComparators {


    public static <T extends Comparable> Comparator<Range<T>> lowerOnlyComparator() {
        return new Comparator<Range<T>>() {
            @Override
            public int compare(Range<T> range1, Range<T> range2) {
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
        };
    }

}
