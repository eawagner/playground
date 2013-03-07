package abbot.common.util.datagen.basic;


import abbot.common.util.datagen.Generator;

import java.util.Random;

/**
 * Will generate integers in a range.  The default behaviour is to simply increment, but it can be set to randomize the
 * integers generated.
 */
public class IntRangeGen implements Generator<Integer> {

    int start = 0;
    int end = Integer.MAX_VALUE;

    int current = 0;
    boolean randomize = false;
    Random random = new Random();

    /**
     * {@inheritDoc}
     */
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

    /**
     * Start of the range.
     * @param start
     */
    public void setStart(int start) {
        this.start = start;
        this.current = start;
    }

    /**
     * Use a generator to generate the start value of the range.
     * @param generator
     */
    public void setStart(Generator<Integer> generator) {
        setStart(generator.generate());
    }

    /**
     * End of the range.
     * @param end
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * Use a generator to generate the end value of the range.
     * @param generator
     */
    public void setEnd(Generator<Integer> generator) {
        setEnd(generator.generate());
    }

    /**
     * If set to true the generated values will be randomized between the start and end points in the range.
     * @param randomize
     */
    public void setRandomize(boolean randomize) {
        this.randomize = randomize;
    }

}
