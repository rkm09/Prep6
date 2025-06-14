package daily.easy;

public class MaxDiff2566 {
    public static void main(String[] args) {
//        int num = 11891;
        int num = 90;
        System.out.println(minMaxDifference(num));
    }

//    greedy; time: O(lognum), space: O(lognum)
    public static int minMaxDifference(int num) {
        String str = Integer.toString(num), minStr = str;
        int pos = 0;
        while(pos < str.length() && str.charAt(pos) == '9') {
            pos++;
        }
        if(pos < str.length()) {
            str = str.replace(str.charAt(pos), '9');
        }
        minStr = minStr.replace(minStr.charAt(0), '0');
        return Integer.parseInt(str) - Integer.parseInt(minStr);
    }
}

/*
You are given an integer num. You know that Bob will sneakily remap one of the 10 possible digits (0 to 9) to another digit.
Return the difference between the maximum and minimum values Bob can make by remapping exactly one digit in num.
Notes:
When Bob remaps a digit d1 to another digit d2, Bob replaces all occurrences of d1 in num with d2.
Bob can remap a digit to itself, in which case num does not change.
Bob can remap different digits for obtaining minimum and maximum values respectively.
The resulting number after remapping can contain leading zeroes.
Example 1:
Input: num = 11891
Output: 99009
Explanation:
To achieve the maximum value, Bob can remap the digit 1 to the digit 9 to yield 99899.
To achieve the minimum value, Bob can remap the digit 1 to the digit 0, yielding 890.
The difference between these two numbers is 99009.
Example 2:
Input: num = 90
Output: 99
Explanation:
The maximum value that can be returned by the function is 99 (if 0 is replaced by 9) and the minimum value that can be returned by the function is 0 (if 9 is replaced by 0).
Thus, we return 99.

Constraints:
1 <= num <= 108
 */

/*
Complexity Analysis
Let num be the given number.
Time complexity: O(lognum).
To generate the minimum and maximum values, we need to traverse each digit of num. Since the number of digits in num is O(lognum), the time required is also O(lognum).
Space complexity: O(lognum).
Similarly, to generate the minimum and maximum values, we need to store each digit of num, requiring O(lognum) space.
 */