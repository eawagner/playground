package abbot.common.util.datagen;


/**
 * Common interface for generating a value of type T.
 * @param <T>
 */
public interface Generator<T> {

    /**
     * Generates a new value.
     * @return
     */
    T generate();

}
