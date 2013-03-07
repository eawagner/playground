package abbot.common.util.datagen.common;


import abbot.common.util.datagen.Generator;

import java.util.List;
import java.util.Random;

import static java.util.Collections.emptyList;

public class SelectionGen<T> implements Generator<T> {

    List<T> values = emptyList();
    int currentIdx = 0;
    boolean randomize = false;
    Random random = new Random();

    /**
     * {@inheritDoc}
     */
    @Override
    public T generate() {
        if (values.isEmpty())
            return null;

        if (randomize) {
            return values.get(random.nextInt(values.size()));
        } else {
            T value = values.get(currentIdx);

            currentIdx = currentIdx++ % values.size();

            return value;
        }
    }

    /**
     * Sets the values that will be used for selection.
     * @param values
     */
    public void setValues(List<T> values) {
        this.values = values;
    }

    /**
     * Uses a generator to generate the values that will be used for selection.
     * @param generator
     */
    public void setValues(Generator<List<T>> generator) {
        setValues(generator.generate());
    }

    /**
     * If set to true the selection of the elements to pick from will be randomized.
     * @param randomize
     */
    public void setRandomize(boolean randomize) {
        this.randomize = randomize;
    }
}
