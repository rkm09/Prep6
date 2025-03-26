package daily.medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinOperations2033 {
    public static void main(String[] args) {
        int[][] grid = {{2,4},{6,8}};
        System.out.println(minOperations(grid, 2));
    }

//    sorting and median; time: O(m.nlogm.n), space: O(m.n)
    public static int minOperations(int[][] grid, int x) {
//        create a list to store all the numbers in the grid
        List<Integer> numList = new ArrayList<>();
        int result = 0;
//        flatten the grid
        for(int[] row : grid) {
            for(int val : row) {
                numList.add(val);
            }
        }
//        sort an ascending order to easily locate the median
        Collections.sort(numList);
//        store the median as the final common value
        int finalCommonNumber = numList.get(numList.size() / 2);
//        iterate through each number in the numList
        for(int num : numList) {
//            if the remainder when divided by x is different return -1
            if(num % x != finalCommonNumber % x)
                return -1;
//            add the number of operations required to make the current number equal to the final common number
            result += Math.abs(finalCommonNumber - num) / x;
        }

        return result;
    }
}

/*
You are given a 2D integer grid of size m x n and an integer x. In one operation, you can add x to or subtract x from any element in the grid.
A uni-value grid is a grid where all the elements of it are equal.
Return the minimum number of operations to make the grid uni-value. If it is not possible, return -1.
Example 1:
Input: grid = [[2,4],[6,8]], x = 2
Output: 4
Explanation: We can make every element equal to 4 by doing the following:
- Add x to 2 once.
- Subtract x from 6 once.
- Subtract x from 8 twice.
A total of 4 operations were used.
Example 2:
Input: grid = [[1,5],[2,3]], x = 1
Output: 5
Explanation: We can make every element equal to 3.
Example 3:
Input: grid = [[1,2],[3,4]], x = 2
Output: -1
Explanation: It is impossible to make every element equal.

Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 105
1 <= m * n <= 105
1 <= x, grid[i][j] <= 104
 */

/*
Median and sorting:
The only operation allowed is adding or subtracting xsome number of times. This means we must be able to reachvfrom bothaandbusingx`.
For this to be possible, the differences v - a and v - b must both be multiples of x, or equivalently:
(v−a)%x=0and(v−b)%x=0
Rearranging this, we get:
a%x=b%x=v%x
This tells us that all numbers in the grid must have the same remainder when divided by x. Otherwise, it is impossible to transform them into a single value using only x-sized steps.
A natural choice is the median of the numbers.
Why? The median is the balancing point that minimizes the total distance numbers need to move. By choosing the median, we ensure that half of the numbers shift up and the other half shift down, naturally minimizing the total number of operations.
 */