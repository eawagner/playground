package abbot.common.util.datagen.common;


import abbot.common.util.datagen.Generator;
import com.google.common.collect.AbstractIterator;

import java.util.Iterator;

/**
 * Generates a new iterable with values populated from the provided seed generator.
 * @param <T>
 */
public class IterableGen<T> implements Generator<Iterable<T>> {

    Generator<T> seedGenerator = new StaticValueGen<T>();  //Defaults as null generator

    private Iterator<T> makeIterator() {
        return new AbstractIterator<T>() {
            @Override
            protected T computeNext() {
                return seedGenerator.generate();
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<T> generate() {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return makeIterator();
            }
        };
    }

    /**
     * Sets the generator used to generate each element in the {@link Iterable}.
     * @param seedGenerator
     */
    public void setSeedGenerator(Generator<T> seedGenerator) {
        this.seedGenerator = seedGenerator;
    }
}
