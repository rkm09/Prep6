package daily.medium;


public class CanPartition416 {
    public static void main(String[] args) {
        int[] nums = {1,5,11,5};
        System.out.println(canPartition(nums));
    }

//    optimised bottom up dp; time: O(n.m), space: O(m) [m - subset sum, n - number of elements]
    public static boolean canPartition(int[] nums) {
        int totalSum = 0;
//        find the sum of all array elements
        for(int num : nums)
            totalSum += num;
//        if the total sum is odd, it cannot be partitioned into equal sum subsets
        if(totalSum % 2 != 0)
            return false;
        int subsetSum = totalSum / 2;
        boolean[] dp = new boolean[subsetSum + 1];
        dp[0] = true;
        for(int curr : nums) {
            for(int j = subsetSum ; j >= curr ; j--) {
                dp[j] |= dp[j - curr];
            }
        }
        return dp[subsetSum];
    }
}

/*
Given an integer array nums, return true if you can partition the array into two subsets such that the sum of the elements in both subsets is equal or false otherwise.
Example 1:
Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].
Example 2:
Input: nums = [1,2,3,5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.

Constraints:
1 <= nums.length <= 200
1 <= nums[i] <= 100
 */