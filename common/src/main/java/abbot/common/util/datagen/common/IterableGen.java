package abbot.common.util.datagen.common;


import abbot.common.util.datagen.Generator;
import com.google.common.collect.AbstractIterator;

import java.util.Iterator;

public class IterableGen<T> implements Generator<Iterable<T>> {

    Generator<T> seedGenerator = new NullGen<T>();

    private Iterator<T> makeIterator() {
        return new AbstractIterator<T>() {
            @Override
            protected T computeNext() {
                return seedGenerator.generate();
            }
        };
    }

    @Override
    public Iterable<T> generate() {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return makeIterator();
            }
        };
    }

    public void setSeedGenerator(Generator<T> seedGenerator) {
        this.seedGenerator = seedGenerator;
    }
}
