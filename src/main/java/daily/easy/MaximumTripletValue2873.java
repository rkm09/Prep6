package daily.easy;

public class MaximumTripletValue2873 {
    public static void main(String[] args) {
        int[] nums = {12,6,1,2,7};
        System.out.println(maximumTripletValue(nums));
    }

//    greedy (single pass); time: O(n), space: O(1)
    public static long maximumTripletValue(int[] nums) {
        int n = nums.length, imax = 0, dmax = 0;
        long res = 0;
        for(int k = 0 ; k < n ; k++) {
            res = Math.max(res, (long) dmax * nums[k]);
            dmax = Math.max(dmax, imax - nums[k]);
            imax = Math.max(imax, nums[k]);
        }
        return res;
    }

//    greedy + prefix sum; time: O(n), space: O(n)
    public static long maximumTripletValue1(int[] nums) {
        int n = nums.length;
        long res = 0;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], nums[i - 1]);
            rightMax[n - 1 - i] = Math.max(rightMax[n - i], nums[n - i]);
        }
        for (int j = 1; j < n - 1; j++) {
            res = Math.max(res, (long) (leftMax[j] - nums[j]) * rightMax[j]);
        }
        return res;
    }

//    brute force; time: O(n^3), space: O(1)
    public static long maximumTripletValueN(int[] nums) {
        int n = nums.length;
        long res = 0;
        for(int i = 0 ; i < n ; i++) {
            for(int j = i + 1 ; j < n ; j++) {
                for(int k = j + 1 ; k < n ; k++) {
                    res = Math.max(res, (long) (nums[i] - nums[j]) * nums[k]);
                }
            }
        }
        return res;
    }
}


/*
You are given a 0-indexed integer array nums.
Return the maximum value over all triplets of indices (i, j, k) such that i < j < k. If all such triplets have a negative value, return 0.
The value of a triplet of indices (i, j, k) is equal to (nums[i] - nums[j]) * nums[k].
Example 1:
Input: nums = [12,6,1,2,7]
Output: 77
Explanation: The value of the triplet (0, 2, 4) is (nums[0] - nums[2]) * nums[4] = 77.
It can be shown that there are no ordered triplets of indices with a value greater than 77.
Example 2:
Input: nums = [1,10,3,4,19]
Output: 133
Explanation: The value of the triplet (1, 2, 4) is (nums[1] - nums[2]) * nums[4] = 133.
It can be shown that there are no ordered triplets of indices with a value greater than 133.
Example 3:
Input: nums = [1,2,3]
Output: 0
Explanation: The only ordered triplet of indices (0, 1, 2) has a negative value of (nums[0] - nums[1]) * nums[2] = -3. Hence, the answer would be 0.

Constraints:
3 <= nums.length <= 100
1 <= nums[i] <= 106
 */

/*
Greedy + Prefix Suffix Array
Let the length of the array nums be n. According to the value formula (nums[i]−nums[j])×nums[k], it can be known that when j is fixed, the maximum value of the triplet is achieved when nums[i] and nums[k] respectively take the maximum values from [0,j) and [j+1,n). We use leftMax[j] and rightMax[j] to maintain the maximum value of the prefix [0,j) and the maximum value of the suffix [j+1,n), respectively. We then enumerate j in order, calculate the value (leftMax[j]−nums[j])×rightMax[j], and return the maximum value (if all values are negative, return 0).
Greedy (single pass):
if we fix k, then the value of the triplet is maximized when nums[i]−nums[j] takes the maximum value. We can use imax to maintain the maximum value of nums[i] and dmax to maintain the maximum value of nums[i]−nums[j]. During the enumeration of k, update dmax and imax.
 */