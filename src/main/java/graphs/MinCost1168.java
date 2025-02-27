package graphs;

import daily.hard.MinCost1368;

import java.util.*;

public class MinCost1168 {
    public static void main(String[] args) {
        MinCost1168 m = new MinCost1168();
        int[][] pipes = {{1,2,1},{2,3,1}};
        int[] wells = {1,2,2};
        System.out.println(m.minCostToSupplyWater(3, wells, pipes));
    }

//    prim's algorithm; time: O(M+N)log(M+N), space: O(M+N)
    public int minCostToSupplyWater1(int n, int[] wells, int[][] pipes) {
//        representation of graph as adjacency list
        List<List<int[]>> graph = new ArrayList<>();
//        min heap to maintain the edges to be visited based on cost
        PriorityQueue<int[]> edgesHeap = new PriorityQueue<>(Comparator.comparingInt(a->a[0]));
        for(int i = 0 ; i < n + 1 ; i++)
            graph.add(new ArrayList<>());
//        add a virtual vertex indexed at 0
//        then add an edge to each of the house weighted by the cost
        for(int i = 0 ; i < wells.length ; i++) {
            int[] virtualEdge = new int[]{wells[i], i + 1};
            graph.get(0).add(virtualEdge);
            edgesHeap.offer(virtualEdge);
        }
//        add the bidirectional edges to the graph
        for(int[] edge : pipes) {
            graph.get(edge[0]).add(new int[]{edge[2], edge[1]});
            graph.get(edge[1]).add(new int[]{edge[2], edge[0]});
        }
        int totalCost = 0;
        Set<Integer> visited = new HashSet<>();
        visited.add(0);
//        kick off the exploration from the virtual vertex 0
        while(visited.size() < n + 1) {
            int[] edge = edgesHeap.poll();
            int nextHouse = edge[1];
            int cost = edge[0];
            if(visited.contains(nextHouse))
                continue;
//            add the next vertex to the visited set
            visited.add(nextHouse);
            totalCost += cost;

//            expand the candidates of edges to choose from in the next round
            for(int[] neighbourEdge : graph.get(nextHouse)){
                if(!visited.contains(neighbourEdge[1]))
                    edgesHeap.offer(neighbourEdge);
            }
         }
        return totalCost;
    }

//    kruskal's algo; Union find; time: O(N+M)log(M+N), space: O(M+N)
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        List<int[]> orderedEdges = new ArrayList<>();
//        add the virtual vertex at index 0, along with the new edges
//        note: use wells.length instead of n + 1, else it will throw out of bound at wells[i]
        for(int i = 0 ; i < wells.length ; i++)
            orderedEdges.add(new int[]{0, i + 1, wells[i]});
//        add the existing edges
        orderedEdges.addAll(Arrays.asList(pipes));
//        sort the edges based on their cost
        orderedEdges.sort(Comparator.comparingInt(a -> a[2]));
        int totalCost = 0;
//        iterate through the ordered edges, note: size : n + 1
        UnionFind uf = new UnionFind(n + 1);
        for(int[] edge : orderedEdges) {
            int house1 = edge[0];
            int house2 = edge[1];
            int cost = edge[2];
//            determine if we should add the new edge to the final MST
//            note, we are not using custom isConnected() and instead using a modified version of union itself
//            so that there is a single call to find()
            if(uf.union(house1, house2))
                totalCost += cost;
        }
        return totalCost;
    }

    static class UnionFind {
        private final int[] parent;
        private final int[] rank;
        UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for(int i = 0 ; i < size ; i++)
                parent[i] = i;
        }

        public int find(int i) {
            if(parent[i] != i)
                parent[i] = find(parent[i]);
            return parent[i];
        }

        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if(rootX == rootY)
                return false;
            if(rank[rootX] > rank[rootY])
                parent[rootY] = rootX;
            else if(rank[rootX] < rank[rootY])
                parent[rootX] = rootY;
            else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            return true;
        }
    }
}

/*
There are n houses in a village. We want to supply water for all the houses by building wells and laying pipes.
For each house i, we can either build a well inside it directly with cost wells[i - 1] (note the -1 due to 0-indexing), or pipe in water from another well to it. The costs to lay pipes between houses are given by the array pipes where each pipes[j] = [house1j, house2j, costj] represents the cost to connect house1j and house2j together using a pipe. Connections are bidirectional, and there could be multiple valid connections between the same two houses with different costs.
Return the minimum total cost to supply water to all houses.
Example 1:
Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
Output: 3
Explanation: The image shows the costs of connecting houses using pipes.
The best strategy is to build a well in the first house with cost 1 and connect the other houses to it with cost 2 so the total cost is 3.
Example 2:
Input: n = 2, wells = [1,1], pipes = [[1,2,1],[1,2,2]]
Output: 2
Explanation: We can supply water with cost two using one of the three options:
Option 1:
  - Build a well inside house 1 with cost 1.
  - Build a well inside house 2 with cost 1.
The total cost will be 2.
Option 2:
  - Build a well inside house 1 with cost 1.
  - Connect house 2 with house 1 with cost 1.
The total cost will be 2.
Option 3:
  - Build a well inside house 2 with cost 1.
  - Connect house 1 with house 2 with cost 1.
The total cost will be 2.
Note that we can connect houses 1 and 2 with cost 1 or with cost 2 but we will always choose the cheapest option.

Constraints:
2 <= n <= 104
wells.length == n
0 <= wells[i] <= 105
1 <= pipes.length <= 104
pipes[j].length == 3
1 <= house1j, house2j <= n
0 <= costj <= 105
house1j != house2j
 */

/*
Prim's Algorithm:
A greedy algorithm used to find the minimum spanning tree in a weighted and undirected graph.
The algorithm operates by building the tree one vertex at a time, from an arbitrary starting vertex, at each step adding the cheapest possible connection from any vertex in the tree to a vertex that is not in the tree.
Starting from an arbitrary vertex, Prim's algorithm grows the minimum spanning tree by adding one vertex at a time to the tree.
The choice of a vertex is based on the greedy strategy, i.e. the addition of the new vertex incurs the minimum cost.
First, given the input, we need to build a graph representation with the adjacency list.
Note that, since the graph is undirected (i.e. bidirectional), for each pipe, we need to add two entries in the adjacency list, with each end of the pipe as a starting vertex.
Also, to convert our problem into the MST problem, we need to add a virtual vertex (we index it as 0) together with the additional n edges to each house.
Starting from the virtual vertex, we build the MST by iteratively adding one vertex at a time.
Note, when using Prim's algorithm, we can use any vertex as a starting point.
Here, for the sake of convenience, we start from the newly-added virtual vertex.
The process of building MST consists of a loop with the following substeps:
Each iteration, we pop an element from the heap. This element contains a vertex along with the cost that is associated with the edge that connecting the vertex to the tree.
The vertex is chosen if it is not already in the tree.
We know that the cost of this vertex is minimal among all choices because it was popped from the heap.
Once the vertex is added, we then examine its neighboring vertices.
Specifically, we add these vertices along with their edges into the heap as the candidates for the next round of selection.
The loop terminates when we have added all the vertices from the graph into the MST.
Time:
Time Complexity: O((N+M)⋅log(N+M))
To build the graph, we iterate through the houses and pipes in the input, which takes O(N+M) time.
While building the MST, we might need to iterate through all the edges in the graph in the worst case, which amounts to N+M in total.
For each edge, it would enter and exit the heap data structure at most once. Entry of edge into heap (i.e. push operation) takes log(N+M) time, while the exit of edge (i.e. pop operation) takes a constant time.
Therefore, the time complexity of the MST construction process is O((N+M)⋅log(N+M)).

Kruskal's:
Similar to Prim's algorithm, Kruskal's algorithm applies the greedy strategy to incrementally add new edges to the final solution.
A major difference between them is that in Prim's algorithm the MST (minimal spanning tree) remains connected as a whole throughout the entire process, while in Kruskal's algorithm, the tree is formed by merging the disjoint components together.
Rather than adding vertices as in Prim's algorithm, the Kruskal's algorithm focuses on adding edges.
Furthermore, in Kruskal's algorithm, we consider all edges at once ranked by their costs, while in Prim's algorithm, although edges are ranked by costs in a heap or priority queue, at each iteration, we only explore edges that are connected to the vertices that are already in the MST.
In the example on the right, we should not add the new edge. Because it does not help us to make the current MST more connected, since all vertices are connected already.
A more concise criteria to determine whether we should add a new edge in Kruskal's algorithm is that whether both ends of the edge belong to the same component (group).
Time:
If K operations, either Union or Find, are applied to L elements, the total run time is O(K⋅log∗L), where log∗is the iterated logarithm.
Let N be the number of houses, and M be the number of pipes from the input.

Time Complexity: O((N+M)⋅log(N+M))
First, we build a list of edges, which takes O(N+M) time.
We then sort the list of edges, which takes O((N+M)⋅log(N+M)) time.
At the end, we iterate through the sorted edges. For each iteration, we invoke a Union-Find operation.
To sum up, the overall time complexity of the algorithm is O((N+M)⋅log(N+M)) which is dominated by the sorting step.
 */