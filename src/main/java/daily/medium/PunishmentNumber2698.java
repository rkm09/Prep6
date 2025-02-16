package daily.medium;

public class PunishmentNumber2698 {
    public static void main(String[] args) {
        System.out.println(punishmentNumber(10));
    }

//    recursion of strings; time: O(n.2^log10n), space: (log10n)
    public static int punishmentNumber(int n) {
        int squaredNum, res = 0;
//        iterate through the numbers in the range of [1,n]
        for(int currentNum = 1 ; currentNum <= n ; currentNum++) {
            squaredNum = currentNum * currentNum;
//            check if valid partition can be done, if so add the squared number to the result
            if(isValidPartition(String.valueOf(squaredNum), currentNum))
                res += squaredNum;
        }
        return res;
    }

    private static boolean isValidPartition(String numString, int target) {
//        valid partition found
        if(numString.isEmpty() && target == 0)
            return true;
//        invalid partition
        if(target < 0)
            return false;
//        recursively check all possible partitions
        for(int index = 0 ; index < numString.length() ; index++) {
            String leftString = numString.substring(0, index + 1);
            String rightString = numString.substring(index + 1);
            int leftNum = Integer.parseInt(leftString);
            if(isValidPartition(rightString, target - leftNum))
                return true;
        }
        return false;
    }
}

/*
Given a positive integer n, return the punishment number of n.
The punishment number of n is defined as the sum of the squares of all integers i such that:
1 <= i <= n
The decimal representation of i * i can be partitioned into contiguous substrings such that the sum of the integer values of these substrings equals i.
Example 1:
Input: n = 10
Output: 182
Explanation: There are exactly 3 integers i in the range [1, 10] that satisfy the conditions in the statement:
- 1 since 1 * 1 = 1
- 9 since 9 * 9 = 81 and 81 can be partitioned into 8 and 1 with a sum equal to 8 + 1 == 9.
- 10 since 10 * 10 = 100 and 100 can be partitioned into 10 and 0 with a sum equal to 10 + 0 == 10.
Hence, the punishment number of 10 is 1 + 81 + 100 = 182
Example 2:
Input: n = 37
Output: 1478
Explanation: There are exactly 4 integers i in the range [1, 37] that satisfy the conditions in the statement:
- 1 since 1 * 1 = 1.
- 9 since 9 * 9 = 81 and 81 can be partitioned into 8 + 1.
- 10 since 10 * 10 = 100 and 100 can be partitioned into 10 + 0.
- 36 since 36 * 36 = 1296 and 1296 can be partitioned into 1 + 29 + 6.
Hence, the punishment number of 37 is 1 + 81 + 100 + 1296 = 1478
Constraints:
1 <= n <= 1000
 */

/*
String Recursion:
Let n represent an integer in the range [1, n].
Time Complexity: O(n⋅2^log10(n))
We iterate through n integers only once. For each integer, we recursively traverse all the possible ways to split the number. The number of recursion calls is dependent on how many times we have to partition a number, n. This is proportional to the number of digits in the squared number, which can be calculated as log
10(n^2), or simply log10(n).
At each digit, we are given the option to either break a partition or continue adding to the partition, giving us 2 options at each digit. The number of times we have to make this decision to exhaust all possible options is proportional to the number of digits in the squared number. As a result, this leads to a time complexity for the recursive function of O(2
log10(n)).
Since we iterate through this process n times, we multiply this time complexity by a factor of n. This leads to an overall time complexity of O(n⋅2log10(n)).
Space Complexity: O(log10(n))
The space complexity is determined by the recursion stack.
The depth of the recursion stack is proportional to the current integer. In the worst case, a recursive call can iterate within itself when each digit is explored individually in a partition.
As a result, the max size of the stack is proportional to the number of digits in the squared number, which can be calculated as log
10(n^2). This leads to a time complexity for the recursive stack of O(log10(n^2)), which can be simplified to O(log10(n)).
 */