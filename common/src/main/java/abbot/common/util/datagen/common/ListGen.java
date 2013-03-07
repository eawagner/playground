package abbot.common.util.datagen.common;

import abbot.common.util.datagen.Generator;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Iterables.limit;
import static com.google.common.collect.Lists.newArrayList;

public class ListGen<T> implements Generator<Collection<T>> {

    IterableGen<T> iterableGen = new IterableGen<T>();
    int numElements = 0;

    @Override
    public List<T> generate() {
        if (numElements <=0)
            return Collections.emptyList();

        return newArrayList(limit(iterableGen.generate(), numElements));


    }

    public void setSeedGenerator(Generator<T> seedGenerator) {
        iterableGen.setSeedGenerator(seedGenerator);
    }

    public void setNumElements(int numElements) {
        this.numElements = numElements;
    }
}
