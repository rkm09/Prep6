package daily.easy;

import java.util.ArrayList;
import java.util.List;

public class MergeArrays2570 {
    public static void main(String[] args) {
        int[][] nums1 = {{1,2},{2,3},{4,5}};
        int[][] nums2 = {{1,4},{3,2},{4,1}};
        int[][] res = mergeArrays(nums1, nums2);
    }

//    def; two pointer; time: O(m+n), space: O(m+n)
    public static int[][] mergeArrays(int[][] nums1, int[][] nums2) {
        int n1 = nums1.length, n2 = nums2.length;
        int ptr1 = 0, ptr2 = 0;
        List<int[]> resList = new ArrayList<>();
        while(ptr1 < n1 && ptr2 < n2) {
            int id1 = nums1[ptr1][0];
            int id2 = nums2[ptr2][0];
            if(id1 == id2)
                resList.add(new int[] {id1, nums1[ptr1++][1] + nums2[ptr2++][1]});
            else if(id1 < id2)
                resList.add(new int[] {id1, nums1[ptr1++][1]});
            else
                resList.add(new int[] {id2, nums2[ptr2++][1]});
        }
//        handle any remaining pairs
        while(ptr1 < n1)
            resList.add(new int[] {nums1[ptr1][0], nums1[ptr1++][1]});
        while(ptr2 < n2)
            resList.add(new int[] {nums2[ptr2][0], nums2[ptr2++][1]});

        int[][] res = new int[resList.size()][2];
        int idx = 0;
        for(int[] num : resList) {
            res[idx++] = num;
        }

        return res;
    }
}

/*
You are given two 2D integer arrays nums1 and nums2.
nums1[i] = [idi, vali] indicate that the number with the id idi has a value equal to vali.
nums2[i] = [idi, vali] indicate that the number with the id idi has a value equal to vali.
Each array contains unique ids and is sorted in ascending order by id.
Merge the two arrays into one array that is sorted in ascending order by id, respecting the following conditions:
Only ids that appear in at least one of the two arrays should be included in the resulting array.
Each id should be included only once and its value should be the sum of the values of this id in the two arrays. If the id does not exist in one of the two arrays, then assume its value in that array to be 0.
Return the resulting array. The returned array must be sorted in ascending order by id.
Example 1:
Input: nums1 = [[1,2],[2,3],[4,5]], nums2 = [[1,4],[3,2],[4,1]]
Output: [[1,6],[2,3],[3,2],[4,6]]
Explanation: The resulting array contains the following:
- id = 1, the value of this id is 2 + 4 = 6.
- id = 2, the value of this id is 3.
- id = 3, the value of this id is 2.
- id = 4, the value of this id is 5 + 1 = 6.
Example 2:
Input: nums1 = [[2,4],[3,6],[5,5]], nums2 = [[1,3],[4,3]]
Output: [[1,3],[2,4],[3,6],[4,3],[5,5]]
Explanation: There are no common ids, so we just include each id with its value in the resulting list.

Constraints:
1 <= nums1.length, nums2.length <= 200
nums1[i].length == nums2[j].length == 2
1 <= idi, vali <= 1000
Both arrays contain unique ids.
Both arrays are in strictly ascending order by id.
 */