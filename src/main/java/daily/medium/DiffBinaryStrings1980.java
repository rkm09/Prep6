package daily.medium;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DiffBinaryStrings1980 {
    public static void main(String[] args) {
        String[] nums = {"00","10"};
        System.out.println(findDifferentBinaryString(nums));
    }

//    recursively generate all strings; time: O(2^n) [?? O(n) due to optimisation], space: O(n)
    public static String findDifferentBinaryString(String[] nums) {
        Set<String> numSet = new HashSet<>(Arrays.asList(nums));
        return generate(numSet, nums.length, "");
    }

    private static String generate(Set<String> numSet, int n, String currentStr) {
        if(currentStr.length() == n) {
            if(!numSet.contains(currentStr))
                return currentStr;
            return "";
        }
        String addZero = generate(numSet, n, currentStr + "0");
        if(!addZero.isEmpty())
            return addZero;
        return generate(numSet, n, currentStr + "1");
    }
}

/*
Given an array of strings nums containing n unique binary strings each of length n, return a binary string of length n that does not appear in nums. If there are multiple answers, you may return any of them.
Example 1:
Input: nums = ["01","10"]
Output: "11"
Explanation: "11" does not appear in nums. "00" would also be correct.
Example 2
Input: nums = ["00","01"]
Output: "11"
Explanation: "11" does not appear in nums. "10" would also be correct.
Example 3:
Input: nums = ["111","011","001"]
Output: "101"
Explanation: "101" does not appear in nums. "000", "010", "100", and "110" would also be correct.

Constraints:
n == nums.length
1 <= n <= 16
nums[i].length == n
nums[i] is either '0' or '1'.
All the strings of nums are unique.
 */

/*
Approach 1: Recursively Generate All Strings
In the constraints, we see thatn≤16. Given that there are only2^16 = 65536 possible binary strings, it is feasible to generate all of them in an attempt to find one that does not appear in nums.
We will use a recursive function generate(curr)to generate the binary strings. At each function call,curr is the current string we have. First, we check if curr.length = n. If it is, we need to stop adding characters and assess if we have an answer. If curr is in nums, we return an empty string. If it isn't, we return curr.
If curr.length != n, we will add a character. Since we are generating all strings, we will call both generate(curr + "0") and generate(curr + "1"). Note that in our base case, we return an empty string if we did not generate a valid answer. Thus, if either call returns a non-empty string, the value it returns is a valid answer.
As each call to generate creates two more calls, the algorithm will have a time complexity of at leastO(2
n). However, we can implement a crucial optimization. We will first call generate(curr + "0")and store the value in addZero. If addZero is not an empty string, we can immediately return it as the answer without needing to make the additional call to generate(curr + "1"). If addZero is an empty string, it means all possible paths from adding a"0"lead to invalid answers,
and thus generate(curr + "1")must generate a valid answer, since it's guaranteed that a valid answer exists.
Why is this optimization such a big deal? Notice that the length of nums is n. Thus, if we check n + 1 different strings of length n, we will surely find a valid answer. By returning addZero early, we terminate the recursion as soon as we find a valid answer, thus we won't check more than n + 1strings of length n. Without any early returns, we would check 2^n strings of length n.
Additionally, we will convert nums to a hash set prior to starting the recursion, so we can have checks inO(1)time complexity in the base case.
 */