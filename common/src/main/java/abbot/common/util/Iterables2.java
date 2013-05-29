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
package abbot.common.util;

import java.util.Iterator;
import java.util.Queue;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;

public class Iterables2 {

    /**
     * Wraps any iterable into a generic iterable object.  This is useful for using complex iterables such as FluentIterable
     * with libraries that use reflection to determine bean definitions such as Jackson.
     * @param iterable
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> simpleIterable(final Iterable<T> iterable) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return iterable.iterator();
            }
        };
    }

    /**
     * Simple method to return an empty iterable.
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> emptyIterable() {
        return emptyList();
    }

    /**
     * Creates an iterable with a single value.
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> singletonIterable(T data) {
        return singleton(data);
    }

    /**
     * Generates an iterable that will drain a queue by consistently polling the latest item.
     * @param queue
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> drainingIterable(final Queue<T> queue) {
        return fromIterator(
                new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        return queue.size() != 0;
                    }

                    @Override
                    public T next() {
                        return queue.poll();
                    }

                    @Override
                    public void remove() {
                        queue.remove();
                    }
                }
        );
    }

    /**
     * Creates an iterable from an iterator.
     * @param iterator
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> fromIterator(final Iterator<T> iterator) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return iterator;
            }
        };
    }
}
