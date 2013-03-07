package abbot.common.util.datagen.common;

import abbot.common.util.datagen.Generator;

/**
 * Generates and returns the save value each time generate is called.
 * @param <T>
 */
public class StaticValueGen<T> implements Generator<T> {

    T value;

    /**
     * {@inheritDoc}
     */
    @Override
    public T generate() {
        return value;
    }

    /**
     * Sets the value that the generator will use.
     * @param value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Uses a generator to generate the value that will be used.
     * @param generator
     */
    public void setValue(Generator<T> generator) {
        setValue(generator.generate());
    }
}
