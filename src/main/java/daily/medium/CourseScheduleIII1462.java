package daily.medium;

import java.util.*;

public class CourseScheduleIII1462 {
    public static void main(String[] args) {
        CourseScheduleIII1462 c = new CourseScheduleIII1462();
        int[][] prerequisites = {{0,1}};
        int[][] queries = {{0,1},{1,0}};
        System.out.println(c.checkIfPrerequisite1(2, prerequisites, queries));
    }

//    dfs; time: O(Q.N^2); space: O(N^2)
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        List<Boolean> result = new ArrayList<>();
        Map<Integer, List<Integer>> adjMap = new HashMap<>();
        for(int[] edge : prerequisites) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>())
                    .add(edge[1]);
        }
        for(int[] query : queries) {
//            reset the visited array for each dfs call, as every query is independent of previous outcome
            boolean[] visited = new boolean[numCourses];
            result.add(isPrerequisite(adjMap, visited, query[0], query[1]));
        }
        return result;
    }

//    perform dfs and return true if there is a path between src and target, and otherwise false
    private boolean isPrerequisite(Map<Integer, List<Integer>> adjMap, boolean[] visited, int src, int target) {
        visited[src] = true;
        if(src == target) return true;
        boolean answer = false;
        List<Integer> neighbours = adjMap.getOrDefault(src, new ArrayList<>());
        for(int neighbour : neighbours) {
            if(!visited[neighbour]) {
                answer = answer || isPrerequisite(adjMap, visited, neighbour, target);
            }
        }
        return answer;
    }

//    topological sort; time: O(N^3 + Q), space: O(N^2)  [faster]
    public List<Boolean> checkIfPrerequisite1(int numCourses, int[][] prerequisites, int[][] queries) {
        Map<Integer, List<Integer>> adjMap = new HashMap<>();
        int[] inDegree = new int[numCourses];
        for(int[] edge : prerequisites) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>())
                    .add(edge[1]);
            inDegree[edge[1]]++;
        }
        Deque<Integer> queue = new ArrayDeque<>();
        for(int i = 0 ; i < numCourses ; i++) {
            if(inDegree[i] == 0)
                queue.offer(i);
        }
        List<Boolean> result = new ArrayList<>();
        Map<Integer, Set<Integer>> nodePrerequisites = new HashMap<>();
        while(!queue.isEmpty()) {
            int node = queue.poll();
            for(int adj : adjMap.getOrDefault(node, new ArrayList<>())) {
//                note: we need to store/save new hashset as default if not present, and hence we use computeIfAbsent instead of getOrDefault
                nodePrerequisites.computeIfAbsent(adj, k -> new HashSet<>())
                        .add(node);
//                add all transitive dependencies as well
                for(int pre : nodePrerequisites.getOrDefault(node, new HashSet<>())) {
                    nodePrerequisites.get(adj).add(pre);
                }
                inDegree[adj]--;
                if(inDegree[adj] == 0)
                    queue.offer(adj);
            }
        }
        for(int[] query : queries) {
            result.add(nodePrerequisites.getOrDefault(query[1], new HashSet<>())
                    .contains(query[0]));
        }
        return result;
    }

//    floyd warshall; time: O(N^3 + Q), space: O(N^2) [fastest]
    public List<Boolean> checkIfPrerequisite2(int numCourses, int[][] prerequisites, int[][] queries) {
        boolean[][] isPrerequisite = new boolean[numCourses][numCourses];
        for(int[] edge : prerequisites) {
            isPrerequisite[edge[0]][edge[1]] = true;
        }
        for(int intermediate = 0 ; intermediate < numCourses ; intermediate++) {
            for(int src = 0 ; src < numCourses ; src++) {
                for(int target = 0 ; target < numCourses ; target++) {
//                    if there is a path i -> intermediate and intermediate -> j, then i -> j exists as well
                    isPrerequisite[src][target] = isPrerequisite[src][target]
                            || isPrerequisite[src][intermediate] && isPrerequisite[intermediate][target];
                }
            }
        }
        List<Boolean> result = new ArrayList<>();
        for(int[] query : queries) {
            result.add(isPrerequisite[query[0]][query[1]]);
        }
        return result;
    }
}


/*
There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course ai first if you want to take course bi.
For example, the pair [0, 1] indicates that you have to take course 0 before you can take course 1.
Prerequisites can also be indirect. If course a is a prerequisite of course b, and course b is a prerequisite of course c, then course a is a prerequisite of course c.
You are also given an array queries where queries[j] = [uj, vj]. For the jth query, you should answer whether course uj is a prerequisite of course vj or not.
Return a boolean array answer, where answer[j] is the answer to the jth query.
Example 1:
Input: numCourses = 2, prerequisites = [[1,0]], queries = [[0,1],[1,0]]
Output: [false,true]
Explanation: The pair [1, 0] indicates that you have to take course 1 before you can take course 0.
Course 0 is not a prerequisite of course 1, but the opposite is true.
Example 2:
Input: numCourses = 2, prerequisites = [], queries = [[1,0],[0,1]]
Output: [false,false]
Explanation: There are no prerequisites, and each course is independent.
Example 3:
Input: numCourses = 3, prerequisites = [[1,2],[1,0],[2,0]], queries = [[1,0],[1,2]]
Output: [true,true]

Constraints:
2 <= numCourses <= 100
0 <= prerequisites.length <= (numCourses * (numCourses - 1) / 2)
prerequisites[i].length == 2
0 <= ai, bi <= numCourses - 1
ai != bi
All the pairs [ai, bi] are unique.
The prerequisites graph has no cycles.
1 <= queries.length <= 104
0 <= ui, vi <= numCourses - 1
ui != vi
 */

/*
approach1: dfs
We can simplify the problem by recognizing that the answer to the query (u, v) is true if there exists a path from node u to node v. This is because the edges are directed to represent dependencies, so if we can reach node v from node u, it indicates that node u is a prerequisite for node v.
This relationship is an example of transitive closure. For instance, consider a path with three nodes: u -> v -> w. In this case:
Node u is a prerequisite for node v.
Node v is a prerequisite for node w. By transitivity, we can conclude that node u is also a prerequisite for node w.
Therefore, the problem reduces to determining whether there exists a path between two nodes. To solve this, we can use Depth-First Search (DFS) to explore the graph. Alternatively, other traversal methods like Breadth-First Search (BFS) can also be used. In this approach, we begin at node u and explore its adjacent nodes recursively until we reach node v. If we find node v during the traversal, we return true. If we exhaust all possible paths without reaching node v, we return false.
To efficiently track visited nodes and prevent revisiting them, we maintain a visited array. This array is reset for each query to ensure that each DFS traversal starts with a clean slate, avoiding interference from previous queries.
Complexity Analysis:
Let N be the number of courses (numCourses) and let Q be the size of the queries list. In the worst case, the size of the prerequisites list can grow up to
N⋅(N−1)/2, when every course is a prerequisite for every other course, forming a complete directed graph.
Time complexity: O(Q⋅N^2).
Creating the adjacency list adjList takes O(N^ 2) time as we need to iterate over the list prerequisites.
Then we iterate over queries and for each we perform DFS that can take O(V+E) which is equivalent to O(N^2).
Hence, the total time complexity equals O(Q⋅N^2).
Space complexity: O(N^2)
The adjacency list requires O(N^2) as it stores every edge in the list prerequisites. For the DFS traversal, we need a visited array of size O(N) and the recursive stack for DFS calls requires O(N) space in the worsts case. Therefore, the total space complexity is equal to O(N^2).

Topological Sort:
We start by calculating the inDegree of each node, which tells us how many nodes it depends on.
Nodes with an inDegree of zero are independent and can be processed first, so we enqueue them. Then, using a queue, we dequeue nodes, process their neighbors, update the prerequisite lists,
and enqueue any neighbors whose inDegree drops to zero. This continues until we’ve processed all nodes, ensuring the correct order of traversal.
Complexity Analysis:
Let N be the number of courses (numCourses) and let Q be the size of the queries list. In the worst case, the size of the prerequisites list can grow up to
N⋅(N−1)/2, when every course is a prerequisite for every other course, forming a complete directed graph.
Time complexity: O(N^3+Q).
Creating the adjacency list adjList takes O(N^2) time as we need to iterate over the list prerequisites. The array inDegree
will be of size O(N). In Kahn's algorithm, we iterate over each node and edge of the vertex which is O(N^2) and for each edge traversed we will also add the prerequisites to the next node which is another O(N).
To answer each query we need constant time to retrieve from the map and hence it's O(Q) to answer all queries.
Hence, the total time complexity equals O(N^3+Q).
Space complexity: O(N^2)
List adjList takes O(N^2) as it will store every edge in the list prerequisites. Array inDegree will take O(N) space and
the queue for Kahn's algorithm will also be O(N) size. Map nodePrerequisites will be from the node to its prerequisites and thus the total number of entries can be equal to O(N^2). Hence, the total space complexity equals O(N^2).

Floyd Warshall:
In the first approach, we discussed the concept of transitive closure, which simplified the problem. The key insight was that the transitive closure allows us to determine if a path exists between two nodes, even indirectly. This concept is central to solving the All-Pairs Shortest Path (APSP) problem, for which the Floyd-Warshall algorithm is commonly used. This algorithm works by systematically considering every possible intermediate node and checking if a path between two nodes can be improved by going through that intermediate node. It then updates the shortest distance between the nodes.
For our problem, however, we don't need to calculate the shortest path, just whether a path exists. This leads us to a simple modification of the Floyd-Warshall algorithm: instead of keeping track of distances, we’ll use boolean values to represent whether a path exists between two nodes.
The main idea is to check if there’s a path from src to target by looking at all possible intermediate nodes. For each intermediate node, we check if there’s a path from src to that node and a path from that node to target. If both conditions hold, then we can confirm that a path exists between src and target. We then set isPrerequisite[src][target] to true.
At the end of this process, we’ll have a 2D array, isPrerequisite, where each entry isPrerequisite[u][v] tells us whether u is a prerequisite for v.
 */