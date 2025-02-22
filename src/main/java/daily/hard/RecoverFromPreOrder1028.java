package daily.hard;

import common.TreeNode;

public class RecoverFromPreOrder1028 {
    public static void main(String[] args) {
        String traversal = "1-2--3--4-5--6--7";
        System.out.println(recoverFromPreorder(traversal).val);
    }

//    recursion; time: O(n), space: O(n)
    public static TreeNode recoverFromPreorder(String traversal) {
        return helper(traversal, 0, 0);
    }

    private static TreeNode helper(String traversal, int depth, int index) {
        if(index >= traversal.length()) return null;
//        count the number of dashes
        int dashCount = 0;
        while((index + dashCount) < traversal.length() && traversal.charAt(index + dashCount) == '-')
            dashCount++;
//        if the number of dashes doesn't match the depth, return null
        if(dashCount != depth) return null;
//        move the index past the dashes
        index += dashCount;
//        extract the node value
        int value = 0;
        while(index < traversal.length() && Character.isDigit(traversal.charAt(index)))
            value = (10 * value) + (traversal.charAt(index++) - '0');
//        create the current node
        TreeNode node = new TreeNode(value);
//        recursively build the left and the right subtree
        node.left = helper(traversal, depth + 1, index);
        node.right = helper(traversal, depth + 1, index);

        return node;
    }
}


/*
We run a preorder depth-first search (DFS) on the root of a binary tree.
At each node in this traversal, we output D dashes (where D is the depth of this node), then we output the value of this node.  If the depth of a node is D, the depth of its immediate child is D + 1.  The depth of the root node is 0.
If a node has only one child, that child is guaranteed to be the left child.
Given the output traversal of this traversal, recover the tree and return its root.
Example 1:
Input: traversal = "1-2--3--4-5--6--7"
Output: [1,2,5,3,4,6,7]
Example 2:
Input: traversal = "1-2--3---4-5--6---7"
Output: [1,2,5,3,null,6,null,4,null,7]
Example 3:
Input: traversal = "1-401--349---90--88"
Output: [1,401,null,349,88,90]

Constraints:
The number of nodes in the original tree is in the range [1, 1000].
1 <= Node.val <= 109
 */