package abbot.common.util.datagen.common;

import abbot.common.util.datagen.Generator;

public class StaticValueGen<T> implements Generator<T> {

    T value;

    public void setValue(T value) {
        this.value = value;
    }

    public void setValue(Generator<T> generator) {
        this.value = generator.generate();
    }

    @Override
    public T generate() {
        return value;
    }
}
