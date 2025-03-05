package graphs;

import java.util.*;

public class Reorder1466 {
    private int count = 0;
    public static void main(String[] args) {
        Reorder1466 r = new Reorder1466();
        int[][] connections = {{0,1},{1,3},{2,3},{4,0},{4,5}};
        System.out.println(r.minReorder(6,connections));
    }

//    dfs; time: O(n), space: O(n)
    public int minReorder(int n, int[][] connections) {
        Map<Integer, List<int[]>> adjMap = new HashMap<>();
        for(int[] edge : connections) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(new int[]{edge[1], 1});
            adjMap.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(new int[]{edge[0], 0});
        }
        dfs(0, -1, adjMap);
        return count;
    }

    private void dfs(int node, int parent, Map<Integer, List<int[]>> adjMap) {
        if(!adjMap.containsKey(node)) return;
        for(int[] adjacentInfo : adjMap.get(node)) {
            int neighbour = adjacentInfo[0];
            int sign = adjacentInfo[1];
            if(neighbour != parent) {
                count += sign;
                dfs(neighbour, node, adjMap);
            }
        }
    }

//    bfs; time: O(n), space: O(n)  [faster]
    public int minReorder1(int n, int[][] connections) {
        Map<Integer, List<int[]>> adjMap = new HashMap<>();
        for(int[] edge : connections) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(new int[]{edge[1], 1});
            adjMap.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(new int[]{edge[0], 0});
        }
        bfs(0, n, adjMap);
        return count;
    }

    private void bfs(int node, int n, Map<Integer, List<int[]>> adjMap) {
        Deque<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[n];
        queue.offer(node);
        visited[node] = true;
        while(!queue.isEmpty()) {
            node = queue.poll();
            if(!adjMap.containsKey(node)) continue;
            for(int[] adjacentInfo : adjMap.get(node)) {
                int neighbour = adjacentInfo[0];
                int sign = adjacentInfo[1];
                if(!visited[neighbour]) {
                    count += sign;
                    visited[neighbour] = true;
                    queue.offer(neighbour);
                }
            }
        }
    }
}

/*
There are n cities numbered from 0 to n - 1 and n - 1 roads such that there is only one way to travel between two different cities (this network form a tree). Last year, The ministry of transport decided to orient the roads in one direction because they are too narrow.
Roads are represented by connections where connections[i] = [ai, bi] represents a road from city ai to city bi.
This year, there will be a big event in the capital (city 0), and many people want to travel to this city.
Your task consists of reorienting some roads such that each city can visit the city 0. Return the minimum number of edges changed.
It's guaranteed that each city can reach city 0 after reorder.
Example 1:
Input: n = 6, connections = [[0,1],[1,3],[2,3],[4,0],[4,5]]
Output: 3
Explanation: Change the direction of edges show in red such that each node can reach the node 0 (capital).
Example 2:
Input: n = 5, connections = [[1,0],[1,2],[3,2],[3,4]]
Output: 2
Explanation: Change the direction of edges show in red such that each node can reach the node 0 (capital).
Example 3:
Input: n = 3, connections = [[1,0],[2,0]]
Output: 0

Constraints:
2 <= n <= 5 * 104
connections.length == n - 1
connections[i].length == 2
0 <= ai, bi <= n - 1
ai != bi
 */

/*
The caveat is that our edges are directed. To count the number of edges that are directed from a parent to its child node, we must traverse the entire tree. If there is an edge from a child to its parent node, we will be unable to reach the child from the parent.
To traverse the entire tree, we must find a way to get from node 0 to all of the nodes in any case. This is possible if the edges are treated as undirected. We add an opposite edge from node b to node a for every given edge in connections from node a to node b. Let us refer to the edge we added as an "artificial" edge and the edge present in connections as an "original" edge.
If we use an "artificial" edge to move from the parent node to the child node, we know that the original edge is directed from the child node to the parent node. We don't need to flip the "original" edge.
If we use an "original" edge to move from the parent node to the child node, it means we need to flip this edge. Whenever we encounter such an edge, we will increment our answer variable by 1.
We can distinguish between an "original" and an "artificial" edge in many different ways (assigning booleans, specific numbers, etc.). In this article, we will associate an extra value with each edge - 1 for "original" edges and 0 for "artificial" edges.

Time complexity: O(n).
We need O(n) time to initialize the adjacency list and O(n) to initialize the visit array.
Each queue operation in the BFS algorithm takes O(1) time, and a single node can only be pushed once, leading to O(n) operations for n nodes. We iterate over all the neighbors of each node that is popped out of the queue, so for an undirected edge, a given edge could be iterated at most twice (by nodes at both ends), resulting in O(e) operations total for all the nodes. As mentioned in the previous approach, O(e)=O(n) since the graph is a tree.
 */