package daily.medium;

import java.util.*;

public class ValidTree261 {
    public static void main(String[] args) {
        int[][] edges = {{0,1},{1,2},{2,3},{1,4}};
        int[][] edges1 = {{0,1},{2,3}};
        ValidTree261 v = new ValidTree261();
        System.out.println(v.validTree(5, edges));
    }

//    iterative dfs; time: O(v + e), space: O(n)
    public boolean validTree(int n, int[][] edges) {
        Map<Integer, List<Integer>> adjMap = new HashMap<>();
        for(int[] edge : edges) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            adjMap.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
        }
        Map<Integer, Integer> parentMap = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();
        parentMap.put(0, - 1);
        stack.push(0);
        while(!stack.isEmpty()) {
            int node = stack.pop();
            for(int neighbour : adjMap.getOrDefault(node, new ArrayList<>())) {
                if(parentMap.get(node) == neighbour)
                    continue;
                if(parentMap.containsKey(neighbour))
                    return false;
                parentMap.put(neighbour, node);
                stack.push(neighbour);
            }
        }
        return parentMap.size() == n;
    }

}

/*
You have a graph of n nodes labeled from 0 to n - 1. You are given an integer n and a list of edges where edges[i] = [ai, bi] indicates that there is an undirected edge between nodes ai and bi in the graph.
Return true if the edges of the given graph make up a valid tree, and false otherwise.
Input: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]
Output: true
Example 2:
Input: n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]
Output: false
Constraints:
1 <= n <= 2000
0 <= edges.length <= 5000
edges[i].length == 2
0 <= ai, bi < n
ai != bi
There are no self-loops or repeated edges.
 */