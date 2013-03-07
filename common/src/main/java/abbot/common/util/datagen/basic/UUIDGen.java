package abbot.common.util.datagen.basic;


import abbot.common.util.datagen.Generator;

import java.util.UUID;

/**
 * Generates a new random UUID each time it is called.
 */
public class UUIDGen implements Generator<String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
