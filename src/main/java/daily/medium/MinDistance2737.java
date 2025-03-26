package daily.medium;

import java.util.*;

public class MinDistance2737 {
    public static void main(String[] args) {
        Integer[][] edgesArr = {{0,1,1},{1,2,3},{2,3,2},{0,3,4}};
        int[] marked = {2,3};
        List<List<Integer>> edges = new ArrayList<>();
        for(Integer[] row : edgesArr) {
            edges.add(Arrays.asList(row));
        }
        System.out.println(minimumDistance(4, edges, 0, marked));
    }


//    dijikstra; time: O((m+n)logn), space: O(n + m)
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
Dijikstra:
Dijkstra’s Algorithm is a great choice for finding shortest paths in graphs with non-negative edge weights, but it fails when negative weights are involved.
One micro optimization for our specific problem is that we don't actually need to find the shortest paths to all nodes. Since we're only interested in finding the minimum distance to any marked node, we can terminate our search as soon as we encounter the first marked node during our exploration. This is because Dijkstra always processes nodes in order of increasing distance from the source. So if the first marked node we encounter has a distance of 10, no other marked node that we haven't processed yet can have a shorter distance.
Let n be the number of nodes and m be the number of edges in the graph.
Time complexity: O((n+m)logn)
The algorithm begins by converting the marked array into a set, which takes O(n) time. Then, it constructs the adjacency list representation of the graph in O(n+m), where each node and its edges are added once.
The main part of the algorithm is Dijkstra’s shortest path computation, which processes each node at most once and each edge at most once. The priority queue operations (insertion and deletion) take O(logn) time per operation. Since each node is added to the queue at most once and each edge results in at most one update operation, the total complexity of Dijkstra’s algorithm is O((n+m)logn).
 */