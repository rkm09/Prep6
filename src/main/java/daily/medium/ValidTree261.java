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

//    advanced graph theory [there should be exactly n - 1 edges]; time: O(n), space: O(v + e)
//    bfs [for iterative dfs replace with stack]
    public boolean validTree2(int n, int[][] edges) {
        if(edges.length != n - 1) return false;
        Map<Integer, List<Integer>> adjMap = new HashMap<>();
        for(int[] edge : edges) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            adjMap.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
        }
        Set<Integer> seen = new HashSet<>();
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        seen.add(0);
        while(!queue.isEmpty()) {
            int node = queue.poll();
            for(int neighbour : adjMap.getOrDefault(node, new ArrayList<>())) {
//                trivial cycle [just continue]
                if(seen.contains(neighbour)) continue;
                seen.add(neighbour);
                queue.offer(neighbour);
            }
        }
        return seen.size() == n;
    }

//    recursive dfs;
    public boolean validTree3(int n, int[][] edges) {
        if(edges.length != n - 1) return false;
        Map<Integer, List<Integer>> adjMap = new HashMap<>();
        for(int[] edge : edges) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            adjMap.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
        }
        Set<Integer> seen = new HashSet<>();
        dfsHelper(0, adjMap, seen);
        return seen.size() == n;
    }

    private void dfsHelper(int node, Map<Integer, List<Integer>> adjMap, Set<Integer> seen) {
//        ignore trivial cycles and return
        if(seen.contains(node)) return;
        seen.add(node);
        for(int neighbour : adjMap.getOrDefault(node, new ArrayList<>())) {
            dfsHelper(neighbour, adjMap, seen);
        }
    }

//    union find; time: O(n(alphaN)), space: O(n)   [fastest!]
    public boolean validTree4(int n, int[][] edges) {
        if(edges.length != n - 1) return false;
        UnionFind uf = new UnionFind(n);
        for(int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }
        return uf.getCount() == 1;
    }

    class UnionFind {
        private int[] parent;
        private int[] rank;
        private int count;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            count = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
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
                if(rank[rootX] > rank[rootY])
                    parent[rootY] = rootX;
                else if(rank[rootY] > rank[rootX])
                    parent[rootX] = rootY;
                else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
                count--;
            }
        }

        private int getCount() {
            return count;
        }
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
Basic:
Time Complexity : O(N+E).
Creating the adjacency list requires initialising a list of length N, with a cost of O(N), and then iterating over and inserting E edges, for a cost of O(E). This gives us O(E)+O(N)=O(N+E).
Each node is added to the data structure once. This means that the outer loop will run N times. For each of the N nodes, its adjacent edges is iterated over once. In total, this means that all E edges are iterated over once by the inner loop. This, therefore, gives a total time complexity of O(N+E).
Because both parts are the same, we get a final time complexity of O(N+E).
Space Complexity : O(N+E).
The adjacency list is a list of length N, with inner lists with lengths that add to a total of E. This gives a total of O(N+E) space.
In the worst case, the stack/ queue will have all N nodes on it at the same time, giving a total of O(N) space.
In total, this gives us O(E+N) space.
Advanced(n-1):
Time Complexity : O(N).
When E != N−1, we simply return false. Therefore, the worst case is when E=N−1. Because E is proportional to N, we'll say E=N to simplify the analysis.
As said above, creating an adjacency list has a time complexity of O(N+E). Because E is now bounded by N, we can reduce this slightly to O(N+N)=O(N).
The iterative breadth-first search and depth-first search are almost identical. Each node is put onto the queue/stack once, ensured by the seen set. Therefore, the inner "neighbour" loop runs once for each node. Across all nodes, the number of cycles this loop does is the same as the number of edges, which is simply N. Therefore, these two algorithms have a time complexity of O(N).
UnionFind:
Time Complexity : O(N⋅α(N)).
When E != N−1, we simply return false. Therefore, the worst case is when E=N−1. Because E is proportional to N, we'll say E=N to simplify the analysis.
We are putting each of the N edges into the UnionFind data structure, using the union(...) method. The union(...) method itself has no loops or recursion, so the entire cost of calling it is dependent on the cost of the find(...) method that it calls.
find(...)'s cost is dependent on how far the node it was searching for is from the root. Using the naïve implementation of union find, this depth could be N. If this was the case for all the calls, we'd have a final cost of O(N^2).
However, remember those optimizations we did? Those keep the tree depths very shallow. It turns out that find(...) amortizes to O(α(N)), where α is the Inverse Ackermann Function. The incredible thing about this function is that it grows so slowly that N will never go higher than 4 in the universe as we know it! So while in "practice" it is effectively O(1), in "theory" it is not.
 */