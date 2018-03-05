import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class OptimalPartition {

    /**
     * Given a list of Integers to partition and number of bins to partition into, partition
     * Integers into binCount bins such that the maximum size of any bin is minimized
     *
     * @param ints     - List of Integers to partition
     * @param binCount - Number of bins to partition into
     * @return
     */
    public static List<List<Integer>> partition(List<Integer> ints, int binCount) {
        List<List<Integer>> bins = new ArrayList<List<Integer>>();
        Collections.sort(ints);
        int min = 0;
        int max = 0;
        int binStart = 0;
        int maxDup = maxDuplicates(ints);
        int binSize = (int) (Math.ceil(ints.size() / (double) binCount));
        if (maxDup > binSize) {
            binSize = maxDup;
        }
        for (int i = 0; i < binCount; i++) {
            List<Integer> bin = new ArrayList<Integer>();
            int curBinSize = 0;
            if(binStart < ints.size()) {
                min = ints.get(binStart);
                max = ints.get(binStart);
            }
            for (int j = binStart; j < ints.size(); j++) {
                int num = ints.get(j);
                int numCount = duplicateNum(ints, j);
                if (curBinSize + numCount <= binSize || i == binCount - 1) {
                    binStart += numCount;
                    curBinSize += numCount;
                    j += numCount - 1;
                    max = num;
                } else {
                    break;
                }
            }
            //If first bin or new bin, populate the bin - otherwise will add an empty bin
            if (i > 0) {
                List<Integer> previousBin = bins.get(i - 1);
                if (previousBin.size() > 0) {
                    if (previousBin.get(previousBin.size() - 1) != max) {
                        bin = partitioner(ints, min, max);
                    }
                }
            } else {
                bin = partitioner(ints, min, max);
            }
            bins.add(bin);
        }
        return bins;
    }

    /**
     * Given a min and max Integer, returns a list of Integers in the given list of Integers
     * that are between min and max - inclusive
     *
     * @param ints - List of Integers to search through
     * @param min  - minimum number in ints to add
     * @param max  - maximum number in ints to add
     * @return
     */
    public static List<Integer> partitioner(List<Integer> ints, int min, int max) {
        List<Integer> bin = new ArrayList<Integer>();
        for (int i = 0; i < ints.size(); i++) {
            int num = ints.get(i);
            if (num >= min && num <= max) {
                bin.add(num);
            }
        }
        return bin;
    }

    /**
     * Given a list of Integers, return the maximum number of duplicates in the list
     *
     * @param ints - List of Integers to search through
     * @return
     */
    public static int maxDuplicates(List<Integer> ints) {
        int max = 1;
        int count = 1;
        int dup = ints.get(0);
        for (int i = 1; i < ints.size(); i++) {
            if (ints.get(i) == dup) {
                count += 1;
            } else {
                count = 1;
                dup = ints.get(i);
            }
            if (count > max) {
                max = count;
            }
        }
        return max;
    }

    /**
     * Given a sorted list of Integers and an index to start at, return the number of integers
     * from the starting index that are the same as the starting num
     *
     * @param ints  - List of Integers to search through
     * @param index - Starting index in the list
     * @return
     */
    public static int duplicateNum(List<Integer> ints, int index) {
        int count = 1;
        int duplicate = ints.get(index);
        for (int i = index + 1; i < ints.size(); i++) {
            if (ints.get(i) == duplicate) {
                count += 1;
            } else {
                break;
            }
        }
        return count;
    }


    public static void main(String[] args) {
        Scanner input = null;
        int binCount = 0;
        int maxSize = 0;
        List<Integer> inputNums = new ArrayList<Integer>();
        //If input file doesn't exist, throw FileNotFoundException,
        //otherwise set input file to first argument
        System.out.println("Reading from Input File: " + args[0] + "...");
        try {
            input = new Scanner(new FileReader(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("No input file by the name of: " + args[0]);
            System.exit(0);
        }
        //If input does not have a nextInt, prompt user to populate input,
        // otherwise set binCount to nextInt
        if (input.hasNextInt()) {
            binCount = Integer.parseInt(input.nextLine());
            //If binCount is less than or equal to zero, prompt user for different bin count
            if (binCount <= 0) {
                System.out.println("Invalid # of Bins - Please provide bin count larger than 0");
                System.exit(0);
            }
        } else {
            System.out.println("Empty Input File - Please provide a populated text file to partition");
            System.exit(0);
        }
        //Go through entire input, prompt user of incorrect inputs
        // - if no incorrect inputs, add input into inputNums
        while (input.hasNextLine()) {
            if (input.hasNextInt()) {
                inputNums.add(Integer.parseInt(input.nextLine()));
            } else {
                System.out.println("Input File contains Invalid Input - " +
                        "Please check that all inputs are Integers");
                System.exit(0);
            }
        }
        //If the size of inputNums is 0, prompt user file must contain inputs besides bin count
        if (inputNums.size() == 0) {
            System.out.println("Input File does not contain inputs, only bin count");
            System.exit(0);
        }
        //Partition the input and set return to out
        System.out.println("Partitioning...");
        List<List<Integer>> out = partition(inputNums, binCount);
        for(int i = 0; i < out.size(); i++) {
            if(out.get(i).size() > maxSize) {
                maxSize = out.get(i).size();
            }
        }
        System.out.println("Successfully partitioned " + inputNums.size() + " items into "
                + binCount + " bins! Maximum bin size of: " + maxSize + "!");
        //Write to output file the list of bins, out
        System.out.println("Writing to Output File: " + args[1] + "...");
        Path file = Paths.get(args[1]);
        try (BufferedWriter writer = Files.newBufferedWriter(file, Charset.forName("US-ASCII"))) {
            for(int i = 0; i < out.size(); i++) {
                for(int j = 0; j < out.get(i).size(); j++) {
                    writer.write(Integer.toString(out.get(i).get(j)));
                    if(j != out.get(i).size() - 1) {
                        writer.write(" ");
                    }
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}


