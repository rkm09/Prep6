package daily.medium;

import java.util.*;

public class PivotArray2161 {
    public static void main(String[] args) {
        PivotArray2161 p = new PivotArray2161();
        int[] nums = {9,12,5,10,14,3,10};
        System.out.println(Arrays.toString(p.pivotArray(nums, 10)));
    }

//    two pointer ; time: O(n), space: O(1)
    public int[] pivotArray(int[] nums, int pivot) {
        int n = nums.length;
        int lessIdx = 0, greaterIdx = n - 1;
        int[] res = new int[n];
        for(int i = 0, j = n - 1 ; i < n ; i++, j--) {
            if(nums[i] < pivot)
                res[lessIdx++] = nums[i];
            if(nums[j] > pivot)
                res[greaterIdx--] = nums[j];
        }
        while(lessIdx <= greaterIdx) {
            res[lessIdx++] = pivot;
        }
        return res;
    }

//    two pass with fixed array; time: O(n), space: O(1)
    public int[] pivotArray1(int[] nums, int pivot) {
        int less = 0, equal = 0;
        for(int num : nums) {
            if(num < pivot) less++;
            else if(num == pivot) equal++;
        }
        int lessIdx = 0;
        int equalIdx = less;
        int greaterIdx = less + equal;
        int[] res = new int[nums.length];
        for(int num : nums) {
            if(num < pivot)
                res[lessIdx++] = num;
            else if(num == pivot)
                res[equalIdx++] = num;
            else
                res[greaterIdx++] = num;
        }
        return res;
    }

//    def; time: O(n), space: O(n)
    public int[] pivotArray2(int[] nums, int pivot) {
        int n = nums.length;
        int[] res = new int[n];
        int pivotCnt = 0;
        Deque<Integer> leftQ = new ArrayDeque<>();
        Deque<Integer> rightQ = new ArrayDeque<>();
        for(int i = 0 ; i < n ; i++) {
           if(nums[i] < pivot)
               leftQ.offer(nums[i]);
           else if(nums[i] > pivot)
               rightQ.offer(nums[i]);
           else
               pivotCnt++;
        }
        int idx = 0;
        while(!leftQ.isEmpty())
            res[idx++] = leftQ.poll();
        while(pivotCnt-- >= 1)
            res[idx++] = pivot;
        while(!rightQ.isEmpty())
            res[idx++] = rightQ.poll();
        return res;
    }

//    dynamic list; time: O(n), space: O(n)
    public int[] pivotArray3(int[] nums, int pivot) {
        List<Integer> less = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();
        for(int num : nums) {
            if(num < pivot)
                less.add(num);
            else if(num == pivot)
                equal.add(num);
            else
                greater.add(num);
        }
        less.addAll(equal);
        less.addAll(greater);
        int n = nums.length, idx = 0;
        int[] res = new int[n];
        for(int num : less)
            res[idx++] = num;
        return res;
    }

}

/*
You are given a 0-indexed integer array nums and an integer pivot. Rearrange nums such that the following conditions are satisfied:
Every element less than pivot appears before every element greater than pivot.
Every element equal to pivot appears in between the elements less than and greater than pivot.
The relative order of the elements less than pivot and the elements greater than pivot is maintained.
More formally, consider every pi, pj where pi is the new position of the ith element and pj is the new position of the jth element. If i < j and both elements are smaller (or larger) than pivot, then pi < pj.
Return nums after the rearrangement.
Example 1:
Input: nums = [9,12,5,10,14,3,10], pivot = 10
Output: [9,5,3,10,10,12,14]
Explanation:
The elements 9, 5, and 3 are less than the pivot so they are on the left side of the array.
The elements 12 and 14 are greater than the pivot so they are on the right side of the array.
The relative ordering of the elements less than and greater than pivot is also maintained. [9, 5, 3] and [12, 14] are the respective orderings.
Example 2:
Input: nums = [-3,4,3,2], pivot = 2
Output: [-3,2,4,3]
Explanation:
The element -3 is less than the pivot so it is on the left side of the array.
The elements 4 and 3 are greater than the pivot so they are on the right side of the array.
The relative ordering of the elements less than and greater than pivot is also maintained. [-3] and [4, 3] are the respective orderings.
Constraints:
1 <= nums.length <= 105
-106 <= nums[i] <= 106
pivot equals to an element of nums.
 */