package daily.medium;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaxFish2658 {
    public static void main(String[] args) {
        MaxFish2658 m = new MaxFish2658();
        int[][] grid = {{0,2,1,0},{4,0,0,3},{1,0,0,4},{0,3,2,0}};
        int[][] grid1 = {{1,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,1}};
        int[][] grid2 = {{1,6,10}};
        int [][] grid3 = {{0,8,10},{2,8,0}};
        System.out.println(m.findMaxFish1(grid));
    }

//    dfs; time: O(m.n), space: O(m.n)
    public int findMaxFish(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
//        a 2d array to keep track of unvisited cells
        boolean[][] visited = new boolean[rows][cols];
        int maxFishCount = 0;
//        iterate through all the cells in the grid
        for(int row = 0 ; row < rows ; row++) {
            for(int col = 0 ; col < cols ; col++) {
//                start dfs for unvisited water cells
                if(!visited[row][col] && grid[row][col] > 0) {
                    maxFishCount = Math.max(maxFishCount, calculateFishCount(grid, visited, row, col));
                }
            }
        }
        return maxFishCount;
    }

    private int calculateFishCount(int[][] grid, boolean[][] visited, int row, int col) {
//        check boundary conditions, land cells or already visited cells
        if(!isValidCell(grid, visited, row, col)) return 0;
//        mark the current cell as visited
        visited[row][col] = true;
//        accumulate fish count from the current cell and its neighbours
        return grid[row][col] + calculateFishCount(grid, visited, row + 1, col)
                + calculateFishCount(grid, visited, row, col + 1)
                + calculateFishCount(grid, visited, row - 1, col)
                + calculateFishCount(grid, visited, row, col - 1);
    }

    private boolean isValidCell(int[][] grid, boolean[][] visited, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length
                && grid[row][col] != 0 && !visited[row][col];
    }

//    bfs; time: O(m.n), space: O(m.n)
    public int findMaxFish1(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int maxFishCount = 0;
        for(int row = 0 ; row < rows ; row++) {
            for(int col = 0 ; col < cols ; col++) {
                if(!visited[row][col] && grid[row][col] != 0) {
                    maxFishCount = Math.max(maxFishCount, calculateFishCountBfs(grid, visited, row, col));
                }
            }
        }
        return maxFishCount;
    }

    private int calculateFishCountBfs(int[][] grid, boolean[][] visited, int row, int col) {
        int fishCount = 0;
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{row, col});
        visited[row][col] = true;
//        directions for exploring down, up, right, left
        int[] rowDirections = {1,-1,0,0};
        int[] colDirections = {0,0,1,-1};
//        bfs traversal
        while(!queue.isEmpty()) {
            int[] cell = queue.poll();
            row = cell[0]; col = cell[1];
            fishCount += grid[row][col];
//            explore all four directions
            for(int dir = 0 ; dir < 4 ; dir++) {
                int newRow = row + rowDirections[dir];
                int newCol = col + colDirections[dir];
                if(isValidCell(grid, visited, newRow, newCol)) {
                    queue.offer(new int[]{newRow, newCol});
//                    mark this one as visited, so it is not a TLE
                    visited[newRow][newCol] = true;
                }
            }
        }
        return fishCount;
    }

//    disjoint set union; time: O(m.n), space: O(m.n)
//    may need rework
    public int findMaxFish2(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        UnionFind uf = new UnionFind(grid);
        int[] rowDirections = {1,-1,0,0};
        int[] colDirections = {0,0,1,-1};
        for(int row = 0 ; row < rows ; row++) {
            for(int col = 0 ; col < cols ; col++) {
                if(grid[row][col] != 0) {
                    for(int dir = 0 ; dir < 4 ; dir++) {
                        int newRow = row + rowDirections[dir];
                        int newCol = col + colDirections[dir];
                        if(newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] != 0) {
                            uf.union(row * cols + col, newRow * cols + newCol);
                        }
                    }

                }
            }
        }
        return uf.getMaxFishCount();
    }

    class UnionFind {
        private int[] parent;
        private int[] rank;
        private int[] fishCount;
        int maxFishCount = 0;
        UnionFind(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
            parent = new int[m * n];
            rank = new int[m * n];
            fishCount = new int[m * n];
            for(int r = 0 ; r < m ; r++) {
                for(int c = 0 ; c < n ; c++) {
                    if(grid[r][c] != 0) {
                        parent[r * n + c] = r * n + c;
                        fishCount[r * n + c] = grid[r][c];
                        maxFishCount = Math.max(maxFishCount, fishCount[r * n + c]);
                    }
                }
            }
        }
        private int find(int i) {
            if(parent[i] != i)
                parent[i] = find(parent[i]);
            return parent[i];
        }
        private void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if(rootX != rootY) {
                if(rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                }
                else if(rank[rootX] < rank[rootY]) {
                    parent[rootX] = parent[rootY];
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }

            fishCount[x] += fishCount[y];
            fishCount[y] = 0;
            maxFishCount = Math.max(maxFishCount, fishCount[x]);
        }

        private int getMaxFishCount() {
            return maxFishCount;
        }
    }
}

/*
You are given a 0-indexed 2D matrix grid of size m x n, where (r, c) represents:
A land cell if grid[r][c] = 0, or
A water cell containing grid[r][c] fish, if grid[r][c] > 0.
A fisher can start at any water cell (r, c) and can do the following operations any number of times:
Catch all the fish at cell (r, c), or
Move to any adjacent water cell.
Return the maximum number of fish the fisher can catch if he chooses his starting cell optimally, or 0 if no water cell exists.
An adjacent cell of the cell (r, c), is one of the cells (r, c + 1), (r, c - 1), (r + 1, c) or (r - 1, c) if it exists.
Example 1:
Input: grid = [[0,2,1,0],[4,0,0,3],[1,0,0,4],[0,3,2,0]]
Output: 7
Explanation: The fisher can start at cell (1,3) and collect 3 fish, then move to cell (2,3) and collect 4 fish.
Example 2:
Input: grid = [[1,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,1]]
Output: 1
Explanation: The fisher can start at cells (0,0) or (3,3) and collect a single fish.

Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 10
0 <= grid[i][j] <= 10
 */

/*
DFS:
DFS works by exploring every connected node (in this case, the water cells) starting from a given cell. When we find a water cell, we start a DFS from that cell. The DFS will look at all neighboring water cells (in all four directions), marking them as visited to ensure we don’t count them again.
As we traverse each connected water region, we also keep a running total of the number of fish in that region. This means that for every new DFS call, we add up all the fish in that group of connected cells.
After exploring all the water cells in one region, we move on to the next unvisited water cell and repeat the process. While doing this, we always track the greatest number of fish encountered in any of the regions. By the time we finish going through the whole grid, we will have found the region with the most fish and that will be our result.
Complexity Analysis:
Let m be the number of rows and n be the number of columns in the grid.
Time Complexity: O(m⋅n)
In the worst case, where the grid is completely filled with water cells, the algorithm iterates through all m x n cells. For each cell, it performs a depth-first search (DFS) to calculate the total fish in the connected region. Therefore, the overall time complexity is O(m⋅n).
Space Complexity: O(m⋅n)
The algorithm uses a visited matrix of size m x n to track visited cells. Additionally, the depth-first search (DFS) can recurse to explore all connected cells, contributing to the space complexity. Hence, the overall space complexity is O(m⋅n).
 */