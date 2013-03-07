package abbot.common.util.datagen.common;

import abbot.common.util.datagen.Generator;

import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Iterables.limit;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

/**
 * Generates lists of a fixed size that are populated with values from the provided seed generator.
 * @param <T>
 */
public class ListGen<T> implements Generator<Collection<T>> {

    IterableGen<T> iterableGen = new IterableGen<T>();
    int numElements = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> generate() {
        if (numElements <=0)
            return emptyList();

        return newArrayList(limit(iterableGen.generate(), numElements));
    }

    /**
     * Sets the generator used to generate each element in the {@link List}.
     * @param seedGenerator
     */
    public void setSeedGenerator(Generator<T> seedGenerator) {
        iterableGen.setSeedGenerator(seedGenerator);
    }

    /**
     * Sets the number of elements that will appear in each list.
     * @param numElements
     */
    public void setNumElements(int numElements) {
        this.numElements = numElements;
    }
}
