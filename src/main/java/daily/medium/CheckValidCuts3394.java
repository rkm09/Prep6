package daily.medium;

import java.util.Arrays;
import java.util.Comparator;

public class CheckValidCuts3394 {
    public static void main(String[] args) {
        CheckValidCuts3394 c = new CheckValidCuts3394();
        int[][] rectangles = {{1,0,5,2},{0,2,2,4},{3,2,5,3},{0,4,4,5}};
        System.out.println(c.checkValidCuts(5, rectangles));
    }

//    line sweep; time: O(nlogn), space: O(S)
    public boolean checkValidCuts(int n, int[][] rectangles) {
//        try both horizontal and vertical cuts
        return checkCuts(rectangles, 0) || checkCuts(rectangles, 1);
    }

//    check if valid cuts can be made in a specific dimension
    private boolean checkCuts(int[][] rectangles, int dim) {
//        sort the rectangles by their starting coordinates in the given dimension
        Arrays.sort(rectangles, Comparator.comparingInt(a->a[dim]));
//        track the furthest ending coordinate seen so far
        int furthestEnd = rectangles[0][dim + 2];
        int gapCount = 0;
        for(int i = 1 ; i < rectangles.length ; i++) {
            int[] rectangle = rectangles[i];
//            if the current rectangle starts after the furthest end we have seen so far, we've found a gap where a cut can be made
            if(furthestEnd <= rectangle[dim])
                gapCount++;
//            update the furthest ending coordinate
            furthestEnd = Math.max(furthestEnd, rectangle[dim + 2]);
        }
//        we need at least two gaps to create three sections
        return gapCount >= 2;
    }
}

/*
You are given an integer n representing the dimensions of an n x n grid, with the origin at the bottom-left corner of the grid. You are also given a 2D array of coordinates rectangles, where rectangles[i] is in the form [startx, starty, endx, endy], representing a rectangle on the grid. Each rectangle is defined as follows:
(startx, starty): The bottom-left corner of the rectangle.
(endx, endy): The top-right corner of the rectangle.
Note that the rectangles do not overlap. Your task is to determine if it is possible to make either two horizontal or two vertical cuts on the grid such that:
Each of the three resulting sections formed by the cuts contains at least one rectangle.
Every rectangle belongs to exactly one section.
Return true if such cuts can be made; otherwise, return false.
Example 1:
Input: n = 5, rectangles = [[1,0,5,2],[0,2,2,4],[3,2,5,3],[0,4,4,5]]
Output: true
Explanation:
The grid is shown in the diagram. We can make horizontal cuts at y = 2 and y = 4. Hence, output is true.
Example 2:
Input: n = 4, rectangles = [[0,0,1,1],[2,0,3,4],[0,2,2,3],[3,0,4,3]]
Output: true
Explanation:
We can make vertical cuts at x = 2 and x = 3. Hence, output is true.
Example 3:
Input: n = 4, rectangles = [[0,2,2,4],[1,0,3,2],[2,2,3,4],[3,0,4,2],[3,2,4,4]]
Output: false
Explanation:
We cannot make two horizontal or two vertical cuts that satisfy the conditions. Hence, output is false.

Constraints:
3 <= n <= 109
3 <= rectangles.length <= 105
0 <= rectangles[i][0] < rectangles[i][2] <= n
0 <= rectangles[i][1] < rectangles[i][3] <= n
No two rectangles overlap.
 */