package daily.medium;

import java.util.HashMap;
import java.util.Map;

public class MaxSubArray576 {
    public static void main(String[] args) {
        int[] nums = {1,-1,5,-2,3};
        System.out.println(maxSubArrayLen(nums, 3));
    }

//    prefix sum + hashmap; time: O(n), space: O(n)
    public static int maxSubArrayLen(int[] nums, int k) {
        int n = nums.length, longestSubArray = 0, prefixSum = 0;
        Map<Integer, Integer> indices = new HashMap<>();
        for(int i = 0 ; i < n ; i++) {
            prefixSum += nums[i];
//            check if all the numbers seen so far sum to k
            if(prefixSum == k)
                longestSubArray = i + 1;
//            if any sub array seen so far sums to k, then update the length of the longest sub array
            if(indices.containsKey(prefixSum - k))
                longestSubArray = Math.max(longestSubArray, i - indices.get(prefixSum - k));
//            only add the current index if the prefix sum is not already present
            if(!indices.containsKey(prefixSum))
                indices.put(prefixSum, i);
        }
        return longestSubArray;
    }
}

/*
Given an integer array nums and an integer k, return the maximum length of a subarray that sums to k. If there is not one, return 0 instead.
Example 1:
Input: nums = [1,-1,5,-2,3], k = 3
Output: 4
Explanation: The subarray [1, -1, 5, -2] sums to 3 and is the longest.
Example 2:
Input: nums = [-2,-1,2,1], k = 1
Output: 2
Explanation: The subarray [-1, 2] sums to 1 and is the longest.
Example 3:
Input: nums = [1,0,-1], k = -1
Output: 2
Constraints:
1 <= nums.length <= 2 * 105
-104 <= nums[i] <= 104
-109 <= k <= 109
 */