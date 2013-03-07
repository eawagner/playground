package abbot.common.util.datagen.common;

import abbot.common.util.datagen.Generator;

public class NullGen<T> implements Generator<T> {
    @Override
    public T generate() {
        return null;
    }
}
