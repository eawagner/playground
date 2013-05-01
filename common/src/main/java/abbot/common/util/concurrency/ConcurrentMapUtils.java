package abbot.common.util.concurrency;


import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapUtils {


    /**
     * Simple method to retrieve a value from a concurrent hashmap, or generate a value if one was not found.
     * This is useful to avoid the problem with concurrency behaviour such as.
     *
     * <code>
     *  if (map.get(key)== null)
     *      map.put(value)
     * </code>
     *
     * The method is thread-safe non-locking method, that will minimize the number of attempts to generate a new value.
     *
     * @param map The concurrent map to look up a value from
     * @param key The key to the value in the map.
     * @param valueFactory A value factory to be used to generate new value if one does not exist
     * @param <K> Key Type
     * @param <V> Value Type
     * @return A value of type from the map for the given key.  Null, if no key was found and the value factory returns null
     */
    public static <K,V> V getValue(ConcurrentMap<K,V> map, K key, ValueFactory<K,V> valueFactory) {

        V value = map.get(key);
        if (value == null) {
            V newValue = valueFactory.createValue(key);
            if (newValue == null)
                return null;

            value = map.putIfAbsent(key, newValue);
            if (value == null)
                value = newValue;
        }
        return value;

    }


    /**
     * Utility class to create a value for a map based on the provided key.
     * @param <K> Key Type
     * @param <V> Value Type
     */
    public static interface ValueFactory<K,V> {

        /**
         * Creates a new value based on the provided key
         * @param key
         * @return value
         */
        V createValue(K key);

    }

}
