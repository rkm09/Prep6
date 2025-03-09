package graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CriticalConnection1192 {
    public static void main(String[] args) {
        CriticalConnection1192 c = new CriticalConnection1192();
//        int[][] arr = {{0,1},{1,2},{2,0},{1,3}};
        int[][] arr = {{0,1},{1,2},{2,0},{1,3},{3,4},{4,5},{5,3}};
        List<List<Integer>> connections = new ArrayList<>();
        for(int[] a : arr) {
            List<Integer> li = List.of(a[0],a[1]);
            connections.add(li);
        }
        List<List<Integer>> res = c.criticalConnections(6, connections);
        System.out.println(res);
    }


//    note: this doesn't work currently
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> resList = new ArrayList<>();
        List<List<Integer>> adjList = new ArrayList<>();
        for(int i = 0 ; i < n ; i++)
            adjList.add(new ArrayList<>());
        for(List<Integer> edge : connections) {
            int node1 = edge.get(0);
            int node2 = edge.get(1);
            adjList.get(node1).add(node2);
            adjList.get(node2).add(node1);
        }
        UnionFind uf = new UnionFind(n);
        Set<Integer> nonCritical = new HashSet<>();
        for(List<Integer> edge : connections) {
            int node1 = edge.get(0);
            int node2 = edge.get(1);
            if(uf.union(node1, node2)) {
                nonCritical.add(node1);
                nonCritical.add(node2);
            }
        }
        System.out.println(nonCritical);
        for(List<Integer> edge : connections) {
            if(nonCritical.contains(edge.get(0)) || nonCritical.contains(edge.get(1)))
                continue;
            resList.add(edge);
        }
        return resList;
    }

    class UnionFind {
        private int[] parent;
        private int[] rank;
        private int count;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            count = n;
            for(int i = 0 ; i < n ; i++)
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
            if(rootX == rootY) return true;
            if(rank[rootX] > rank[rootY])
                parent[rootY] = rootX;
            else if(rank[rootX] < rank[rootY])
                parent[rootX] = rootY;
            else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            count--;
            return false;
        }
        public int getCount() {
            return count;
        }
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