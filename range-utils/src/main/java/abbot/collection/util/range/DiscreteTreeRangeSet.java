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
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

/**
 * An implementation of {@link TreeRangeSet} which handles all required operations to work with {@link DiscreteDomain} ranges
 * such as {@link com.google.common.collect.DiscreteDomains#integers()}.  All of the ranges used within this implementation
 * will be canonicalized using the {@link DiscreteDomain} provided.
 * @param <T>
 */
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
    public boolean encloses(Range<T> range) {
        return super.encloses(range.canonical(discreteDomain));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DiscreteTreeRangeSet<T> complement() {
        DiscreteTreeRangeSet<T> complement = new DiscreteTreeRangeSet<T>(discreteDomain);
        complement.add(Ranges.<T>all());
        complement.remove(this);
        return complement;
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

        return discreteDomain.equals(that.discreteDomain);

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
