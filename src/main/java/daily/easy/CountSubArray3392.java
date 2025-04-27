package daily.easy;

public class CountSubArray3392 {
    public static void main(String[] args) {
        int[] nums = {1,2,1,4,1};
        int[] nums1 = {-1,-4,-1,4};
        System.out.println(countSubarrays(nums1));
    }

//    fixed window; time: O(n), space: O(1)
    public static int countSubarrays(int[] nums) {
        int n = nums.length, count = 0;
        int left = 0, right = 2;
        while(right < n) {
            int sum = nums[left] + nums[right];
            if(sum * 2 == nums[left + 1]) count++;
            left++; right++;
        }
        return count;
    }

//    one time traversal; time: O(n), space: O(1)
    public static int countSubarrays1(int[] nums) {
        int n = nums.length, count = 0;
        for(int i = 1 ; i < n - 1 ; i++) {
            if(nums[i] == (nums[i - 1] + nums[i + 1]) * 2)
                count++;
        }
        return count;
    }
}

/*
Given an integer array nums, return the number of subarrays of length 3 such that the sum of the first and third numbers equals exactly half of the second number.
Example 1:
Input: nums = [1,2,1,4,1]
Output: 1
Explanation:
Only the subarray [1,4,1] contains exactly 3 elements where the sum of the first and third numbers equals half the middle number.
Example 2:
Input: nums = [1,1,1]
Output: 0
Explanation:
[1,1,1] is the only subarray of length 3. However, its first and third numbers do not add to half the middle number.
Constraints:
3 <= nums.length <= 100
-100 <= nums[i] <= 100
 */