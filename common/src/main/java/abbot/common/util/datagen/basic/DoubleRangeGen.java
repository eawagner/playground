package abbot.common.util.datagen.basic;


import abbot.common.util.datagen.Generator;

import java.util.Random;

/**
 * Will generate a random double in a given range.
 */
public class DoubleRangeGen implements Generator<Double> {

    double start = 0;
    double end = Double.MAX_VALUE;

    Random random = new Random();

    /**
     * {@inheritDoc}
     */
    @Override
    public Double generate() {
        if (start > end)
            return start;

        return (random.nextDouble() * (end - start)) + start;
    }

    /**
     * Start of the range.
     * @param start
     */
    public void setStart(double start) {
        this.start = start;
    }

    /**
     * Use a generator to generate the start value of the range.
     * @param generator
     */
    public void setStart(Generator<Double> generator) {
        setStart(generator.generate());
    }

    /**
     * End of the range.
     * @param end
     */
    public void setEnd(double end) {
        this.end = end;
    }

    /**
     * Use a generator to generate the end value of the range.
     * @param generator
     */
    public void setEnd(Generator<Double> generator) {
        setEnd(generator.generate());
    }
}
