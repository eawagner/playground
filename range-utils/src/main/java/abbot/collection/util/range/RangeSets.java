package abbot.collection.util.range;


import com.google.common.collect.DiscreteDomain;

public class RangeSets {

    public static <T extends Comparable<T>> RangeSet<T> newRangeSet() {
        return new TreeRangeSet<T>();
    }

    public static <T extends Comparable<T>> RangeSet<T> newRangeSet(DiscreteDomain<T> domain) {
        return new DiscreteTreeRangeSet<T>(domain);
    }

    public static <T extends Comparable<T>> RangeSet<T> union(RangeSet<T> ... rangeSets) {
        TreeRangeSet<T> result = new TreeRangeSet<T>();
        for (RangeSet<T> rangeSet : rangeSets)
            result.add(rangeSet);

        return result;
    }

    public static <T extends Comparable<T>> RangeSet<T> union(DiscreteDomain<T> domain, RangeSet<T> ... rangeSets) {
        DiscreteTreeRangeSet<T> result = new DiscreteTreeRangeSet<T>(domain);
        for (RangeSet<T> rangeSet : rangeSets)
            result.add(rangeSet);

        return result;
    }

    public static <T extends Comparable<T>> RangeSet<T> intersection(RangeSet<T> ... rangeSets) {
        TreeRangeSet<T> result = new TreeRangeSet<T>();
        for (RangeSet<T> rangeSet : rangeSets)
            result.add(rangeSet.complement());

        return result.complement();
    }

    public static <T extends Comparable<T>> RangeSet<T> intersection(DiscreteDomain<T> domain, RangeSet<T> ... rangeSets) {
        DiscreteTreeRangeSet<T> result = new DiscreteTreeRangeSet<T>(domain);
        for (RangeSet<T> rangeSet : rangeSets)
            result.add(rangeSet.complement());

        return result.complement();
    }
}
