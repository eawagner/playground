/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements. See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
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
