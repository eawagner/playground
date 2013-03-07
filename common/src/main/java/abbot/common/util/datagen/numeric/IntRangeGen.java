package abbot.common.util.datagen.numeric;


import abbot.common.util.datagen.Generator;

import java.util.Random;

public class IntRangeGen implements Generator<Integer> {

    int start = 0;
    int end = Integer.MAX_VALUE;

    int current = 0;
    boolean randomize = false;
    Random random = new Random();


    @Override
    public Integer generate() {
        if (start > end)
            return start;

        if (randomize) {
            return random.nextInt(end - start) + start;
        } else {
            int value = current;

            if (current == end)
                current = start;
            else
                current++;

            return value;
        }
    }

    public void setStart(int start) {
        this.start = start;
        this.current = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setRandomize(boolean randomize) {
        this.randomize = randomize;
    }

}
