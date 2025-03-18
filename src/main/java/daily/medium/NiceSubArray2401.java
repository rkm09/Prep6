package daily.medium;

public class NiceSubArray2401 {
    public static void main(String[] args) {
        int[] nums = {1,3,8,48,10};
        System.out.println(longestNiceSubarray(nums));
    }

//    sliding window; time: O(n), space:O(1)
    public static int longestNiceSubarray(int[] nums) {
        int usedBits = 0, maxLength = 0;  // track bits in the current window with 'usedBits'
        int windowStart = 0, n = nums.length;
        for(int windowEnd = 0 ; windowEnd < n ; windowEnd++) {
//            resolve any bit conflicts
            while((usedBits & nums[windowEnd]) != 0) {
                usedBits ^= nums[windowStart];
                windowStart++;
            }
//            add current number to the window
            usedBits |= nums[windowEnd];
//            update max length if longer window is found
            maxLength = Math.max(maxLength, windowEnd - windowStart + 1);
        }
        return maxLength;
    }
}

/*
You are given an array nums consisting of positive integers.
We call a subarray of nums nice if the bitwise AND of every pair of elements that are in different positions in the subarray is equal to 0.
Return the length of the longest nice subarray.
A subarray is a contiguous part of an array.
Note that subarrays of length 1 are always considered nice.

Example 1:
Input: nums = [1,3,8,48,10]
Output: 3
Explanation: The longest nice subarray is [3,8,48]. This subarray satisfies the conditions:
- 3 AND 8 = 0.
- 3 AND 48 = 0.
- 8 AND 48 = 0.
It can be proven that no longer nice subarray can be obtained, so we return 3.
Example 2:
Input: nums = [3,1,5,11,13]
Output: 1
Explanation: The length of the longest nice subarray is 1. Any subarray of length 1 can be chosen.

Constraints:
1 <= nums.length <= 105
1 <= nums[i] <= 109
 */