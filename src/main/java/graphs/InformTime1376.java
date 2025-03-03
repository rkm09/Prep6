package graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class InformTime1376 {
    private int maxTime = Integer.MIN_VALUE;
    public static void main(String[] args) {
        InformTime1376 i = new InformTime1376();
        int[] manager = {2,2,-1,2,2,2}, informTime = {0,0,1,0,0,0};
        System.out.println(i.numOfMinutes(6, 2, manager, informTime));
    }

//    dfs; time: O(n), space: O(n)
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        List<List<Integer>> adjList = new ArrayList<>();
        for(int i = 0 ; i < n ; i++)
            adjList.add(new ArrayList<>());
//        make an adjacency list, each index stores the ids of subordinate employees
        for(int i = 0 ; i < n ; i++) {
            if(manager[i] != -1)
                adjList.get(manager[i]).add(i);
        }
        dfs(adjList, informTime, headID, 0);
        return maxTime;
    }

    private void dfs(List<List<Integer>> adjList, int[] informTime, int curr, int time) {
//        maximum time for an employee to get the news
        maxTime = Math.max(maxTime, time);
//        visit the subordinate employees who get the news after informTime[curr] unit time
        for(int adjacent : adjList.get(curr))
            dfs(adjList, informTime, adjacent, time + informTime[curr]);
    }

//    bfs; time: O(n), space: O(n)
    public int numOfMinutes1(int n, int headID, int[] manager, int[] informTime) {
        List<List<Integer>> adjList = new ArrayList<>();
        for(int i = 0 ; i < n ; i++)
            adjList.add(new ArrayList<>());
        for(int i = 0 ; i < n ; i++)
            if(manager[i] != -1)
                adjList.get(manager[i]).add(i);
//        note: could do it with pair also, instead of int[]
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] {headID, 0});
        while(!queue.isEmpty()) {
            int[] info = queue.poll();
            int curr = info[0];
            int time = info[1];
            maxTime = Math.max(maxTime, time);
            for(int adjacent : adjList.get(curr))
                queue.offer(new int[] {adjacent, time + informTime[curr]});
        }
        return maxTime;
    }
}

/*
A company has n employees with a unique ID for each employee from 0 to n - 1. The head of the company is the one with headID.
Each employee has one direct manager given in the manager array where manager[i] is the direct manager of the i-th employee, manager[headID] = -1. Also, it is guaranteed that the subordination relationships have a tree structure.
The head of the company wants to inform all the company employees of an urgent piece of news. He will inform his direct subordinates, and they will inform their subordinates, and so on until all employees know about the urgent news.
The i-th employee needs informTime[i] minutes to inform all of his direct subordinates (i.e., After informTime[i] minutes, all his direct subordinates can start spreading the news).
Return the number of minutes needed to inform all the employees about the urgent news.
Example 1:
Input: n = 1, headID = 0, manager = [-1], informTime = [0]
Output: 0
Explanation: The head of the company is the only employee in the company.
Example 2:
Input: n = 6, headID = 2, manager = [2,2,-1,2,2,2], informTime = [0,0,1,0,0,0]
Output: 1
Explanation: The head of the company with id = 2 is the direct manager of all the employees in the company and needs 1 minute to inform them all.
The tree structure of the employees in the company is shown.

Constraints:
1 <= n <= 105
0 <= headID < n
manager.length == n
0 <= manager[i] < n
manager[headID] == -1
informTime.length == n
0 <= informTime[i] <= 1000
informTime[i] == 0 if employee i has no subordinates.
It is guaranteed that all the employees can be informed.
 */

/*
We start the DFS from the root node, i.e. headID with the time as 0, then we iterate over its subordinates with time as time + informTime[parent].
Before we iterate over the subordinates, we update the variable maxTime, which is the maximum time we have seen so far.
In the end, we can just return the maxTime, which is the time for the employee that got the information at last.
Note that we don't have to keep track of the nodes we have traversed, as the structure we are traversing over is a directed tree without any cycles. Hence, we will never visit a node twice.
 */