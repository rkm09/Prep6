package daily.medium;

import java.util.*;

public class CompleteComponents2685 {
    public static void main(String[] args) {
        CompleteComponents2685 c = new CompleteComponents2685();
        int[][] edges = {{0,1},{0,2},{1,2},{3,4}};
        System.out.println(c.countCompleteComponents(6, edges));

    }

//    union find; time: O(n + alpha(m)), space: O(n)
    public int countCompleteComponents(int n, int[][] edges) {
        UnionFind dsu = new UnionFind(n);
        Map<Integer, Integer> edgeCount = new HashMap<>();
//        connect components with edges
        for(int[] edge : edges) {
            dsu.union(edge[0], edge[1]);
        }
//        count edges in each component
        for(int[] edge : edges) {
            int root = dsu.find(edge[0]);
            edgeCount.put(root, edgeCount.getOrDefault(root, 0) + 1);
        }
//        check if each component is complete
        int totalCount = 0;
        for(int vertex = 0 ; vertex < n ; vertex++) {
            if(dsu.find(vertex) == vertex) {
                int nodeCount = dsu.getComponentSize(vertex);
                int expectedEdgeCount = nodeCount * (nodeCount - 1) / 2;
                if(edgeCount.getOrDefault(vertex, 0) == expectedEdgeCount)
                    totalCount++;
            }
        }
        return totalCount;
    }
}

class UnionFind {
    private final int[] parent;
    private final int[] size;
    UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        Arrays.fill(size, 1);
        for(int i = 0 ; i < n ; i++)
            parent[i] = i;
    }
//    find root by path compression
    public int find(int i) {
        if(parent[i] != i)
            parent[i] = find(parent[i]);
        return parent[i];
    }
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
//        union by size
        if(rootX != rootY) {
//            merge smaller component into larger
            if(size[rootX] > size[rootY]) {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }
            else {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            }
        }
    }

    public int getComponentSize(int vertex) {
        return size[vertex];
    }
}

/*
You are given an integer n. There is an undirected graph with n vertices, numbered from 0 to n - 1. You are given a 2D integer array edges where edges[i] = [ai, bi] denotes that there exists an undirected edge connecting vertices ai and bi.
Return the number of complete connected components of the graph.
A connected component is a subgraph of a graph in which there exists a path between any two vertices, and no vertex of the subgraph shares an edge with a vertex outside of the subgraph.
A connected component is said to be complete if there exists an edge between every pair of its vertices.
Example 1:
Input: n = 6, edges = [[0,1],[0,2],[1,2],[3,4]]
Output: 3
Explanation: From the picture above, one can see that all of the components of this graph are complete.
Example 2:
Input: n = 6, edges = [[0,1],[0,2],[1,2],[3,4],[3,5]]
Output: 1
Explanation: The component containing vertices 0, 1, and 2 is complete since there is an edge between every pair of two vertices. On the other hand, the component containing vertices 3, 4, and 5 is not complete since there is no edge between vertices 4 and 5. Thus, the number of complete components in this graph is 1.

Constraints:
1 <= n <= 50
0 <= edges.length <= n * (n - 1) / 2
edges[i].length == 2
0 <= ai, bi <= n - 1
ai != bi
There are no repeated edges.
 */

/*
Union Find:
Let n be the number of vertices and m be the number of edges in the given graph.
Time complexity: O(n+mα(n))
The solution uses a Union-Find data structure with path compression and union by size. Building the Union-Find structure takes O(n) time for initialization. Processing all edges through union operations takes O(mα(n)) time, where α(n) is the inverse Ackermann function, which grows extremely slowly and is practically constant.
Counting edges in each component requires iterating through all edges again, taking O(m) time. Finally, checking if each component is complete involves iterating through all vertices once, taking O(n) time.
Therefore, the overall time complexity is O(n+mα(n)), which is essentially linear in practice.
Space complexity: O(n)
The Union-Find data structure uses two arrays of size n for parent pointers and component sizes, requiring O(n) space. The edge count map stores at most n entries (one for each potential component root), requiring O(n) space. Therefore, the overall space complexity is O(n).
 */