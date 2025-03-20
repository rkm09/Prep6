package graphs;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class MazeII505 {
    public static void main(String[] args) {
        MazeII505 m = new MazeII505();
        int[][] maze = {{0,0,1,0,0},{0,0,0,0,0},{0,0,0,1,0},{1,1,0,1,1},{0,0,0,0,0}};
        int[] start = {0,4}, destination = {4,4};
        System.out.println(m.shortestDistance(maze, start, destination));
    }

//    dfs; time: O(m.n.max(m,n)), space: O(m.n)
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        int[][] distance = new int[maze.length][maze[0].length];
        for(int[] row : distance)
            Arrays.fill(row, Integer.MAX_VALUE);
        distance[start[0]][start[1]] = 0;
        dfs(maze, start, distance);
        return distance[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 :
                distance[destination[0]][destination[1]];
    }

    private void dfs(int[][] maze, int[] start, int[][] distance) {
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        for(int[] dir : dirs) {
            int x = start[0] + dir[0];
            int y = start[1] + dir[1];
            int count = 0;
            while(x >= 0 && x < maze.length && y >= 0 && y < maze[0].length
            && maze[x][y] == 0) {
                x += dir[0];
                y += dir[1];
                count++;
            }
            if(distance[start[0]][start[1]] + count < distance[x - dir[0]][y - dir[1]]) {
                distance[x - dir[0]][y - dir[1]] = distance[start[0]][start[1]] + count;
                dfs(maze, new int[]{x - dir[0], y - dir[1]}, distance);
            }
        }
    }

//    bfs; time: O(m.n.max(m,n)), space: O(m.n)
    public int shortestDistance1(int[][] maze, int[] start, int[] destination) {
        int rows = maze.length, cols = maze[0].length;
        int[][] dirs = {{0,-1},{0,1},{-1,0},{1,0}};
        int[][] distance = new int[rows][cols];
        for(int[] row : distance)
            Arrays.fill(row, Integer.MAX_VALUE);
        distance[start[0]][start[1]] = 0;
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(start);
        while(!queue.isEmpty()) {
            int[] current = queue.poll();
            for(int[] dir : dirs) {
                int x = current[0] + dir[0];
                int y = current[1] + dir[1];
                int count = 0;
                while(x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] == 0) {
                    x += dir[0];
                    y += dir[1];
                    count++;
                }
                if(distance[current[0]][current[1]] + count < distance[x - dir[0]][y - dir[1]]) {
                    distance[x - dir[0]][y - dir[1]] = distance[current[0]][current[1]] + count;
                    queue.offer(new int[]{x - dir[0], y - dir[1]});
                }
            }
        }
        return distance[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 :
                distance[destination[0]][destination[1]];
    }
}

/*
There is a ball in a maze with empty spaces (represented as 0) and walls (represented as 1). The ball can go through the empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall. When the ball stops, it could choose the next direction.
Given the m x n maze, the ball's start position and the destination, where start = [startrow, startcol] and destination = [destinationrow, destinationcol], return the shortest distance for the ball to stop at the destination. If the ball cannot stop at destination, return -1.
The distance is the number of empty spaces traveled by the ball from the start position (excluded) to the destination (included).
You may assume that the borders of the maze are all walls (see examples).
Example 1:
Input: maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [4,4]
Output: 12
Explanation: One possible way is : left -> down -> left -> down -> right -> down -> right.
The length of the path is 1 + 1 + 3 + 1 + 2 + 2 + 2 = 12.
Example 2:
Input: maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [3,2]
Output: -1
Explanation: There is no way for the ball to stop at the destination. Notice that you can pass through the destination but you cannot stop there.
Example 3:
Input: maze = [[0,0,0,0,0],[1,1,0,0,1],[0,0,0,0,0],[0,1,0,0,1],[0,1,0,0,0]], start = [4,3], destination = [0,1]
Output: -1

Constraints:
m == maze.length
n == maze[i].length
1 <= m, n <= 100
maze[i][j] is 0 or 1.
start.length == 2
destination.length == 2
0 <= startrow, destinationrow < m
0 <= startcol, destinationcol < n
Both the ball and the destination exist in an empty space, and they will not be in the same position initially.
The maze contains at least 2 empty spaces.
 */

/*
We can view the given search space in the form of a tree. The root node of the tree represents the starting position. Four different routes are possible from each position i.e. left, right, up or down. These four options can be represented by 4 branches of each node in the given tree. Thus, the new node reached from the root traversing over the branch represents the new position occupied by the ball after choosing the corresponding direction of travel.
DFS:
Time complexity : O(m∗n∗max(m,n)). Complete traversal of maze will be done in the worst case. Here, m and n refers to the number of rows and columns of the maze. Further, for every current node chosen, we can travel upto a maximum depth of max(m,n) in any direction.
Space complexity : O(mn). distance array of size m∗n is used.

 */
