package daily.medium;

public class CountServers1267 {
    public static void main(String[] args) {
        int[][] grid = {{1,0},{0,1}};
        System.out.println(countServers(grid));
    }

//    track using two arrays; time: O(m.n), space: O(m + n)
    public static int countServers(int[][] grid) {
        if(grid == null || grid.length == 0) return 0;
        int rows = grid.length, cols = grid[0].length;
//        note: it is opposite
        int[] rowsCount = new int[cols];
        int[] colsCount = new int[rows];
        for(int row = 0 ; row < rows ; row++) {
            for(int col = 0 ; col < cols ; col++) {
                if(grid[row][col] == 1) {
                    rowsCount[col]++;
                    colsCount[row]++;
                }
            }
        }
        int communicableServers = 0;
        for(int row = 0 ; row < rows ; row++) {
            for(int col = 0 ; col < cols ; col++) {
                if(grid[row][col] == 1) {
                    if(rowsCount[col] > 1 || colsCount[row] > 1)
                        communicableServers++;
                }
            }
        }
        return communicableServers;
    }
}

/*
You are given a map of a server center, represented as a m * n integer matrix grid, where 1 means that on that cell there is a server and 0 means that it is no server. Two servers are said to communicate if they are on the same row or on the same column.
Return the number of servers that communicate with any other server.
Example 1:
Input: grid = [[1,0],[0,1]]
Output: 0
Explanation: No servers can communicate with others.
Example 2:
Input: grid = [[1,0],[1,1]]
Output: 3
Explanation: All three servers can communicate with at least one other server.
Example 3:
Input: grid = [[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]
Output: 4
Explanation: The two servers in the first row can communicate with each other. The two servers in the third column can communicate with each other. The server at right bottom corner can't communicate with any other server.
Constraints:
m == grid.length
n == grid[i].length
1 <= m <= 250
1 <= n <= 250
grid[i][j] == 0 or 1

 */