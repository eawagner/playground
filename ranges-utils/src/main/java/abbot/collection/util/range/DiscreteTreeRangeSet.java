package abbot.collection.util.range;


import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

public class DiscreteTreeRangeSet<T extends Comparable<T>> extends TreeRangeSet<T> implements DiscreteRangeSet{
    DiscreteDomain<T> discreteDomain;

    public DiscreteTreeRangeSet(DiscreteDomain<T> discreteDomain) {
        this.discreteDomain = discreteDomain;
    }

    @Override
    public DiscreteDomain<T> getDiscreteDomain() {
        return discreteDomain;
    }

    @Override
    public boolean add(Range<T> tRange) {
        return super.add(tRange.canonical(discreteDomain));
    }

    @Override
    public boolean remove(Range<T> tRange) {
        return super.remove(tRange.canonical(discreteDomain));
    }

    @Override
    public boolean containsAll(Range<T> range) {
        return super.containsAll(range.canonical(discreteDomain));
    }
}
