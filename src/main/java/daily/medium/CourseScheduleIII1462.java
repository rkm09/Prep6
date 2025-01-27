package daily.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseScheduleIII1462 {
    public static void main(String[] args) {
        CourseScheduleIII1462 c = new CourseScheduleIII1462();
        int[][] prerequisites = {{0,1}};
        int[][] queries = {{0,1},{1,0}};
        System.out.println(c.checkIfPrerequisite(2, prerequisites, queries));
    }

//    dfs; time: O(Q.N^2); space: O(N^2)
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        List<Boolean> result = new ArrayList<>();
        Map<Integer, List<Integer>> adjMap = new HashMap<>();
        for(int[] edge : prerequisites) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>())
                    .add(edge[1]);
        }
        for(int i = 0 ; i < queries.length ; i++) {
//            reset the visited array for each dfs call
            boolean[] visited = new boolean[numCourses];
            result.add(isPrerequisite(adjMap, visited, queries[i][0], queries[i][1]));
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
 */