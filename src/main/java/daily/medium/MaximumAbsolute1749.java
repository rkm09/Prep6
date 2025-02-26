package daily.medium;

public class MaximumAbsolute1749 {
    public static void main(String[] args) {
        int[] nums = {1,-3,2,3,-4};
        System.out.println(maxAbsoluteSum(nums));
    }

//    greedy prefix sum - shorter; time: O(n), space: O(1)
    public static int maxAbsoluteSum(int[] nums) {
        int minPrefixSum = 0, maxPrefixSum = 0;
        int prefixSum = 0;
        for(int num : nums) {
            prefixSum += num;
            minPrefixSum = Math.min(minPrefixSum, prefixSum);
            maxPrefixSum = Math.max(maxPrefixSum, prefixSum);
        }
        return maxPrefixSum - minPrefixSum;
    }
}

/*
You are given an integer array nums. The absolute sum of a subarray [numsl, numsl+1, ..., numsr-1, numsr] is abs(numsl + numsl+1 + ... + numsr-1 + numsr).
Return the maximum absolute sum of any (possibly empty) subarray of nums.
Note that abs(x) is defined as follows:
If x is a negative integer, then abs(x) = -x.
If x is a non-negative integer, then abs(x) = x.
Example 1:
Input: nums = [1,-3,2,3,-4]
Output: 5
Explanation: The subarray [2,3] has absolute sum = abs(2+3) = abs(5) = 5.
Example 2:
Input: nums = [2,-5,1,-4,3,-2]
Output: 8
Explanation: The subarray [-5,1,-4] has absolute sum = abs(-5+1-4) = abs(-8) = 8.

Constraints:
1 <= nums.length <= 105
-104 <= nums[i] <= 104
 */

/*
Greedy prefix sum - shorter
This approach is similar to the previous one where we utilized a prefix sum array to calculate the sum of any subarray between two indices. In the earlier method, we considered each index as the endpoint of a subarray and computed the maximum possible sum by tracking the minimum and maximum prefix sums encountered up to that point.
To maximize the absolute subarray sum, we need to find two prefix sums — one that is as large as possible and another that is as small as possible. This is because the sum of any subarray between indices i and j can be expressed as prefixSum[j] - prefixSum[i]. The greater the difference between prefixSum[j] and prefixSum[i], the larger the absolute sum of the subarray. Thus, to maximize this difference, prefixSum[j] should be as large as possible, while prefixSum[i] should be as small as possible.
With this observation, we iterate through the array while keeping track of two values: minPrefixSum, which stores the smallest prefix sum encountered so far, and maxPrefixSum, which stores the largest prefix sum. As we process each element, we update these values accordingly. Once we have finished iterating, the absolute difference between maxPrefixSum and minPrefixSum gives us the maximum absolute subarray sum.
One important note is that, instead of initializing maxPrefixSum to INT_MIN as is commonly done, we initialize it to 0. This is because the empty subarray, which has a sum of 0, is a valid subarray. In cases where all elements in the array are negative, initializing maxPrefixSum to 0 ensures that it correctly reflects the scenario where no positive subarray sum exists.
 */