package abbot.common.util.datagen.common;


import abbot.common.util.datagen.Generator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SelectionGen<T> implements Generator<T> {

    List<T> values = Collections.emptyList();
    int currentIdx = 0;
    boolean randomize = false;
    Random random = new Random();

    public void setValues(List<T> values) {
        this.values = values;
    }

    public void setRandomize(boolean randomize) {
        this.randomize = randomize;
    }

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
}
