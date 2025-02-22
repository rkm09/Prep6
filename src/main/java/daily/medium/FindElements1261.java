package daily.medium;

import common.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class FindElements1261 {
}


// dfs; time: O(n), space: O(n)
// note that unlike def; since we are not changing values of the tree nodes, only passing around value
// and updating set, at line 29, 30 it would be 2 * current value and not wrt node.val.
class FindElements {
    private Set<Integer> seen;
    public FindElements(TreeNode root) {
        seen = new HashSet<>();
        dfs(root, 0);
    }

    public boolean find(int target) {
        return seen.contains(target);
    }

    private void dfs(TreeNode node, int currentVal) {
        if(node == null) return;
        seen.add(currentVal);
        dfs(node.left, 2 * currentVal + 1);
        dfs(node.right, 2 * currentVal + 2);
    }
}

// def; dfs; time: O(n), space: O(n) [dfs faster than bfs]
class FindElements1 {
    private final Set<Integer> seen;
    public FindElements1(TreeNode root) {
        root.val = 0;
        seen = new HashSet<>();
        dfs(root);
    }

    public boolean find(int target) {
        return seen.contains(target);
    }

    private void dfs(TreeNode node) {
        seen.add(node.val);
        if(node.left != null) {
            node.left.val = 2 * node.val + 1;
            dfs(node.left);
        }
        if(node.right != null) {
            node.right.val = 2 * node.val + 2;
            dfs(node.right);
        }
    }
}

// bfs; time: O(n), space: O(n)
class FindElements2 {

    private final Set<Integer> seen;

    public FindElements2(TreeNode root) {
        seen = new HashSet<>();
        root.val = 0;
        bfs(root);
    }

    public boolean find(int target) {
        return seen.contains(target);
    }

    private void bfs(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        root.val = 0;
        queue.offer(root);
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            seen.add(node.val);
            if(node.left != null) {
                node.left.val = 2 * node.val + 1;
                queue.offer(node.left);
            }
            if(node.right != null) {
                node.right.val = 2 * node.val + 2;
                queue.offer(node.right);
            }
        }
    }
}

/**
 * Your FindElements object will be instantiated and called as such:
 * FindElements obj = new FindElements(root);
 * boolean param_1 = obj.find(target);
 */

/*
Given a binary tree with the following rules:root.val == 0
For any treeNode:
If treeNode.val has a value x and treeNode.left != null, then treeNode.left.val == 2 * x + 1
If treeNode.val has a value x and treeNode.right != null, then treeNode.right.val == 2 * x + 2
Now the binary tree is contaminated, which means all treeNode.val have been changed to -1.
Implement the FindElements class:
FindElements(TreeNode* root) Initializes the object with a contaminated binary tree and recovers it.
bool find(int target) Returns true if the target value exists in the recovered binary tree.
Example 1:
Input
["FindElements","find","find"]
[[[-1,null,-1]],[1],[2]]
Output
[null,false,true]
Explanation
FindElements findElements = new FindElements([-1,null,-1]);
findElements.find(1); // return False
findElements.find(2); // return True
Example 2:
Input
["FindElements","find","find","find"]
[[[-1,-1,-1,-1,-1]],[1],[3],[5]]
Output
[null,true,true,false]
Explanation
FindElements findElements = new FindElements([-1,-1,-1,-1,-1]);
findElements.find(1); // return True
findElements.find(3); // return True
findElements.find(5); // return False
Example 3:
Input
["FindElements","find","find","find","find"]
[[[-1,null,-1,-1,null,-1]],[2],[3],[4],[5]]
Output
[null,true,false,false,true]
Explanation
FindElements findElements = new FindElements([-1,null,-1,-1,null,-1]);
findElements.find(2); // return True
findElements.find(3); // return False
findElements.find(4); // return False
findElements.find(5); // return True

Constraints:
TreeNode.val == -1
The height of the binary tree is less than or equal to 20
The total number of nodes is between [1, 104]
Total calls of find() is between [1, 104]
0 <= target <= 106
 */