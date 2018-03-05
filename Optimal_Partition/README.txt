To Run:
java -jar <path to Optimal_Partition.jar> <path to input text file> <path to output text file>
-The input text file must exist and should be populated wiht Integers.
-The output text file will be created if it doesn't exist already (will overwrite)

Example use:
java -jar out/artifacts/Optimal_Partition_jar/Optimal_Partition.jar input.txt output.txt

-------------------------------------------------------------------------------------------
My algorithm for this Optimal Partition problem will make the most use out of every bin
meaning some bins may be empty. These empty bins, however, if filled with elements
from other bins, would not change the result of minimizing the maximum number of items
in each bin.
For example:
An input of:
3
1
1
1
1
3
3
5
5
Creates 3 bins of:
1 1 1 1
3 3 5 5

-Where the 3rd bin is empty
This could also be partitioned like:
1 1 1 1
3 3
5 5
-But that does not change the max size of a bin - 4
-So my algoirthm goes with the first option, maximizing use of every bin
