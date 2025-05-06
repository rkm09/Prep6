package daily.medium;

import java.util.Arrays;

public class BuildArray1920 {
    public static void main(String[] args) {
        int[] nums = {0,2,1,5,3,4};
        int[] res = buildArray(nums);
        System.out.println(Arrays.toString(res));
    }

//    in-place; time: O(n), space: O(1)
    public static int[] buildArray(int[] nums) {
        int n = nums.length;
        for(int i = 0 ; i < n ; i++) {
            nums[i] += 1000 * (nums[nums[i]] % 1000);
        }
        for(int i = 0 ; i < n ; i++) {
            nums[i] = nums[i] / 1000;
        }
        return nums;
    }

//    time: O(n), space: O(1) [separate result]
    public static int[] buildArray1(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        for(int i = 0 ; i < n ; i++) {
            res[i] = nums[nums[i]];
        }
        return res;
    }
}

/*
Given a zero-based permutation nums (0-indexed), build an array ans of the same length where ans[i] = nums[nums[i]] for each 0 <= i < nums.length and return it.
A zero-based permutation nums is an array of distinct integers from 0 to nums.length - 1 (inclusive).
Example 1:
Input: nums = [0,2,1,5,3,4]
Output: [0,1,2,4,5,3]
Explanation: The array ans is built as follows:
ans = [nums[nums[0]], nums[nums[1]], nums[nums[2]], nums[nums[3]], nums[nums[4]], nums[nums[5]]]
    = [nums[0], nums[2], nums[1], nums[5], nums[3], nums[4]]
    = [0,1,2,4,5,3]
Example 2:
Input: nums = [5,0,1,2,3,4]
Output: [4,5,0,1,2,3]
Explanation: The array ans is built as follows:
ans = [nums[nums[0]], nums[nums[1]], nums[nums[2]], nums[nums[3]], nums[nums[4]], nums[nums[5]]]
    = [nums[5], nums[0], nums[1], nums[2], nums[3], nums[4]]
    = [4,5,0,1,2,3]

Constraints:
1 <= nums.length <= 1000
0 <= nums[i] < nums.length
The elements in nums are distinct.

Follow-up: Can you solve it without using an extra space (i.e., O(1) memory)?
 */

/*
In order to allow the construction process to proceed completely, we need to enable each element nums[i] in nums to store both the 'current value' (i.e., nums[i]) and the 'final value' (i.e., nums[nums[i]]).
We notice that the range of values of the elements in nums is [0,999] inclusive, which means that both the 'current value' and the 'final value' of each element in nums are within the closed interval [0,999].
Therefore, we can use a concept similar to the "1000-based system" to represent the "current value" and "final value" of each element. For each element, we use the quotient when it is divided by 1000 to represent its "final value," and the remainder to represent its "current value."
So, we first traverse nums, calculate the "final value" of each element, and add 1000 times that value to the element. Then, we traverse the array again, and divide the value of each element by 1000, retaining the quotient. At this point, nums is the completed array, and we return this array as the answer.
When calculating the "final value" of nums[i] and modifying the element, we need to calculate the value of nums[nums[i]] before the modification, and the element at the index nums[i] in nums may have been modified. Therefore, we need to take the modulus of the value at that index with 1000 to get the "final value".
 */
