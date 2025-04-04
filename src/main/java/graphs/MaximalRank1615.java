package graphs;

import java.util.*;

public class MaximalRank1615 {
    public static void main(String[] args) {
        MaximalRank1615 m = new MaximalRank1615();
//        int[][] roads = {{0,1},{0,3},{1,2},{1,3}};
        int[][] roads = {{0,1},{1,2},{2,3},{2,4},{5,6},{5,7}};
        System.out.println(m.maximalNetworkRank(8, roads));
    }

//    in-degree of nodes; time: O(E + V^2), space: O(E)
    public int maximalNetworkRank(int n, int[][] roads) {
        Map<Integer, Set<Integer>> adjMap = new HashMap<>();
        for(int[] edge : roads) {
            adjMap.computeIfAbsent(edge[0], k -> new HashSet<>()).add(edge[1]);
            adjMap.computeIfAbsent(edge[1], k -> new HashSet<>()).add(edge[0]);
        }
        int maxRank = 0;
        for(int node1 = 0 ; node1 < n ; node1++) {
            for(int node2 = node1 + 1 ; node2 < n  ; node2++) {
                Set<Integer> edges1 = adjMap.getOrDefault(node1, new HashSet<>());
                Set<Integer> edges2 = adjMap.getOrDefault(node2, new HashSet<>());
                int currentRank = edges1.size() + edges2.size();
                if(edges1.contains(node2))
                    currentRank--;
                maxRank = Math.max(maxRank, currentRank);
            }
        }
        return maxRank;
    }
}

/*
There is an infrastructure of n cities with some number of roads connecting these cities. Each roads[i] = [ai, bi] indicates that there is a bidirectional road between cities ai and bi.
The network rank of two different cities is defined as the total number of directly connected roads to either city. If a road is directly connected to both cities, it is only counted once.
The maximal network rank of the infrastructure is the maximum network rank of all pairs of different cities.
Given the integer n and the array roads, return the maximal network rank of the entire infrastructure.
Example 1:
Input: n = 4, roads = [[0,1],[0,3],[1,2],[1,3]]
Output: 4
Explanation: The network rank of cities 0 and 1 is 4 as there are 4 roads that are connected to either 0 or 1. The road between 0 and 1 is only counted once.
Example 2:
Input: n = 5, roads = [[0,1],[0,3],[1,2],[1,3],[2,3],[2,4]]
Output: 5
Explanation: There are 5 roads that are connected to cities 1 or 2.
Example 3:
Input: n = 8, roads = [[0,1],[1,2],[2,3],[2,4],[5,6],[5,7]]
Output: 5
Explanation: The network rank of 2 and 5 is 5. Notice that all the cities do not have to be connected.

Constraints:
2 <= n <= 100
0 <= roads.length <= n * (n - 1) / 2
roads[i].length == 2
0 <= ai, bi <= n-1
ai != bi
Each pair of cities has at most one road connecting them.
 */

/*
The network rank of a pair of nodes is the sum of the in-degree (number of edges connected to a node) of each node minus the number of common edges between them.
 */