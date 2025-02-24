package daily.medium;

import common.TreeNode;

public class ConstructFromPrePost859 {
    private int preIndex = 0, postIndex = 0;
    public static void main(String[] args) {
        ConstructFromPrePost859 c = new ConstructFromPrePost859();
        int[] preorder = {1,2,4,5,3,6,7};
        int[] postorder = {4,5,2,6,7,3,1};
        System.out.println(c.constructFromPrePost(preorder, postorder).val);
    }

//    optimised recursion; time: O(n), space: O(n)
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        return constructTree(preorder, postorder);
    }

//    helper function to recursively build the tree
    private TreeNode constructTree(int[] preorder, int[] postorder) {
//        create a new node with a value at the current preorder index
        TreeNode root = new TreeNode(preorder[preIndex++]);
//        recursively create the left subtree if the root is not the last of its subtree
        if(root.val != postorder[postIndex]) {
            root.left = constructTree(preorder, postorder);
        }
//        recursively construct the right subtree if the root is still not the last of its subtree
        if(root.val != postorder[postIndex]) {
            root.right = constructTree(preorder, postorder);
        }
//        mark this node and its subtree as fully processed
        postIndex++;
        return root;
    }
}

/*
Given two integer arrays, preorder and postorder where preorder is the preorder traversal of a binary tree of distinct values and postorder is the postorder traversal of the same tree, reconstruct and return the binary tree.
If there exist multiple answers, you can return any of them.
Example 1:
Input: preorder = [1,2,4,5,3,6,7], postorder = [4,5,2,6,7,3,1]
Output: [1,2,3,4,5,6,7]
Example 2:
Input: preorder = [1], postorder = [1]
Output: [1]

Constraints:
1 <= preorder.length <= 30
1 <= preorder[i] <= preorder.length
All the values of preorder are unique.
postorder.length == preorder.length
1 <= postorder[i] <= postorder.length
All the values of postorder are unique.
It is guaranteed that preorder and postorder are the preorder traversal and postorder traversal of the same binary tree.
 */

/*
Optimised Recursion:
In the previous approaches, we explicitly searched for the dividing point between the left and right subtrees using postorder, which introduced an additional lookup step. Here we remove that extra search by dynamically determining subtree boundaries as we traverse the arrays, making the recursion more efficient.
The core idea is to process nodes in preorder to determine which nodes to create and use postorder to recognize when a subtree is complete. Since preorder always visits nodes in the order Root → Left → Right, each recursive call picks the next node from preorder and assigns it as the root of the current subtree. Meanwhile, since postorder follows Left → Right → Root, a subtree is fully processed when we encounter its root in postorder.
To track this, we maintain an index posIndex that moves forward as nodes get finalized.
To construct the tree, we first check if the current root’s value matches postorder[posIndex]. If it does, the subtree ends at this node, meaning it has no children. Otherwise, we attempt to construct the left subtree by making a recursive call. If the next value still doesn’t match postorder[posIndex], it means there must also be a right subtree, so we make another recursive call to construct it.
Once both subtrees are built, we move posIndex forward to mark this node and its subtree as fully processed.
 */