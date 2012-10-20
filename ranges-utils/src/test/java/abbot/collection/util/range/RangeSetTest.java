package abbot.collection.util.range;

import com.google.common.collect.Ranges;
import org.junit.Test;

import static junit.framework.Assert.*;

public class RangeSetTest {


    @Test
    public void simpleTest() {

        TreeRangeSet<Integer> intervalSet = new TreeRangeSet<Integer>();

        intervalSet.add(Ranges.closed(3, 9));
        intervalSet.add(Ranges.closed(13, 19));
        intervalSet.add(Ranges.closed(5, 6));
        intervalSet.add(Ranges.closed(10, 12));
        intervalSet.add(Ranges.closed(33,99));
        intervalSet.add(Ranges.greaterThan(90));
        intervalSet.add(Ranges.lessThan(-80));
        intervalSet.add(Ranges.closed(0, 1));
        intervalSet.add(Ranges.closed(89, 90));
        intervalSet.add(Ranges.closed(20, 33));


        assertFalse(intervalSet.contains(2));
        assertFalse(intervalSet.contains(-80));
        assertTrue(intervalSet.contains(-81));
        assertTrue(intervalSet.contains(0));
        assertTrue(intervalSet.contains(12));
        assertTrue(intervalSet.contains(9));
        assertTrue(intervalSet.contains(7));
    }

    @Test
    public void testEmptyRangeAdd() {
        TreeRangeSet<Integer> intervalSet = new TreeRangeSet<Integer>();

        intervalSet.add(Ranges.closed(0,3));
        intervalSet.add(Ranges.closed(10,13));

        //empty ranges
        intervalSet.add(Ranges.openClosed(5,5));
        intervalSet.add(Ranges.closedOpen(5,5));

        assertEquals(2, intervalSet.size());
        assertFalse(intervalSet.contains(5));

    }

    @Test
    public void testRangeConsolidation() {
        TreeRangeSet<Integer> intervalSet = new TreeRangeSet<Integer>();

        intervalSet.add(Ranges.closed(0,3));
        intervalSet.add(Ranges.closed(10,13));

        assertEquals(2, intervalSet.size());
        assertFalse(intervalSet.contains(5));

        intervalSet.add(Ranges.open(-2, 0));
        intervalSet.add(Ranges.open(13,15));

        assertEquals(2, intervalSet.size());
        assertFalse(intervalSet.contains(5));

        intervalSet.add(Ranges.atMost(-2));
        intervalSet.add(Ranges.atLeast(15));

        assertEquals(2, intervalSet.size());
        assertFalse(intervalSet.contains(5));

        //link them into one set
        intervalSet.add(Ranges.open(3,10));

        assertEquals(1, intervalSet.size());
        assertTrue(intervalSet.contains(5));
    }

}
