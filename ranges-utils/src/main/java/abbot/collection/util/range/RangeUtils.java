package abbot.collection.util.range;

import com.google.common.collect.Ranges;

public class RangeUtils {


    public static <T extends Comparable<T>> RangeSet<T> compliment(RangeSet<T> rangeSet) {
        RangeSet<T> compliment = new TreeRangeSet<T>();
        compliment.add(Ranges.<T>all());

        compliment.remove(rangeSet);
        return compliment;
    }

}
