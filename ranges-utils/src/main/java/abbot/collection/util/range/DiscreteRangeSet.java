package abbot.collection.util.range;


import com.google.common.collect.DiscreteDomain;

public interface DiscreteRangeSet<T extends Comparable<T>> {

    public DiscreteDomain<T> getDiscreteDomain();

}
