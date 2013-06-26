package abbot.collection.util.range;

import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static com.google.common.collect.BoundType.CLOSED;
import static com.google.common.collect.BoundType.OPEN;
import static com.google.common.collect.Ranges.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static junit.framework.Assert.*;

public class RangeSetTest {


    @Test
    public void simpleTest() {

        TreeRangeSet<Integer> rangeSet = new DiscreteTreeRangeSet<Integer>(DiscreteDomains.integers());

        rangeSet.add(closed(3, 9));
        rangeSet.add(closed(13, 19));
        rangeSet.add(closed(5, 6));
        rangeSet.add(closed(10, 12));
        rangeSet.add(closed(33, 99));
        rangeSet.add(greaterThan(90));
        rangeSet.add(lessThan(-80));
        rangeSet.add(closed(0, 1));
        rangeSet.add(closed(89, 90));
        rangeSet.add(closed(20, 33));


        assertFalse(rangeSet.contains(2));
        assertFalse(rangeSet.contains(-80));
        assertTrue(rangeSet.contains(-81));
        assertTrue(rangeSet.contains(0));
        assertTrue(rangeSet.contains(12));
        assertTrue(rangeSet.contains(9));
        assertTrue(rangeSet.contains(7));

        System.out.println(rangeSet);
        System.out.println(rangeSet.complement());
    }

    @Test
    public void testEmptyRangeAdd() {
        TreeRangeSet<Integer> rangeSet = new DiscreteTreeRangeSet<Integer>(DiscreteDomains.integers());

        rangeSet.add(closed(0, 3));
        rangeSet.add(closed(10, 13));

        //empty ranges
        rangeSet.add(openClosed(5, 5));
        rangeSet.add(closedOpen(5, 5));

        assertEquals(2, rangeSet.size());
        assertFalse(rangeSet.contains(5));

        System.out.println(rangeSet);

    }

    @Test
    public void testRangeConsolidation() {
        TreeRangeSet<Integer> rangeSet = new DiscreteTreeRangeSet<Integer>(DiscreteDomains.integers());

        rangeSet.add(closed(0, 3));
        rangeSet.add(closed(10, 13));

        assertEquals(2, rangeSet.size());
        assertFalse(rangeSet.contains(5));

        rangeSet.add(open(-2, 0));
        rangeSet.add(open(13, 15));

        assertEquals(2, rangeSet.size());
        assertFalse(rangeSet.contains(5));

        rangeSet.add(atMost(-2));
        rangeSet.add(atLeast(15));

        assertEquals(2, rangeSet.size());
        assertFalse(rangeSet.contains(5));

        //link them into one set
        rangeSet.add(open(3, 10));

        assertEquals(1, rangeSet.size());
        assertTrue(rangeSet.contains(5));

        System.out.println(rangeSet);
    }

    @Test
    public void testRemove() {
        TreeRangeSet<Integer> rangeSet = new DiscreteTreeRangeSet<Integer>(DiscreteDomains.integers());
        rangeSet.add(Ranges.<Integer>all());

        assertEquals(1, rangeSet.size());
        assertTrue(rangeSet.contains(1));

        rangeSet.remove(singleton(1));
        assertEquals(2, rangeSet.size());
        assertFalse(rangeSet.contains(1));

        rangeSet.remove(singleton(1));
        assertEquals(2, rangeSet.size());
        assertFalse(rangeSet.contains(1));

        rangeSet.remove(atMost(0));
        assertEquals(1, rangeSet.size());
        assertFalse(rangeSet.contains(0));

        rangeSet.remove(atLeast(2));
        assertEquals(0, rangeSet.size());
        assertFalse(rangeSet.contains(2));

        System.out.println(rangeSet);
    }

    @Ignore
    @Test
    public void speedTest() {
        int maxNumberRanges = 1000000;
        double maxVariance = 1000;
        double maxIntervalSize = .01;
        ArrayList<Range<Double>> ranges = new ArrayList<Range<Double>>(maxNumberRanges);
        ArrayList<Double> toCheck = new ArrayList<Double>(maxNumberRanges);
        Random random = new Random();
        for (int i = 0;i < maxNumberRanges; i++) {
            double base = random.nextDouble() * maxNumberRanges / maxVariance;
            ranges.add(range(
                    base, (random.nextBoolean() ? CLOSED : OPEN),
                    base + (random.nextDouble() * maxIntervalSize), (random.nextBoolean() ? CLOSED : OPEN)
            ));

            toCheck.add(random.nextDouble() * maxNumberRanges / maxVariance);
        }

        generateTimes(ranges, toCheck, 10);
    }

    private void generateTimes(ArrayList<Range<Double>> ranges, ArrayList<Double> toCheck, int numTimes) {

        long totalAddTime = 0;
        long totalCheckTime = 0;

        for (int i = 0; i < numTimes;i++) {

            RangeSet<Double> treeRangeSet = new TreeRangeSet<Double>();
            long startTime = System.nanoTime();

            treeRangeSet.addAll(ranges);

            long stopTime = System.nanoTime();
            totalAddTime += (stopTime - startTime);
            System.out.println("Add run time " + MILLISECONDS.convert(stopTime - startTime, NANOSECONDS) + " for " + treeRangeSet.size() + " ranges");

            startTime = System.nanoTime();
            for (Double value : toCheck)
                treeRangeSet.contains(value);

            stopTime = System.nanoTime();
            totalCheckTime += (stopTime - startTime);
            System.out.println("Check run time " + MILLISECONDS.convert(stopTime - startTime, NANOSECONDS));



            startTime = System.nanoTime();
            RangeSet<Double> complement = treeRangeSet.complement();
            System.out.println(complement.complement().size());
            stopTime = System.nanoTime();
            System.out.println("Compliment time " + MILLISECONDS.convert(stopTime - startTime, NANOSECONDS) + " with " + complement.size() + " ranges");

            System.out.println();
        }

        System.out.println("Add Time: " + MILLISECONDS.convert(totalAddTime / numTimes, NANOSECONDS) + " (avg) , " + MILLISECONDS.convert(totalAddTime, NANOSECONDS) + " (total)");
        System.out.println("Check Time: " + MILLISECONDS.convert(totalCheckTime / numTimes, NANOSECONDS) + " (avg) , " + MILLISECONDS.convert(totalCheckTime, NANOSECONDS) + " (total)");
    }

}
