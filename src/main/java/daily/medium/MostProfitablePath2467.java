package daily.medium;

import java.util.*;

public class MostProfitablePath2467 {
    private Map<Integer, Integer> bobPath;
    private boolean[] visited;
    private Map<Integer, List<Integer>> adjMap;
    public static void main(String[] args) {
        MostProfitablePath2467 m = new MostProfitablePath2467();
        int[][] edges = {{0,1},{1,2},{1,3},{3,4}};
        int[] amount = {-2,2,2,-4,6};
        System.out.println(m.mostProfitablePath(edges, 3, amount));
    }

//    dfs + bfs; time: O(n), space: O(n)
    public int mostProfitablePath(int[][] edges, int bob, int[] amount) {
        adjMap = new HashMap<>();
//        note n is amount.length, not edge.length (which could be less than the total number of vertices)
        int n = amount.length, maxIncome = Integer.MIN_VALUE;
        visited = new boolean[n];
        bobPath = new HashMap<>();

        for(int[] edge : edges) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            adjMap.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
        }

//        find the path and the time taken by Bob to reach node 0
        findBobPath(bob, 0);
//        need to do this as, findBobPath made changes to visited
        Arrays.fill(visited, false);
//        bfs for Alice
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0,0,0});
        while(!queue.isEmpty()) {
            int[] node = queue.poll();
            int sourceNode = node[0], time = node[1], income = node[2];
//            Alice reaches the node first
            if(!bobPath.containsKey(sourceNode) || time < bobPath.get(sourceNode)) {
                income += amount[sourceNode];
            }
//            Alice and Bob reach the node at the same time
            else if(time == bobPath.get(sourceNode)) {
                income += amount[sourceNode] / 2;
            }
//            update the maximum value if the current node is a new leaf
            if(adjMap.containsKey(sourceNode)  && adjMap.get(sourceNode).size() == 1 && sourceNode != 0)
                maxIncome = Math.max(maxIncome, income);
//            explore adjacent unvisited vertices
            for(int adjacentNode : adjMap.getOrDefault(sourceNode, new ArrayList<>())) {
                if(!visited[adjacentNode]) {
                    queue.offer(new int[] {adjacentNode, time + 1, income});
                }
            }
//            mark as visited
            visited[sourceNode] = true;
        }

        return maxIncome;
    }

//    depth first search
    private boolean findBobPath(int sourceNode,  int time) {
//        mark and set the time node is reached
        bobPath.put(sourceNode, time);
        visited[sourceNode] = true;

//        destination for Bob is found
        if(sourceNode == 0)
            return true;

//        traverse through adjacent nodes
        for(int adjacentNode : adjMap.getOrDefault(sourceNode, new ArrayList<>())) {
            if(!visited[adjacentNode]) {
                if(findBobPath(adjacentNode, time + 1))
                    return true;
            }
        }
//        if node 0 ain't reached, remove the current node from the path
        bobPath.remove(sourceNode);
        return false;
    }
}

/*
There is an undirected tree with n nodes labeled from 0 to n - 1, rooted at node 0. You are given a 2D integer array edges of length n - 1 where edges[i] = [ai, bi] indicates that there is an edge between nodes ai and bi in the tree.
At every node i, there is a gate. You are also given an array of even integers amount, where amount[i] represents:
the price needed to open the gate at node i, if amount[i] is negative, or,
the cash reward obtained on opening the gate at node i, otherwise.
The game goes on as follows:
Initially, Alice is at node 0 and Bob is at node bob.
At every second, Alice and Bob each move to an adjacent node. Alice moves towards some leaf node, while Bob moves towards node 0.
For every node along their path, Alice and Bob either spend money to open the gate at that node, or accept the reward. Note that:
If the gate is already open, no price will be required, nor will there be any cash reward.
If Alice and Bob reach the node simultaneously, they share the price/reward for opening the gate there. In other words, if the price to open the gate is c, then both Alice and Bob pay c / 2 each. Similarly, if the reward at the gate is c, both of them receive c / 2 each.
If Alice reaches a leaf node, she stops moving. Similarly, if Bob reaches node 0, he stops moving. Note that these events are independent of each other.
Return the maximum net income Alice can have if she travels towards the optimal leaf node.
Example 1:
Input: edges = [[0,1],[1,2],[1,3],[3,4]], bob = 3, amount = [-2,4,2,-4,6]
Output: 6
Explanation:
The above diagram represents the given tree. The game goes as follows:
- Alice is initially on node 0, Bob on node 3. They open the gates of their respective nodes.
  Alice's net income is now -2.
- Both Alice and Bob move to node 1.
  Since they reach here simultaneously, they open the gate together and share the reward.
  Alice's net income becomes -2 + (4 / 2) = 0.
- Alice moves on to node 3. Since Bob already opened its gate, Alice's income remains unchanged.
  Bob moves on to node 0, and stops moving.
- Alice moves on to node 4 and opens the gate there. Her net income becomes 0 + 6 = 6.
Now, neither Alice nor Bob can make any further moves, and the game ends.
It is not possible for Alice to get a higher net income.
Example 2:
Input: edges = [[0,1]], bob = 1, amount = [-7280,2350]
Output: -7280
Explanation:
Alice follows the path 0->1 whereas Bob follows the path 1->0.
Thus, Alice opens the gate at node 0 only. Hence, her net income is -7280.

Constraints:
2 <= n <= 105
edges.length == n - 1
edges[i].length == 2
0 <= ai, bi < n
ai != bi
edges represents a valid tree.
1 <= bob < n
amount.length == n
amount[i] is an even integer in the range [-104, 104].
 */