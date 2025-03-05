package daily.medium;

public class ColoredCells2579 {
    public static void main(String[] args) {
        ColoredCells2579 c = new ColoredCells2579();
        System.out.println(c.coloredCells(2));
    }

//    iterative addition; time: O(n), space: O(1)
    public long coloredCells(int n) {
        long numOfBlueCells = 1;
        int addend = 4;
        while(--n > 0) {
            numOfBlueCells += addend;
            addend += 4;
        }
        return numOfBlueCells;
    }
}

/*
There exists an infinitely large two-dimensional grid of uncolored unit cells. You are given a positive integer n, indicating that you must do the following routine for n minutes:
At the first minute, color any arbitrary unit cell blue.
Every minute thereafter, color blue every uncolored cell that touches a blue cell.
Below is a pictorial representation of the state of the grid after minutes 1, 2, and 3.
Return the number of colored cells at the end of n minutes.
Example 1:
Input: n = 1
Output: 1
Explanation: After 1 minute, there is only 1 blue cell, so we return 1.
Example 2:
Input: n = 2
Output: 5
Explanation: After 2 minutes, there are 4 colored cells on the boundary and 1 in the center, so we return 5.
Constraints:
1 <= n <= 105
 */

/*
we notice a clear pattern: each iteration adds a multiple of 4 new cells to the existing structure. Specifically, the number of cells added at each step follows the sequence: 4, 8, 12, 16, ..., increasing by 4 every time.
 */