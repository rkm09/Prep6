package daily.medium;

public class CountComponents363 {
    public static void main(String[] args) {
        CountComponents363 c = new CountComponents363();
        int[][] edges = {{0,1},{1,2},{3,4}};
        System.out.println(c.countComponents(5, edges));
    }

//    dsu; time: O(V + E.alpha(n)), space: O(V)
    public int countComponents(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        for(int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }
        return uf.getCount();
    }

    static class UnionFind {
        private final int[] parent;
        private final int[] rank;
        private int count;
        UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            count = size;
            for(int i = 0 ; i < size ; i++)
                parent[i] = i;
        }
        public int find(int i) {
            if(parent[i] != i)
                parent[i] = find(parent[i]);
            return parent[i];
        }
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if(rootX != rootY) {
                if(rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if(rank[rootX] < rank[rootY])
                    parent[rootY] = rootX;
                else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
                count--;
            }
        }

        public int getCount() {
            return count;
        }
    }
}

/*
You have a graph of n nodes. You are given an integer n and an array edges where edges[i] = [ai, bi] indicates that there is an edge between ai and bi in the graph.
Return the number of connected components in the graph.
Example 1:
Input: n = 5, edges = [[0,1],[1,2],[3,4]]
Output: 2
Example 2:
Input: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]
Output: 1
Constraints:
1 <= n <= 2000
1 <= edges.length <= 5000
edges[i].length == 2
0 <= ai <= bi < n
ai != bi
There are no repeated edges.
 */