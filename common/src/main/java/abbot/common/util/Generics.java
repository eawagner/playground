package abbot.common.util;


public class Generics {

    /**
     * Provides similar behaviour (superficially) to dynamic_cast in C++.
     *
     * If the object can be cast to the generic type it will be returned, otherwise it will return null.
     *
     * @param o Object to be cast.
     * @param <T> The type of object to be cast to.
     * @return The object cast to the specified type or null if it can't be cast.
     */
    @SuppressWarnings("unchecked")
    public static <T> T dynamicCast(Object o) {
        try {
            if (o != null)
                return (T) o;

        } catch (Throwable ignored) {}

        return null;
    }



}
