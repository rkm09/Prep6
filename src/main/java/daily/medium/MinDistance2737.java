package daily.medium;

import java.util.*;

public class MinDistance2737 {
    public static void main(String[] args) {
        Integer[][] edgesArr = {{0,1,1},{1,2,3},{2,3,2},{0,3,4}};
        Integer[][] edgesArr1 = {{0,1,4},{3,0,6}};
        int[] marked = {2,3};
        int[] marked1 = {2,1};
        List<List<Integer>> edges = new ArrayList<>();
        for(Integer[] row : edgesArr1) {
            edges.add(Arrays.asList(row));
        }
//        System.out.println(minimumDistance(4, edges, 0, marked));
        System.out.println(minimumDistance1(4, edges, 3, marked1));
    }


//    dijkstra; time: O((m+n)logn), space: O(n + m)  [fast]
    public static int minimumDistance(int n, List<List<Integer>> edges, int s, int[] marked) {
        List<List<int[]>> graph = new ArrayList<>();
        Set<Integer> markedSet = new HashSet<>();
        for(int node : marked)
            markedSet.add(node);
        for(int i = 0 ; i < n ; i++)
            graph.add(new ArrayList<>());
        for(List<Integer> edge : edges) {
            int u = edge.get(0), v = edge.get(1), w = edge.get(2);
            graph.get(u).add(new int[] {v, w});
        }
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a->a[1]));
        minHeap.offer(new int[] {s, 0});
        int[] shortestDistance = new int[n];
        Arrays.fill(shortestDistance, Integer.MAX_VALUE);
        shortestDistance[s] = 0;
        while(!minHeap.isEmpty()) {
            int[] nodeInfo = minHeap.poll();
            int node = nodeInfo[0], distance = nodeInfo[1];
            if(markedSet.contains(node))
                return distance;
            for(int[] neighbourInfo : graph.get(node)) {
                int neighbour = neighbourInfo[0], neighbourDistance = neighbourInfo[1];
                int newDistance = distance + neighbourDistance;
                if(newDistance < shortestDistance[neighbour]) {
                    shortestDistance[neighbour] = newDistance;
                    minHeap.offer(new int[]{neighbour, newDistance});
                }
            }
        }

        return -1;
    }

//    optimised bellman ford; time: O(m.n), space: O(m.n) [faster than normal bellman]
    public static int minimumDistance1(int n, List<List<Integer>> edges, int s, int[] marked) {
        int[] distance = new int[n];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[s] = 0;
        for(int iteration = 0 ; iteration < n - 1 ; iteration++) {
            boolean isUpdated = false;
            for(List<Integer> edge : edges) {
                int to = edge.get(0), from = edge.get(1), weight = edge.get(2);
                if(distance[to] != Integer.MAX_VALUE && distance[to] + weight < distance[from]) {
                    distance[from] = weight + distance[to];
                    isUpdated = true;
                }
            }
            if(!isUpdated) break;
        }
        int minDistance = Integer.MAX_VALUE;
        for(int node : marked)
            minDistance = Math.min(minDistance, distance[node]);
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }


//    shortest path faster algorithm [SPFA]; time: O(m.n), space: O(m.n) [fast]
    public static int minimumDistance2(int n, List<List<Integer>> edges, int s, int[] marked) {
//        build the graph
        List<List<int[]>> graph = new ArrayList<>();
        for(int i = 0 ; i < n ; i++)
            graph.add(new ArrayList<>());
        for(List<Integer> edge : edges) {
            int u = edge.get(0), v = edge.get(1), w = edge.get(2);
            graph.get(u).add(new int[] {v, w});
        }
//       distance array
        int[] distance = new int[n];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[s] = 0;
//        track nodes in queue
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(s);
        boolean[] inQueue = new boolean[n];
        inQueue[s] = true;
        while(!queue.isEmpty()) {
            int current = queue.poll();
            inQueue[current] = false;
//            explore neighbours
            for(int[] next : graph.get(current)) {
                int nextNode = next[0];
                int weight = next[1];
//                relaxation step
                if(distance[nextNode] > distance[current] + weight) {
                    distance[nextNode] = distance[current] + weight;
//                    add to queue if not already in queue
                    if(!inQueue[nextNode]) {
                        queue.offer(nextNode);
                        inQueue[nextNode] = true;
                    }
                }
            }
        }
//        find min distance to any marked node
        int minDistance = Integer.MAX_VALUE;
        for(int node : marked)
            minDistance = Math.min(minDistance, distance[node]);
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }
}

/*
You are given a positive integer n which is the number of nodes of a 0-indexed directed weighted graph and a 0-indexed 2D array edges where edges[i] = [ui, vi, wi] indicates that there is an edge from node ui to node vi with weight wi.
You are also given a node s and a node array marked; your task is to find the minimum distance from s to any of the nodes in marked.
Return an integer denoting the minimum distance from s to any node in marked or -1 if there are no paths from s to any of the marked nodes.
Example 1:
Input: n = 4, edges = [[0,1,1],[1,2,3],[2,3,2],[0,3,4]], s = 0, marked = [2,3]
Output: 4
Explanation: There is one path from node 0 (the green node) to node 2 (a red node), which is 0->1->2, and has a distance of 1 + 3 = 4.
There are two paths from node 0 to node 3 (a red node), which are 0->1->2->3 and 0->3, the first one has a distance of 1 + 3 + 2 = 6 and the second one has a distance of 4.
The minimum of them is 4.
Example 2:
Input: n = 5, edges = [[0,1,2],[0,2,4],[1,3,1],[2,3,3],[3,4,2]], s = 1, marked = [0,4]
Output: 3
Explanation: There are no paths from node 1 (the green node) to node 0 (a red node).
There is one path from node 1 to node 4 (a red node), which is 1->3->4, and has a distance of 1 + 2 = 3.
So the answer is 3.
Example 3:
Input: n = 4, edges = [[0,1,1],[1,2,3],[2,3,2]], s = 3, marked = [0,1]
Output: -1
Explanation: There are no paths from node 3 (the green node) to any of the marked nodes (the red nodes), so the answer is -1.

Constraints:
2 <= n <= 500
1 <= edges.length <= 104
edges[i].length = 3
0 <= edges[i][0], edges[i][1] <= n - 1
1 <= edges[i][2] <= 106
1 <= marked.length <= n - 1
0 <= s, marked[i] <= n - 1
s != marked[i]
marked[i] != marked[j] for every i != j
The graph might have repeated edges.
The graph is generated such that it has no self-loops.
 */

/*
Dijkstra:
When solving shortest-path problems in graphs, the first algorithm that comes to mind is Dijkstra’s Algorithm. This well-known approach is especially effective for finding the shortest distance from a source node to other nodes in a weighted graph, as long as all edge weights are non-negative.
The core idea behind Dijkstra’s Algorithm is to explore the graph outward from the source node, always moving in order of increasing distance. This greedy strategy ensures that once we reach a node, we have found the shortest possible path to it.

One micro optimization for our specific problem is that we don't actually need to find the shortest paths to all nodes. Since we're only interested in finding the minimum distance to any marked node, we can terminate our search as soon as we encounter the first marked node during our exploration. This is because Dijkstra always processes nodes in order of increasing distance from the source. So if the first marked node we encounter has a distance of 10, no other marked node that we haven't processed yet can have a shorter distance.
Let n be the number of nodes and m be the number of edges in the graph.
Time complexity: O((n+m)logn)
The algorithm begins by converting the marked array into a set, which takes O(n) time. Then, it constructs the adjacency list representation of the graph in O(n+m), where each node and its edges are added once.
The main part of the algorithm is Dijkstra’s shortest path computation, which processes each node at most once and each edge at most once. The priority queue operations (insertion and deletion) take O(logn) time per operation. Since each node is added to the queue at most once and each edge results in at most one update operation, the total complexity of Dijkstra’s algorithm is O((n+m)logn).

Bellman:
Dijkstra’s Algorithm is a great choice for finding shortest paths in graphs with non-negative edge weights, but it fails when negative weights are involved. The Bellman-Ford Algorithm, on the other hand, is designed to handle graphs with negative weights, making it more general. While we don't have negative weights in this problem, understanding Bellman-Ford helps us see an alternative approach that works under broader conditions.
The core idea behind Bellman-Ford is straightforward: in a graph with n nodes, the shortest path between any two nodes can have at most n - 1 edges. If a path has n or more edges, it must contain a cycle. Since we're only interested in simple paths (paths without cycles), we never need more than n - 1 edges to reach any node optimally.
Now, let’s apply this to our problem. We start at node s and want to determine the shortest path to any node in the marked array. Instead of immediately finding the best path, Bellman-Ford gradually refines our understanding of distances by repeatedly considering all edges in the graph.
To implement this approach, we first initialize an array dist, where dist[i] represents the shortest known distance from s to node i. Initially, all values are set to infinity (or a very large number), except for s, which starts at 0 since it takes no cost to reach itself. This setup reflects our initial knowledge: we know how to reach s but have no information about the shortest paths to other nodes.
The algorithm’s core operation is edge relaxation. This means checking every edge (u, v, weight) in the graph and updating dist[v] if we find a shorter path to v through u. If dist[u] + weight < dist[v], we update dist[v]. This process is repeated exactly n - 1 times because, in the worst case, the shortest path to any node may require n - 1 edges.
Why n - 1 iterations? The first pass ensures that we find the shortest one-edge paths. The second pass builds on that, discovering the shortest two-edge paths, and so on. By the time we've repeated this process n - 1 times, all shortest paths have been fully propagated throughout the graph.

Optimised Bellman:
break out of loop, if no update on specific iteration;

Shortest Path Faster Algorithm (SPFA):
Now, let's explore a lesser-known but powerful algorithm called the Shortest Path Faster Algorithm (SPFA). This algorithm is an optimization of Bellman-Ford and combines ideas from both Bellman-Ford and Breadth-First Search (BFS) to achieve better performance in many cases.
To understand SPFA, let’s consider why Bellman-Ford can be inefficient. When relaxing edges in the Bellman-Ford algorithm, many iterations might not lead to any improvements in the distance values. SPFA addresses this inefficiency by only considering nodes whose distances have been updated recently, as only these nodes have the potential to update their neighbors.
Think of it like a road network: if the estimated travel time to a city changes, it might affect nearby cities but not those farther away. SPFA follows this natural flow of information by recalculating routes only through cities whose travel times have changed.
The key idea behind SPFA is that a node’s outgoing edges need to be checked only if its shortest known distance has been updated. To achieve this, SPFA maintains a queue of "active" nodes i.e., nodes that have recently had their shortest distances improved. When we improve the distance to a node, we add it to the queue (if it's not already there). Then, we process the queue by repeatedly:
Removing a node from the queue.
Exploring all its outgoing edges, potentially updating distances to neighbors.
Adding any neighbor whose distance was improved to the queue.
Along with the queue and the distance array used in the Bellman-Ford Algorithm, SPFA also maintains a boolean array to track which nodes are currently in the queue. During relaxation, we only add nodes that are not currently enqueued to prevent duplicate entries.
Once the queue is empty, the shortest paths from the source to all reachable nodes have been found. The final step is to check the distances of all marked nodes and return the smallest among them.
What makes SPFA particularly useful is that it adapts to the structure of the graph. In sparse graphs or graphs where the shortest paths are discovered quickly, SPFA can be significantly faster than the standard Bellman-Ford algorithm. However, it's worth noting that SPFA doesn't improve the worst-case time complexity – in worst-case scenarios, a node might enter the queue multiple times, potentially up to the number of edges in the graph.
 */