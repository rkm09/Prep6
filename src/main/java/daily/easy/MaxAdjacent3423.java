package daily.easy;

public class MaxAdjacent3423 {
    public static void main(String[] args) {
        int[] nums = {1,2,4};
        System.out.println(maxAdjacentDistance(nums));
    }

//    traversal; time: O(n), space: O(1)
    public static int maxAdjacentDistance(int[] nums) {
        int n = nums.length;
        int maxDist =  Math.abs(nums[n - 1] - nums[0]);
        for(int i = 1 ; i < n ; i++) {
            int diff = Math.abs(nums[i] - nums[i - 1]);
            maxDist = Math.max(maxDist, diff);
        }
        return maxDist;
    }
}

/*
Given a circular array nums, find the maximum absolute difference between adjacent elements.
Note: In a circular array, the first and last elements are adjacent.
Example 1:
Input: nums = [1,2,4]
Output: 3
Explanation:
Because nums is circular, nums[0] and nums[2] are adjacent. They have the maximum absolute difference of |4 - 1| = 3.
Example 2:
Input: nums = [-5,-10,-5]
Output: 5
Explanation:
The adjacent elements nums[0] and nums[1] have the maximum absolute difference of |-5 - (-10)| = 5.

Constraints:
2 <= nums.length <= 100
-100 <= nums[i] <= 100
 */