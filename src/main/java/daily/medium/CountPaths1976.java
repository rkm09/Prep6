package daily.medium;

import java.util.*;

public class CountPaths1976 {
    private static final int MOD = (int) 1e9 + 7;
    public static void main(String[] args) {
        int[][] roads = {{1,0,10}};
        System.out.println(countPaths(2, roads));
    }

//    dijikstra; time: O((N+E)logN), space: O(N+E)
    public static int countPaths(int n, int[][] roads) {
//        build the adjacency list
        List<List<int[]>> graph = new ArrayList<>();
        for(int i = 0 ; i < n ; i++)
            graph.add(new ArrayList<>());
        for(int[] road : roads) {
            graph.get(road[0]).add(new int[] {road[1], road[2]});
            graph.get(road[1]).add(new int[] {road[0], road[2]});
        }
//        min heap for dijikstra [with respect to time]
        PriorityQueue<long[]> minHeap = new PriorityQueue<>(Comparator.comparingLong(a->a[1]));
        minHeap.offer(new long[]{0,0});
//        store shortest time to each node
        long[] shortestTime = new long[n];
        Arrays.fill(shortestTime, Long.MAX_VALUE);
//        number of ways to reach each node in the shortest time
        int[] pathCount = new int[n];
//        distance to source is 0
        shortestTime[0] = 0;
//        one way to reach the source node
        pathCount[0] = 1;
        while(!minHeap.isEmpty()) {
            long[] nodeInfo = minHeap.poll();
            int currNode = (int) nodeInfo[0];
//            current shortest time
            long currTime = nodeInfo[1];
//            skip outdated distances
            if(currTime > shortestTime[currNode])
                continue;
            for(int[] neighbour : graph.get(currNode)) {
                int neighbourNode = neighbour[0];
                int roadTime = neighbour[1];
//                found a new shortest path, update the shortest time and reset the path count
                if(currTime + roadTime < shortestTime[neighbourNode]) {
                    shortestTime[neighbourNode] = currTime + roadTime;
                    pathCount[neighbourNode] = pathCount[currNode];
                    minHeap.offer(new long[]{neighbourNode, shortestTime[neighbourNode]});
                } else if(currTime + roadTime == shortestTime[neighbourNode]) {
//                    found another way with the same shortest time, add to the path count
                    pathCount[neighbourNode] = (pathCount[neighbourNode] + pathCount[currNode]) % MOD;
                }
            }
        }
        return pathCount[n-1];
    }
}

/*
You are in a city that consists of n intersections numbered from 0 to n - 1 with bi-directional roads between some intersections. The inputs are generated such that you can reach any intersection from any other intersection and that there is at most one road between any two intersections.
You are given an integer n and a 2D integer array roads where roads[i] = [ui, vi, timei] means that there is a road between intersections ui and vi that takes timei minutes to travel. You want to know in how many ways you can travel from intersection 0 to intersection n - 1 in the shortest amount of time.
Return the number of ways you can arrive at your destination in the shortest amount of time. Since the answer may be large, return it modulo 109 + 7.
Example 1:
Input: n = 7, roads = [[0,6,7],[0,1,2],[1,2,3],[1,3,3],[6,3,3],[3,5,1],[6,5,1],[2,5,1],[0,4,5],[4,6,2]]
Output: 4
Explanation: The shortest amount of time it takes to go from intersection 0 to intersection 6 is 7 minutes.
The four ways to get there in 7 minutes are:
- 0 ➝ 6
- 0 ➝ 4 ➝ 6
- 0 ➝ 1 ➝ 2 ➝ 5 ➝ 6
- 0 ➝ 1 ➝ 3 ➝ 5 ➝ 6
Example 2:
Input: n = 2, roads = [[1,0,10]]
Output: 1
Explanation: There is only one way to go from intersection 0 to intersection 1, and it takes 10 minutes.

Constraints:
1 <= n <= 200
n - 1 <= roads.length <= n * (n - 1) / 2
roads[i].length == 3
0 <= ui, vi <= n - 1
1 <= timei <= 109
ui != vi
There is at most one road connecting any two intersections.
You can reach any intersection from any other intersection.
 */

/*
The problem guarantees that every intersection is reachable from any other intersection, ensuring that the graph is fully connected. Additionally, there is at most one road between any two intersections, so we do not have to consider duplicate edges.
One important detail is that the number of ways can be large, so the answer must be returned modulo 10
9+7. A common mistake is assuming that all roads have unique travel times, but the problem does not impose this restriction. Multiple roads may contribute to the shortest path calculation, and all must be considered. Since the roads are bidirectional, each one can be traversed in either direction. However, backtracking is unnecessary here, meaning we can ignore paths that visit the same road twice, as they will definitely take more time to reach the destination.
Approach 1: Dijkstra's Algorithm
Intuition
Dijkstra’s algorithm is the best fit for this problem because it efficiently finds the shortest path from a single source node to all other nodes in a graph with edges that have non-negative weights. The core principle of Dijkstra’s algorithm is that it always expands the currently known shortest path first, ensuring that when we reach a node, we do so in the minimum time possible.
Other approaches, such as Breadth-First Search (BFS), Depth-First Search (DFS), or the Bellman-Ford algorithm, would not be efficient. BFS does not work for weighted graphs unless modified with a priority queue, which ultimately turns it into Dijkstra’s algorithm. DFS would be highly inefficient because it would explore all possible paths, many of which would be unnecessary since they do not guarantee the shortest travel time. The brute-force approach of checking all paths using DFS would have an exponential time complexity and would be infeasible for large inputs.
Dijkstra’s algorithm is a greedy algorithm that uses a min-heap (priority queue) to process nodes in increasing order of their shortest known distance. The algorithm starts from the source node, which is node 0, and initializes its distance to 0 while setting the distance for all other nodes to infinity. The priority queue ensures that the node with the shortest known distance is always processed first.
For each node that is extracted from the priority queue, its neighbors are checked. If traveling through the current node provides a shorter path to a neighboring node, the shortest time to that node is updated, and the neighbor is added to the priority queue for further processing. This continues until all nodes have been processed, at which point the shortest time to each node is known.
The reason Dijkstra’s algorithm works correctly is that once a node is extracted from the priority queue, we are guaranteed that we have found the shortest possible path to that node. Any future attempts to update its distance will fail. This is because any other node that could have led to a shorter path already has a greater cost (otherwise, we would have extracted it first from the heap). Additionally, since all edges have a positive weight, any further paths to that node will only add a positive value to the total cost, increasing it further.
If we encounter another way to reach a node with the same shortest time, we do not reset the path count. Instead, we add the number of ways we could reach the previous node to the current node’s path count. Since the number of ways can be large, we take the result modulo 10
9+7 to prevent integer overflow.
This problem is notorious for its edge cases, which often cause issues when submitted. A common mistake is using INT_MAX (or similar equivalent in the language of your choice) as the initial value, assuming it is large enough to represent an unreachable node. However, for this problem, using INT_MAX causes incorrect results or even integer overflow in certain test cases.
To understand why, we need to analyze the constraints. The number of nodes (n) is at most 200, and the edge weights (time[i]) can be as large as 10
9. The worst-case scenario occurs when the shortest path to a node involves traversing 199 edges, forming a nearly linear path. In such a case, the total shortest path value can reach:
199×10 9=1.99×10 11
This is far greater than INT_MAX (which is 2.1×10 9). If we initialize our distances with INT_MAX, adding even a single edge weight (10
9) could exceed this limit, causing integer overflow. As a result, the algorithm may produce incorrect results when comparing distances, leading to failures in large test cases like test case 53.
To avoid this issue, we should initialize the shortestTime array with LLONG_MAX, which is 9.2×10
18, or use a sufficiently large constant like 1e12. Both options ensure that our algorithm can correctly compute distances without encountering overflow. This small but crucial adjustment is necessary to handle the problem’s constraints correctly.
Complexity Analysis
Let N be the number of nodes in the graph and E be the number of edges in the given road connections.
Time complexity: O((N+E)logN)
Constructing the adjacency list requires iterating over all E edges, taking O(E) time. Dijkstra's algorithm, implemented using a priority queue, processes each node at most once, and each edge is relaxed once. Since inserting and extracting from a priority queue takes O(logN) time, the total complexity of Dijkstra’s traversal is O((N+E)logN).
The inner loop of Dijkstra's algorithm iterates over all neighboring edges of the current node. Since each edge is processed only when it is relaxed, it results in an overall complexity of O(ElogN). Combining the adjacency list construction and Dijkstra’s algorithm, the total time complexity remains O((N+E)logN).
Space complexity: O(N+E)
 */