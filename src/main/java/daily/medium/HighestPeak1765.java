package daily.medium;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class HighestPeak1765 {
    public static void main(String[] args) {
        HighestPeak1765 h = new HighestPeak1765();
        int[][] isWater = {{0,1},{0,0}};
        int[][] res = h.highestPeak(isWater);
    }

//    dp; time: O(m.n), space: O(m.n) [faster]
    public int[][] highestPeak(int[][] isWater) {
        int rows = isWater.length, cols = isWater[0].length;
        int[][] cellHeights = new int[rows][cols];
//        large value to represent uninitialised heights
        final int INF = rows * cols;
//        initialize the cell matrix with INF (unprocessed cells)
        for(int[] row : cellHeights) {
            Arrays.fill(row, INF);
        }
//        set the height of water cells to 0
        for(int row = 0 ; row < rows ; row++) {
            for(int col = 0 ; col < cols ; col++) {
                if(isWater[row][col] == 1)
                    cellHeights[row][col] = 0;
            }
        }
//        forward pass (check top, left); updating heights based on top and left cells
        for(int row = 0 ; row < rows ; row++) {
            for (int col = 0; col < cols; col++) {
//                initialize the minimum neighbour distance
                int minNeighbourDistance = INF;
//                check the cell above
                int neighbourRow = row - 1;
                int neighbourCol = col;
                if (isValidCell(neighbourRow, neighbourCol, rows, cols))
                    minNeighbourDistance = Math.min(minNeighbourDistance, cellHeights[neighbourRow][neighbourCol]);
//                check the cell to the left
                neighbourRow = row;
                neighbourCol = col - 1;
                if (isValidCell(neighbourRow, neighbourCol, rows, cols))
                    minNeighbourDistance = Math.min(minNeighbourDistance, cellHeights[neighbourRow][neighbourCol]);
//                set the current cell's height as the minimum of its neighbours + 1
                cellHeights[row][col] = Math.min(minNeighbourDistance + 1, cellHeights[row][col]);
            }
        }

//         backward pass (check down, right); updating heights based on bottom and right cells
        for(int row = rows - 1 ; row >= 0 ; row--) {
            for(int col = cols - 1 ; col >= 0 ; col--) {
//                initialize the minimum neighbour distance
                int minNeighbourDistance = INF;
//                check the cell below
                int neighbourRow = row + 1;
                int neighbourCol = col;
                if(isValidCell(neighbourRow, neighbourCol, rows, cols))
                    minNeighbourDistance = Math.min(minNeighbourDistance, cellHeights[neighbourRow][neighbourCol]);
//                check the cell to the right
                neighbourRow = row;
                neighbourCol = col + 1;
                if(isValidCell(neighbourRow, neighbourCol, rows, cols))
                    minNeighbourDistance = Math.min(minNeighbourDistance, cellHeights[neighbourRow][neighbourCol]);
                cellHeights[row][col] = Math.min(minNeighbourDistance + 1, cellHeights[row][col]);
            }
        }

        return cellHeights;
    }

    private boolean isValidCell(int row, int col, int rows, int cols) {
        return row >= 0 && col >= 0 && row < rows && col < cols;
    }

//    bfs; time: O(m.n), space: O(m.n)
    public int[][] highestPeak1(int[][] isWater) {
        int rows = isWater.length, cols = isWater[0].length;
        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,1,-1};
        int[][] cellHeights = new int[rows][cols];
        for(int[] row : cellHeights)
            Arrays.fill(row, -1);
        Deque<int[]> queue = new ArrayDeque<>();
        for(int row = 0 ; row < rows ; row++) {
            for(int col = 0 ; col < cols ; col++) {
                if(isWater[row][col] == 1) {
                    cellHeights[row][col] = 0;
                    queue.offer(new int[]{row,col});
                }
            }
        }
        int levelHeight = 1;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0 ; i < size ; i++) {
                int[] cell = queue.poll();
                for(int dir = 0 ; dir < 4 ; dir++) {
                    int neighbourX = cell[0] + dx[dir];
                    int neighbourY = cell[1] + dy[dir];
                    if(isValidCell(neighbourX, neighbourY, rows, cols) && cellHeights[neighbourX][neighbourY] == -1) {
                        cellHeights[neighbourX][neighbourY] = levelHeight;
                        queue.offer(new int[]{neighbourX, neighbourY});
                    }
                }
            }
            levelHeight++;
        }
        return cellHeights;
    }
}

/*
You are given an integer matrix isWater of size m x n that represents a map of land and water cells.
If isWater[i][j] == 0, cell (i, j) is a land cell.
If isWater[i][j] == 1, cell (i, j) is a water cell.
You must assign each cell a height in a way that follows these rules:
The height of each cell must be non-negative.
If the cell is a water cell, its height must be 0.
Any two adjacent cells must have an absolute height difference of at most 1. A cell is adjacent to another cell if the former is directly north, east, south, or west of the latter (i.e., their sides are touching).
Find an assignment of heights such that the maximum height in the matrix is maximized.
Return an integer matrix height of size m x n where height[i][j] is cell (i, j)'s height. If there are multiple solutions, return any of them.
Example 1:
Input: isWater = [[0,1],[0,0]]
Output: [[1,0],[2,1]]
Explanation: The image shows the assigned heights of each cell.
The blue cell is the water cell, and the green cells are the land cells.
Example 2:
Input: isWater = [[0,0,1],[1,0,0],[0,0,0]]
Output: [[1,1,0],[0,1,1],[1,2,2]]
Explanation: A height of 2 is the maximum possible height of any assignment.
Any height assignment that has a maximum height of 2 while still meeting the rules will also be accepted.

Constraints:
m == isWater.length
n == isWater[i].length
1 <= m, n <= 1000
isWater[i][j] is 0 or 1.
There is at least one water cell.

 */

/*
DP:
In this approach, we build on the idea that the height of each cell should be the smallest distance to any water cell. From there, we observe that once we know the smallest distances of a cell’s neighboring cells,
calculating the distance for the current cell becomes straightforward — it’s just the smallest of the neighbors’ distances plus one. The core idea is to use dynamic programming to compute these distances efficiently.
Dynamic programming works well here because:
Each cell's height can be derived from the heights of its neighboring cells.
By iterating over the grid in a specific order, we can ensure that all necessary states are computed before being used.
However, the challenge is figuring out the correct order to compute these states. In DP terms, we need to ensure all necessary states are computed before using them.
Let’s simplify by imagining we can only move down or right. In that case, the top-left corner has no choices — it’s either a water cell or not reachable. Similarly, for the first row and column, we only have options from neighboring cells directly below or to the right.
Using this, we can fill the DP table row by row and column by column, in common order.
Finally, we perform a second pass, moving upward or left, to correct any distances that were overestimated during the first pass, which only considered partial directions (top and left).

BFS:
For every cell in the grid, we calculate its smallest distance to any water cell and assign that value as its height.
Heights increase smoothly from water cells, ensuring the highest peak is at the farthest distance from all water cells.
This can be visualized as a "ripple effect" where water cells propagate their distances outward, assigning heights to nearby land cells.
This approach works intuitively for two reasons:
It follows the rule that the height difference between two adjacent cells is at most one. This is because the minimum distance to water for any two neighboring cells cannot differ by more than one.
It’s optimal because it ensures that the height of the cells increases consistently as we move farther from water cells, maximizing the highest peak on the map.
To find the shortest distance from any cell to a water cell, we use Breadth-First Search (BFS) starting from all water cells. When a land cell is reached for the first time, its shortest distance to a water cell is set.
*/