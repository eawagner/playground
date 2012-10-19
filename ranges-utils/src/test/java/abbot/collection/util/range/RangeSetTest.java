package abbot.collection.util.range;

import com.google.common.collect.Ranges;
import org.junit.Test;

import static junit.framework.Assert.*;

public class RangeSetTest {


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
