import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

public class OptimalPartitionTest {

    //Test that partition works with the example input
    @Test
    public void exampleTest() {
        OptimalPartition p = new OptimalPartition();
        List<Integer> input = new ArrayList<Integer>();
        Integer[] inputs = new Integer[]{5, 2, 3, 6, 1, 6};
        input.addAll(Arrays.asList(inputs));
        int binCount = 3;
        List<List<Integer>> output = p.partition(input, binCount);
        assertArrayEquals(new Integer[]{1, 2}, output.get(0).toArray());
        assertArrayEquals(new Integer[]{3, 5}, output.get(1).toArray());
        assertArrayEquals(new Integer[]{6, 6}, output.get(2).toArray());
    }

    //Test that partition works with uneven bins - input count does not fit into bins evenly
    @Test
    public void unevenBinsTest() {
        OptimalPartition p = new OptimalPartition();
        List<Integer> input = new ArrayList<Integer>();
        Integer[] inputs = new Integer[]{5, 2, 3, 6, 1, 6, 2};
        input.addAll(Arrays.asList(inputs));
        int binCount = 3;
        List<List<Integer>> output = p.partition(input, binCount);
        assertArrayEquals(new Integer[]{1, 2, 2}, output.get(0).toArray());
        assertArrayEquals(new Integer[]{3, 5}, output.get(1).toArray());
        assertArrayEquals(new Integer[]{6, 6}, output.get(2).toArray());
    }

    //Test that partition works with an in-between skip (will stop filling current bin if it
    //is more optimal to fill the next bin)
    @Test
    public void skipBinTest() {
        OptimalPartition p = new OptimalPartition();
        List<Integer> input = new ArrayList<Integer>();
        Integer[] inputs = new Integer[]{5, 2, 3, 6, 1, 6, 6};
        input.addAll(Arrays.asList(inputs));
        int binCount = 3;
        List<List<Integer>> output = p.partition(input, binCount);
        assertArrayEquals(new Integer[]{1, 2, 3}, output.get(0).toArray());
        assertArrayEquals(new Integer[]{5}, output.get(1).toArray());
        assertArrayEquals(new Integer[]{6, 6, 6}, output.get(2).toArray());
    }

    //Test that partition will make empty bins if extra bins are unnecessary to achieve
    //optimal partitioning of the ints
    @Test
    public void emptyBinTest() {
        OptimalPartition p = new OptimalPartition();
        List<Integer> input = new ArrayList<Integer>();
        Integer[] inputs = new Integer[]{1, 1, 1, 1, 3, 3, 5, 5};
        input.addAll(Arrays.asList(inputs));
        int binCount = 3;
        List<List<Integer>> output = p.partition(input, binCount);
        assertArrayEquals(new Integer[]{1, 1, 1, 1}, output.get(0).toArray());
        assertArrayEquals(new Integer[]{3, 3, 5, 5}, output.get(1).toArray());
        assertArrayEquals(new Integer[]{}, output.get(2).toArray());
    }

    //Test that partition will work on a lesser bin count of 2
    @Test
    public void lesserBinCountTest() {
        OptimalPartition p = new OptimalPartition();
        List<Integer> input = new ArrayList<Integer>();
        Integer[] inputs = new Integer[]{1, 5, 7, 3, 4, 8, 2, 9, 5};
        input.addAll(Arrays.asList(inputs));
        int binCount = 2;
        List<List<Integer>> output = p.partition(input, binCount);
        assertArrayEquals(new Integer[]{1, 2, 3, 4}, output.get(0).toArray());
        assertArrayEquals(new Integer[]{5, 5, 7, 8, 9}, output.get(1).toArray());
    }

    //Test that partition will work on a greater bin count of 4
    @Test
    public void greaterBinCountTest() {
        OptimalPartition p = new OptimalPartition();
        List<Integer> input = new ArrayList<Integer>();
        Integer[] inputs = new Integer[]{1, 2, 2, 2, 3, 5, 5, 5, 5, 6, 6, 7, 7};
        input.addAll(Arrays.asList(inputs));
        int binCount = 4;
        List<List<Integer>> output = p.partition(input, binCount);
        assertArrayEquals(new Integer[]{1, 2, 2, 2}, output.get(0).toArray());
        assertArrayEquals(new Integer[]{3}, output.get(1).toArray());
        assertArrayEquals(new Integer[]{5, 5, 5, 5}, output.get(2).toArray());
        assertArrayEquals(new Integer[]{6, 6, 7, 7}, output.get(3).toArray());
    }

    //Test that partition will fill greater than binSize if it has to (still optimal partition)
    @Test
    public void fillGreaterThanBinSizeTest() {
        OptimalPartition p = new OptimalPartition();
        List<Integer> input = new ArrayList<Integer>();
        Integer[] inputs = new Integer[]{1, 2, 2, 2, 3, 5, 5, 5, 5, 6, 6, 7, 7, 8};
        input.addAll(Arrays.asList(inputs));
        int binCount = 4;
        List<List<Integer>> output = p.partition(input, binCount);
        assertArrayEquals(new Integer[]{1, 2, 2, 2}, output.get(0).toArray());
        assertArrayEquals(new Integer[]{3}, output.get(1).toArray());
        assertArrayEquals(new Integer[]{5, 5, 5, 5}, output.get(2).toArray());
        assertArrayEquals(new Integer[]{6, 6, 7, 7, 8}, output.get(3).toArray());
    }

    //Test that partition works with negatives
    @Test
    public void negativesTest() {
        OptimalPartition p = new OptimalPartition();
        List<Integer> input = new ArrayList<Integer>();
        Integer[] inputs = new Integer[]{-8, -3, -5, -10, 4, 6, 8, 0, -6};
        input.addAll(Arrays.asList(inputs));
        int binCount = 3;
        List<List<Integer>> output = p.partition(input, binCount);
        assertArrayEquals(new Integer[]{-10, -8, -6}, output.get(0).toArray());
        assertArrayEquals(new Integer[]{-5, -3, 0}, output.get(1).toArray());
        assertArrayEquals(new Integer[]{4, 6, 8}, output.get(2).toArray());
    }

    //Test that partition will work on large input, input value, and bin count
    //-Random gen of 30 integers from -100 to 100
    @Test
    public void monsterPartitionTest() {
        OptimalPartition p = new OptimalPartition();
        List<Integer> input = new ArrayList<Integer>();
        Integer[] inputs = new Integer[]{-27, -68, -6, -73, 44, 37, -24, -1, -77, 71, 72,
                -57, -57, 95, 78, -94, 27, -7, -41, 44, 8, 58, 39, -21, 100, 23, 0, 93, 14, 48};
        input.addAll(Arrays.asList(inputs));
        int binCount = 10;
        List<List<Integer>> output = p.partition(input, binCount);
        assertArrayEquals(new Integer[]{-94, -77, -73}, output.get(0).toArray());
        assertArrayEquals(new Integer[]{-68, -57, -57}, output.get(1).toArray());
        assertArrayEquals(new Integer[]{-41, -27, -24}, output.get(2).toArray());
        assertArrayEquals(new Integer[]{-21, -7, -6}, output.get(3).toArray());
        assertArrayEquals(new Integer[]{-1, 0, 8}, output.get(4).toArray());
        assertArrayEquals(new Integer[]{14, 23, 27}, output.get(5).toArray());
        assertArrayEquals(new Integer[]{37, 39}, output.get(6).toArray());
        assertArrayEquals(new Integer[]{44, 44, 48}, output.get(7).toArray());
        assertArrayEquals(new Integer[]{58, 71, 72}, output.get(8).toArray());
        assertArrayEquals(new Integer[]{78, 93, 95, 100}, output.get(9).toArray());
    }

    //Test the functionality of the partitioner helper
    @Test
    public void partitionerTest() {
        OptimalPartition p = new OptimalPartition();
        List<Integer> ints = new ArrayList<Integer>();
        Integer[] intsArray = new Integer[]{1, 2, 3, 4, 4, 6, 6, 7, 8, 9};
        ints.addAll(Arrays.asList(intsArray));
        List<Integer> partitioned = p.partitioner(ints, 4, 7);
        assertArrayEquals(new Integer[]{4, 4, 6, 6, 7}, partitioned.toArray());
        partitioned = p.partitioner(ints, 3, 4);
        assertArrayEquals(new Integer[]{3, 4, 4}, partitioned.toArray());
    }

    //Test the functionality of the maxDuplicates helper
    @Test
    public void maxDuplicatesTest() {
        OptimalPartition p = new OptimalPartition();
        List<Integer> ints = new ArrayList<Integer>();
        Integer[] intsArray = new Integer[]{5, 2, 3, 6, 1};
        ints.addAll(Arrays.asList(intsArray));
        Collections.sort(ints);
        int max = p.maxDuplicates(ints);
        assertEquals(1, max);
        ints.add(6);
        Collections.sort(ints);
        max = p.maxDuplicates(ints);
        assertEquals(2, max);
        ints.add(3);
        ints.add(3);
        Collections.sort(ints);
        max = p.maxDuplicates(ints);
        assertEquals(3, max);
        ints.add(3);
        Collections.sort(ints);
        max = p.maxDuplicates(ints);
        assertEquals(4, max);
    }

    //Test the functionality of the duplicateNum helper
    @Test
    public void duplicateNumTest() {
        OptimalPartition p = new OptimalPartition();
        List<Integer> ints = new ArrayList<Integer>();
        Integer[] intsArray = new Integer[]{5, 2, 3, 6, 1, 6};
        ints.addAll(Arrays.asList(intsArray));
        Collections.sort(ints);
        int num = p.duplicateNum(ints, 0);
        assertEquals(1, num);
        num = p.duplicateNum(ints, 4);
        assertEquals(2, num);
        ints.add(6);
        Collections.sort(ints);
        num = p.duplicateNum(ints, 4);
        assertEquals(3, num);
        ints.add(6);
        Collections.sort(ints);
        num = p.duplicateNum(ints, 5);
        assertEquals(3, num);
    }


}
