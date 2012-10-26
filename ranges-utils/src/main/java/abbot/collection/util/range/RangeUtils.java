package abbot.collection.util.range;

import com.google.common.collect.Ranges;

public class RangeUtils {


    public static <T extends Comparable<T>> RangeSet<T> compliment(RangeSet<T> rangeSet) {
        RangeSet<T> compliment;
        if (rangeSet instanceof DiscreteRangeSet)
            compliment = new DiscreteTreeRangeSet<T>(((DiscreteRangeSet<T>)rangeSet).getDiscreteDomain());
        else
            compliment = new TreeRangeSet<T>();

        compliment.add(Ranges.<T>all());

        compliment.remove(rangeSet);
        return compliment;
    }


}
