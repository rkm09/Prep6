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
//    for iterative bfs, just replace with queue representation using poll, offer
    public boolean validTree1(int n, int[][] edges) {
        Map<Integer, List<Integer>> adjMap = new HashMap<>();
        for(int[] edge : edges) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            adjMap.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
        }
        Map<Integer, Integer> childParentMap = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();
        childParentMap.put(0, - 1);
        stack.push(0);
        while(!stack.isEmpty()) {
            int node = stack.pop();
            for(int neighbour : adjMap.getOrDefault(node, new ArrayList<>())) {
//                ignore trivial cycle(A->B->A) since it is an undirected graph
                if(childParentMap.get(node) == neighbour)
                    continue;
//                check if we have already seen this before, if so there must be a cycle
                if(childParentMap.containsKey(neighbour))
                    return false;
                childParentMap.put(neighbour, node);
                stack.push(neighbour);
            }
        }
        return childParentMap.size() == n;
    }

//    recursive dfs;
    public boolean validTree(int n, int[][] edges) {
        Map<Integer, List<Integer>> adjMap = new HashMap<>();
        for(int[] edge : edges) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            adjMap.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
        }
        Set<Integer> seen = new HashSet<>();
//        we return true, iff no cycles are detected and the whole graph has been traversed
        return dfs(0, - 1, adjMap, seen) && seen.size() == n;
    }

    private boolean dfs(int node, int parent, Map<Integer, List<Integer>> adjMap, Set<Integer> seen) {
        if(seen.contains(node)) return false;
        seen.add(node);
        for(int neighbour : adjMap.getOrDefault(node, new ArrayList<>())) {
            if(parent != neighbour) {
                boolean result = dfs(neighbour, node, adjMap, seen);
                if(!result)
                    return false;
            }
        }
        return true;
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

/*
Recall that a graph, G, is a tree iff the following two conditions are met:
G is fully connected. In other words, for every pair of nodes in G, there is a path between them.
G contains no cycles. In other words, there is exactly one path between each pair of nodes in G.
Recall that breadth-first search and depth-first search are almost the same "algorithm", just with a different data structure.
Time Complexity : O(N+E).
Creating the adjacency list requires initialising a list of length N, with a cost of O(N), and then iterating over and inserting E edges, for a cost of O(E). This gives us O(E)+O(N)=O(N+E).
Each node is added to the data structure once. This means that the outer loop will run N times. For each of the N nodes, its adjacent edges is iterated over once. In total, this means that all E edges are iterated over once by the inner loop. This, therefore, gives a total time complexity of O(N+E).
Because both parts are the same, we get a final time complexity of O(N+E).
Space Complexity : O(N+E).
The adjacency list is a list of length N, with inner lists with lengths that add to a total of E. This gives a total of O(N+E) space.
In the worst case, the stack/ queue will have all N nodes on it at the same time, giving a total of O(N) space.
In total, this gives us O(E+N) space.
 */