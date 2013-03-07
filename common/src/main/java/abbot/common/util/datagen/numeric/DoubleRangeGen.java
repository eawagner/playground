package abbot.common.util.datagen.numeric;


import abbot.common.util.datagen.Generator;

import java.util.Random;

public class DoubleRangeGen implements Generator<Double> {

    double start = 0;
    double end = Double.MAX_VALUE;

    Random random = new Random();


    @Override
    public Double generate() {
        if (start > end)
            return start;

        return (random.nextDouble() * (end - start)) + start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

}
