package daily.easy;

import java.util.Arrays;

public class CheckArraySorted1752 {
    public static void main(String[] args) {
        int[] nums = {3,4,5,1,2};
        int[] nums1 = {2,1,3,4};
        System.out.println(check(nums1));
    }

//    find the smallest element; time: O(n), space: O(1)
    public static boolean check(int[] nums) {
        int n = nums.length;
        if(n <= 1) return true;
        int inversionCount = 0;
        for(int i = 1 ; i < n ; i++) {
//            for every pair count the inversion
            if(nums[i] < nums[i - 1])
                inversionCount++;
        }
//        also check between the last and the first element, to account for the cyclic order
        if(nums[0] < nums[n - 1])
            inversionCount++;
        return inversionCount <= 1;
    }

//    compare with sorted; time: O(n^2), space: O(n)
    public static boolean check1(int[] nums) {
        int size = nums.length;
        int[] sortedArr = nums.clone();
        Arrays.sort(sortedArr);
        for(int rotOffset = 0 ; rotOffset < size ; rotOffset++) {
            boolean isMatch = true;
            for(int index = 0 ; index < size ; index++) {
                if(nums[(rotOffset + index) % size] != sortedArr[index]) {
                    isMatch = false;
                    break;
                }
            }
            if(isMatch) return true;
        }
        return false;
    }
}

/*
Given an array nums, return true if the array was originally sorted in non-decreasing order, then rotated some number of positions (including zero). Otherwise, return false.
There may be duplicates in the original array.
Note: An array A rotated by x positions results in an array B of the same length such that A[i] == B[(i+x) % A.length], where % is the modulo operation.
Example 1:
Input: nums = [3,4,5,1,2]
Output: true
Explanation: [1,2,3,4,5] is the original sorted array.
You can rotate the array by x = 3 positions to begin on the the element of value 3: [3,4,5,1,2].
Example 2:
Input: nums = [2,1,3,4]
Output: false
Explanation: There is no sorted array once rotated that can make nums.
Example 3:
Input: nums = [1,2,3]
Output: true
Explanation: [1,2,3] is the original sorted array.
You can rotate the array by x = 0 positions (i.e. no rotation) to make nums.
Constraints:
1 <= nums.length <= 100
1 <= nums[i] <= 100
 */

/*
To find whether an array can be sorted by rotation, we need to check if, after a certain point, the sequence of elements remains sorted in a cyclic manner. A more efficient way to do this is by finding the smallest element in the array and using its position to identify the potential rotation offset, which would be the point where the original sorted array begins.
Once we identify the smallest element, we treat it as the "starting" point of the sorted array. From this position, we check if the next n elements, wrapping around cyclically, form a sorted sequence.
The key observation here is that, in a sorted array that has been rotated, all elements should be in non-decreasing order, except for one place where the largest element will be followed by the smallest element due to the rotation. This results in at most one "inversion" — a pair where a number is greater than the next one.
 */