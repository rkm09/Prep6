package daily.easy;

public class MaxCount2529 {
    public static void main(String[] args) {
        int[] nums = {-2,-1,-1,1,2,3};
        System.out.println(maximumCount(nums));
    }

    public static int maximumCount(int[] nums) {
        int n = nums.length;
        if(nums[0] > 0) return n;
        int posLen = 0, negLen = 0;
        for (int num : nums) {
            if(num < 0) negLen++;
            else if (num > 0) posLen++;
        }
        return Math.max(posLen, negLen);
    }

    private static int binSearch(int[] nums, int num) {
        int left = 0, right = nums.length - 1;
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if(nums[mid] == num)
                return mid;
            if(nums[mid] < num)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return left;
    }
}

/*
Given an array nums sorted in non-decreasing order, return the maximum between the number of positive integers and the number of negative integers.
In other words, if the number of positive integers in nums is pos and the number of negative integers is neg, then return the maximum of pos and neg.
Note that 0 is neither positive nor negative.
Example 1:
Input: nums = [-2,-1,-1,1,2,3]
Output: 3
Explanation: There are 3 positive integers and 3 negative integers. The maximum count among them is 3.
Example 2:
Input: nums = [-3,-2,-1,0,0,1,2]
Output: 3
Explanation: There are 2 positive integers and 3 negative integers. The maximum count among them is 3.
Example 3:
Input: nums = [5,20,66,1314]
Output: 4
Explanation: There are 4 positive integers and 0 negative integers. The maximum count among them is 4.

Constraints:
1 <= nums.length <= 2000
-2000 <= nums[i] <= 2000
nums is sorted in a non-decreasing order.

Follow up: Can you solve the problem in O(log(n)) time complexity?
 */