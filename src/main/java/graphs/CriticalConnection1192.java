package graphs;

import common.Pair;

import java.util.*;

public class CriticalConnection1192 {
    Map<Integer, List<Integer>> graph;
    Map<Integer, Integer> rank;
    Set<Pair<Integer, Integer>> connSet;
    public static void main(String[] args) {
        CriticalConnection1192 c = new CriticalConnection1192();
        int[][] arr = {{0,1},{1,2},{2,0},{1,3}};
       // int[][] arr = {{0,1},{1,2},{2,0},{1,3},{3,4},{4,5},{5,3}};
        List<List<Integer>> connections = new ArrayList<>();
        for(int[] a : arr) {
            List<Integer> li = List.of(a[0],a[1]);
            connections.add(li);
        }
        List<List<Integer>> res = c.criticalConnections(6, connections);
        System.out.println(res);
    }


//    dfs for cycle detection; time: O(V+E), space: O(E)
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> resList = new ArrayList<>();
        formGraph(n, connections);
        dfs(0,0);
        for(Pair<Integer, Integer> criticalConnection : connSet) {
            resList.add(new ArrayList<>(List.of(criticalConnection.getKey(), criticalConnection.getValue())));
        }
        return resList;
    }

    private void formGraph(int n, List<List<Integer>> connections) {
        graph = new HashMap<>();
        rank = new HashMap<>();
        connSet = new HashSet<>();
        for(List<Integer> edge : connections) {
            int u = edge.get(0), v = edge.get(1);
//            bidirectional edges
            graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            graph.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
            int sortedU = Math.min(u, v), sortedV = Math.max(u, v);
            connSet.add(new Pair<>(sortedU, sortedV));
        }
    }

    private int dfs(int node, int discoveryRank) {
//        if node is already visited, return the rank
        if(rank.get(node) != null)
            return rank.get(node);
//        update the rank of this node
        rank.put(node, discoveryRank);
//        this is the max we have seen till now, can start with [discovery + 1] instead of INT_MAX
//        note: it won't make any diff if you rather choose to start with discoveryRank itself since it will still be greater than previous level when it returns from dfs call
        int minRank = discoveryRank;
        for(int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            Integer neighborRank = rank.get(neighbor);
//            skip the parent
            if(neighborRank != null && neighborRank == discoveryRank - 1)
                continue;
//            recurse on the neighbor
            int recursiveRank = dfs(neighbor, discoveryRank + 1);
//            step 1: check if the edge needs to be discarded
            if(recursiveRank <= discoveryRank) {
                int sortedU = Math.min(node, neighbor), sortedV = Math.max(node, neighbor);
                connSet.remove(new Pair<>(sortedU, sortedV));
            }
//            step 2: update minimum rank if needed
            minRank = Math.min(minRank, recursiveRank);
        }
        return minRank;
    }
}

/*
There are n servers numbered from 0 to n - 1 connected by undirected server-to-server connections forming a network where connections[i] = [ai, bi] represents a connection between servers ai and bi. Any server can reach other servers directly or indirectly through the network.
A critical connection is a connection that, if removed, will make some servers unable to reach some other server.
Return all critical connections in the network in any order.
Example 1:
Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
Output: [[1,3]]
Explanation: [[3,1]] is also accepted.
Example 2:
Input: n = 2, connections = [[0,1]]
Output: [[0,1]]

Constraints:
2 <= n <= 105
n - 1 <= connections.length <= 105
0 <= ai, bi <= n - 1
ai != bi
There are no repeated connections.
 */

/*
dfs for cycle detection:
An edge is a critical connection, if and only if it is not in a cycle.
That being said, we would need to see what variations we can make to the standard depth-first traversal to catch all cycles in the graph and then remove the corresponding edges along the way. For this, we will look at the concept of a rank of a node. This is very similar to the concept of discovery times in Tarjan's algorithm for those who are familiar with that algorithm. Since this is a graph with no designated concept of a root node like in the case of trees, we can consider any node to be the root node of our graph. Essentially, we need some node to start the traversal from, and that node becomes the root node for all intents and purposes in our algorithm.
The rank of the root node is always 0. If a node has not yet been visited, we'll keep the rank as null/None (you can use any sentinel value here except for -1 - you'll see why in the implementation.)
So how does this rank help us in detecting cycles in the graph? Well, it works exactly like keeping a set of visited nodes would work. At each step of our traversal, we maintain the rank of the nodes we've come across until now on the current path. If at any point, we come across a neighbor that has a rank lower than the current node's rank, we know that the neighbor must have already been visited. In other words, if we started along a path with rank 0 from the root node and are currently at a node with rank m and now we discover a node that already has a rank assigned to it and that value is 0 <= n < m, then it implies the presence of a cycle.
Time Complexity: O(V+E) where V represents the number of vertices and E represents the number of edges in the graph. We process each node exactly once thanks to the rank dictionary also acting as a "visited" safeguard at the top of the dfs function. Since the problem statement mentions that the graph is connected, that means E>=V and hence, the overall time complexity would be dominated by the number of edges i.e. O(E).
Space Complexity: O(E). The overall space complexity is a sum of the space occupied by the connDict dictionary, rank dictionary, and graph data structure. E+V+(V+E) = O(E).
 */