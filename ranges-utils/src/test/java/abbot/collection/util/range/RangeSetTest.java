package abbot.collection.util.range;

import com.google.common.collect.*;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.*;

public class RangeSetTest {


    @Test
    public void simpleTest() {

        TreeRangeSet<Integer> rangeSet = new TreeRangeSet<Integer>();

        rangeSet.add(Ranges.closed(3, 9));
        rangeSet.add(Ranges.closed(13, 19));
        rangeSet.add(Ranges.closed(5, 6));
        rangeSet.add(Ranges.closed(10, 12));
        rangeSet.add(Ranges.closed(33,99));
        rangeSet.add(Ranges.greaterThan(90));
        rangeSet.add(Ranges.lessThan(-80));
        rangeSet.add(Ranges.closed(0, 1));
        rangeSet.add(Ranges.closed(89, 90));
        rangeSet.add(Ranges.closed(20, 33));


        assertFalse(rangeSet.contains(2));
        assertFalse(rangeSet.contains(-80));
        assertTrue(rangeSet.contains(-81));
        assertTrue(rangeSet.contains(0));
        assertTrue(rangeSet.contains(12));
        assertTrue(rangeSet.contains(9));
        assertTrue(rangeSet.contains(7));

        System.out.println(rangeSet);
    }

    @Test
    public void testEmptyRangeAdd() {
        TreeRangeSet<Integer> rangeSet = new TreeRangeSet<Integer>();

        rangeSet.add(Ranges.closed(0, 3));
        rangeSet.add(Ranges.closed(10, 13));

        //empty ranges
        rangeSet.add(Ranges.openClosed(5, 5));
        rangeSet.add(Ranges.closedOpen(5, 5));

        assertEquals(2, rangeSet.size());
        assertFalse(rangeSet.contains(5));

        System.out.println(rangeSet);

    }

    @Test
    public void testRangeConsolidation() {
        TreeRangeSet<Integer> rangeSet = new TreeRangeSet<Integer>();

        rangeSet.add(Ranges.closed(0,3));
        rangeSet.add(Ranges.closed(10,13));

        assertEquals(2, rangeSet.size());
        assertFalse(rangeSet.contains(5));

        rangeSet.add(Ranges.open(-2, 0));
        rangeSet.add(Ranges.open(13,15));

        assertEquals(2, rangeSet.size());
        assertFalse(rangeSet.contains(5));

        rangeSet.add(Ranges.atMost(-2));
        rangeSet.add(Ranges.atLeast(15));

        assertEquals(2, rangeSet.size());
        assertFalse(rangeSet.contains(5));

        //link them into one set
        rangeSet.add(Ranges.open(3,10));

        assertEquals(1, rangeSet.size());
        assertTrue(rangeSet.contains(5));

        System.out.println(rangeSet);
    }

    @Ignore
    @Test
    public void testRemove() {
        TreeRangeSet<Integer> rangeSet = new TreeRangeSet<Integer>();
        rangeSet.add(Ranges.<Integer>all());

        System.out.println(Ranges.lessThan(1).isConnected(Ranges.singleton(1)));

        rangeSet.remove(Ranges.singleton(1).canonical(DiscreteDomains.integers()));
        rangeSet.remove(Ranges.singleton(1).canonical(DiscreteDomains.integers()));
        rangeSet.remove(Ranges.atMost(1).canonical(DiscreteDomains.integers()));
        rangeSet.remove(Ranges.atLeast(2).canonical(DiscreteDomains.integers()));

        System.out.println(rangeSet);
    }

    @Ignore
    @Test
    public void speedTest() {
        int maxNumberRanges = 10000;
        double maxVariance = 100;
        double maxIntervalSize = .01;
        ArrayList<Range<Double>> ranges = new ArrayList<Range<Double>>(maxNumberRanges);
        ArrayList<Double> toCheck = new ArrayList<Double>(maxNumberRanges);
        Random random = new Random();
        for (int i = 0;i < maxNumberRanges; i++) {
            double base = random.nextDouble() * maxNumberRanges / maxVariance;
            ranges.add(Ranges.range(
                    base, (random.nextBoolean() ? BoundType.CLOSED : BoundType.OPEN),
                    base + (random.nextDouble() * maxIntervalSize), (random.nextBoolean() ? BoundType.CLOSED : BoundType.OPEN)
            ));

            toCheck.add(random.nextDouble() * maxNumberRanges / maxVariance);
        }

        TreeRangeSet<Double> treeRangeSet = new TreeRangeSet<Double>();

        System.out.println();
        long startTime = System.nanoTime();

        treeRangeSet.addAll(ranges);

        System.out.println("Add run time " + TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime,TimeUnit.NANOSECONDS));

        startTime = System.nanoTime();
        for (Double value : toCheck)
            treeRangeSet.contains(value);

        System.out.println("Check run time " + TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime,TimeUnit.NANOSECONDS));
    }

}
