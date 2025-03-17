package daily.easy;

import java.util.HashMap;
import java.util.Map;

public class DivideArray2206 {
    public static void main(String[] args) {
        int[] nums = {3,2,3,2,2,2};
        System.out.println(divideArray(nums));
    }

//    def; map; time: O(n), space: O(n)
    public static boolean divideArray(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();
        for(int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        for(int val : freq.values()) {
            if(val % 2 != 0) return false;
        }
        return true;
    }

//    def; freq counter; time: O(n), space: O(1) [fastest]
    public static boolean divideArray1(int[] nums) {
        int[] freq = new int[501];
        for(int num : nums)
            freq[num]++;
        for(int cnt : freq) {
            if(cnt % 2 != 0) return false;
        }
        return true;
    }
}

/*
You are given an integer array nums consisting of 2 * n integers.
You need to divide nums into n pairs such that:
Each element belongs to exactly one pair.
The elements present in a pair are equal.
Return true if nums can be divided into n pairs, otherwise return false.
Example 1:
Input: nums = [3,2,3,2,2,2]
Output: true
Explanation:
There are 6 elements in nums, so they should be divided into 6 / 2 = 3 pairs.
If nums is divided into the pairs (2, 2), (3, 3), and (2, 2), it will satisfy all the conditions.
Example 2:
Input: nums = [1,2,3,4]
Output: false
Explanation:
There is no way to divide nums into 4 / 2 = 2 pairs such that the pairs satisfy every condition.

Constraints:
nums.length == 2 * n
1 <= n <= 500
1 <= nums[i] <= 500
 */