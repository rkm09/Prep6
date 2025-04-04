package daily.medium;

import common.Pair;
import common.TreeNode;

public class LowestCommonAncestor1123 {
    public static void main(String[] args) {
        LowestCommonAncestor1123 l = new LowestCommonAncestor1123();
    }

//    dfs; time: O(n), space: O(n)
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        return dfs(root).getKey();
    }

    private Pair<TreeNode, Integer> dfs(TreeNode node) {
        if(node == null)
            return new Pair<>(null, 0);
        Pair<TreeNode, Integer> left = dfs(node.left);
        Pair<TreeNode, Integer> right = dfs(node.right);
        if(left.getValue() > right.getValue())
            return new Pair<>(left.getKey(), left.getValue() + 1);
        else if(left.getValue() < right.getValue())
            return new Pair<>(right.getKey(), right.getValue() + 1);
        return new Pair<>(node, left.getValue() + 1);
    }
}
/*
Given the root of a binary tree, return the lowest common ancestor of its deepest leaves.
Recall that:
The node of a binary tree is a leaf if and only if it has no children
The depth of the root of the tree is 0. if the depth of a node is d, the depth of each of its children is d + 1.
The lowest common ancestor of a set S of nodes, is the node A with the largest depth such that every node in S is in the subtree with root A.
Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4]
Output: [2,7,4]
Explanation: We return the node with value 2, colored in yellow in the diagram.
The nodes coloured in blue are the deepest leaf-nodes of the tree.
Note that nodes 6, 0, and 8 are also leaf nodes, but the depth of them is 2, but the depth of nodes 7 and 4 is 3.
Example 2:
Input: root = [1]
Output: [1]
Explanation: The root is the deepest node in the tree, and it's the lca of itself.
Example 3:
Input: root = [0,1,3,null,2]
Output: [2]
Explanation: The deepest leaf node in the tree is 2, the lca of one node is itself.
Constraints:
The number of nodes in the tree will be in the range [1, 1000].
0 <= Node.val <= 1000
The values of the nodes in the tree are unique.

Note: This question is the same as 865: https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/
 */

/*
We use a recursive method to perform a depth-first search, recursively traversing each node in the tree and returning the maximum depth d of the current subtree and the lca node. If the current node is null, we return depth 0 and an null node. In each search, we recursively search the left and right subtrees, and then compare the depths of the left and right subtrees:
If the left subtree is deeper, the deepest leaf node is in the left subtree, we return {left subtree depth + 1, the lca node of the left subtree}
If the right subtree is deeper, the deepest leaf node is in the right subtree, we return {right subtree depth + 1, the lca node of the right subtree}
If both left and right subtrees have the same depth and both have the deepest leaf nodes, we return {left subtree depth + 1, current node}.
Finally, we return the root node's lca node.
 */