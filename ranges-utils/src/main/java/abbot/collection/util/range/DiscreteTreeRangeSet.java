package abbot.collection.util.range;


import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

public class DiscreteTreeRangeSet<T extends Comparable<T>> extends TreeRangeSet<T>{
    private final DiscreteDomain<T> discreteDomain;

    public DiscreteTreeRangeSet(DiscreteDomain<T> discreteDomain) {
        this.discreteDomain = discreteDomain;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(Range<T> tRange) {
        return super.add(tRange.canonical(discreteDomain));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Range<T> tRange) {
        return super.remove(tRange.canonical(discreteDomain));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Range<T> range) {
        return super.containsAll(range.canonical(discreteDomain));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DiscreteTreeRangeSet<T> complement() {
        DiscreteTreeRangeSet<T> compliment = new DiscreteTreeRangeSet<T>(discreteDomain);
        compliment.add(Ranges.<T>all());
        compliment.remove(this);
        return compliment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DiscreteTreeRangeSet that = (DiscreteTreeRangeSet) o;

        if (!discreteDomain.equals(that.discreteDomain)) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + discreteDomain.hashCode();
        return result;
    }
}
