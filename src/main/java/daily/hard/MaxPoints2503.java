package daily.hard;

import java.util.*;

public class MaxPoints2503 {
    public static void main(String[] args) {
        MaxPoints2503 m = new MaxPoints2503();
        int[][] grid = {{1,2,3},{2,5,7},{3,5,1}};
        int[] queries = {5,6,2};
        System.out.println(Arrays.toString(m.maxPoints(grid, queries)));
    }

//    sorting + min heap; time: O(klogk + m.nlog(m.n)), space: O(m.n + k)
    public int[] maxPoints(int[][] grid, int[] queries) {
//        directions
        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
        int rowCount = grid.length, colCount = grid[0].length;
        int[] res = new int[queries.length];
        int[][] sortedQueries = new int[queries.length][2];
        for(int idx = 0 ; idx < queries.length ; idx++) {
            sortedQueries[idx][0] = queries[idx];
            sortedQueries[idx][1] = idx;
        }
//        sort queries in ascending order
        Arrays.sort(sortedQueries, Comparator.comparingInt(a -> a[0]));
//        min heap to process cells in ascending order of values
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
//        start from top left cell
        minHeap.offer(new int[] {grid[0][0], 0, 0});
        boolean[][] visited = new boolean[rowCount][colCount];
//        mark first cell as visited
        visited[0][0] = true;
//        keep track of the total cells processed
        int totalPoints = 0;
//        process queries in sorted order
        for(int[] query : sortedQueries) {
            int queryVal = query[0];
            int queryIdx = query[1];
//            expand the cells that are smaller than the current query value
            while(!minHeap.isEmpty() && minHeap.peek()[0] < queryVal) {
                int[] current = minHeap.poll();
                int currentRow = current[1];
                int currentCol = current[2];
//                increment count of valid cells
                totalPoints++;
//                explore all four possible directions
                for(int[] direction : directions) {
                    int nextRow = currentRow + direction[0];
                    int nextCol = currentCol + direction[1];
//                    check if the new cell is within bounds and is not visited
                    if(nextRow >= 0 && nextRow < rowCount && nextCol >= 0 && nextCol < colCount
                     && !visited[nextRow][nextCol]) {
                        minHeap.offer(new int[] {grid[nextRow][nextCol], nextRow, nextCol});
//                        mark as visited
                        visited[nextRow][nextCol] = true;
                    }
                }
            }
//            store the result for this query
            res[queryIdx] = totalPoints;
        }
        return res;

    }

//    TLE; brute force; bfs; time: O(k.m.n), space: O(m.n) [k - number of queries]
    public int[] maxPointsX(int[][] grid, int[] queries) {
//        directions
        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
        int[] res = new int[queries.length];
        int rowCount = grid.length, colCount = grid[0].length;
//        iterate through each query
        for(int queryIdx = 0 ; queryIdx < queries.length ; queryIdx++) {
            int queryVal = queries[queryIdx];
//            start bfs from top left corner
            Deque<int[]> bfsQueue = new ArrayDeque<>();
            bfsQueue.offer(new int[] {0,0});
            boolean[][] visited = new boolean[rowCount][colCount];
//            mark the starting cell as visited
            visited[0][0] = true;
            int points = 0 ;
//            bfs traversal
            while(!bfsQueue.isEmpty()) {
                int[] current = bfsQueue.poll();
                int currentRow = current[0];
                int currentCol = current[1];
//                if the current cell is greater than or equal to query value stop expanding from here
                if(grid[currentRow][currentCol] >= queryVal)
                    continue;
//                count the valid cells
                points++;
//                explore all four directions
                for(int[] direction : directions) {
                    int nextRow = currentRow + direction[0];
                    int nextCol = currentCol + direction[1];
//                    ensure the new position is within bounds and has not been visited and the value meets the condition
                    if(nextRow >= 0 && nextRow < rowCount && nextCol >= 0 && nextCol < colCount
                    && !visited[nextRow][nextCol] && grid[nextRow][nextCol] < queryVal) {
                        bfsQueue.offer(new int[] {nextRow, nextCol});
//                        mark the cell as visited
                        visited[nextRow][nextCol] = true;
                    }
                }
            }
//           store the result for the current query
            res[queryIdx] = points;
        }

        return res;
    }

}

/*
You are given an m x n integer matrix grid and an array queries of size k.
Find an array answer of size k such that for each integer queries[i] you start in the top left cell of the matrix and repeat the following process:
If queries[i] is strictly greater than the value of the current cell that you are in, then you get one point if it is your first time visiting this cell, and you can move to any adjacent cell in all 4 directions: up, down, left, and right.
Otherwise, you do not get any points, and you end this process.
After the process, answer[i] is the maximum number of points you can get. Note that for each query you are allowed to visit the same cell multiple times.
Return the resulting array answer.
Example 1:
Input: grid = [[1,2,3],[2,5,7],[3,5,1]], queries = [5,6,2]
Output: [5,8,1]
Explanation: The diagrams above show which cells we visit to get points for each query.
Example 2:
Input: grid = [[5,2,1],[1,1,2]], queries = [3]
Output: [0]
Explanation: We can not get any points because the value of the top left cell is already greater than or equal to 3.
Constraints:
m == grid.length
n == grid[i].length
2 <= m, n <= 1000
4 <= m * n <= 105
k == queries.length
1 <= k <= 104
1 <= grid[i][j], queries[i] <= 106
 */

/*
sorting + min heap:
In the brute force approach, we restart the search from the top-left corner for every query, treating each query as an independent problem. This results in a significant amount of redundant work because many queries share overlapping information. If a smaller query has already determined that certain cells are accessible, then a larger query should be able to reuse that information instead of starting from scratch. This suggests that instead of treating each query separately, we can process them in an order that allows us to build on previously discovered results, avoiding unnecessary recomputation.
A natural way to achieve this is to sort the queries in increasing order while keeping track of their original indices. By doing this, we ensure that when we process a query, all smaller queries have already been resolved. This allows us to maintain a growing region of accessible cells rather than restarting the search for each query.
To efficiently manage this expanding region, we use a min-heap (priority queue).
Time complexity: O(klogk+n⋅mlog(n⋅m))
The algorithm first sorts the k queries, which takes O(klogk) time. Then, for each query, it processes cells using a min-heap. In the worst case, all n⋅m cells are processed and pushed into the heap. Each heap operation (push or pop) takes O(log(n⋅m)) time. Therefore, processing all cells takes O(n⋅mlog(n⋅m)).
 */