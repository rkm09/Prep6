package daily.medium;

import common.ListNode;

import java.util.*;

public class MostRecentlyUsed1756 {
}

// using arraylist
class MRUQueue {

    List<Integer> indexList;
    public MRUQueue(int n) {
        indexList = new ArrayList<>();
        for(int i = 1 ; i <= n ; i++)
            indexList.add(i);
    }

//    time: O(n) [removal followed by shifting all would take n]
    public int fetch(int k) {
//        get the kth element(1 indexed)
        int val = indexList.get(k-1);
//        remove the element at the index
        indexList.remove(k - 1);
//        append to the end
        indexList.add(val);
        return val;
    }
}

// linked list; time: O(n), space: O(n)
class MRUQueue1 {

    private final ListNode head;
    private ListNode tail;
    public MRUQueue1(int n) {
        head = new ListNode(0, null);
        ListNode current = head;
//        initialize linked list with values from 1 to n
        for(int number = 1; number <= n ; number++) {
            current.next = new ListNode(number);
            current = current.next;
        }
        tail = current;
    }

    public int fetch(int k) {
        ListNode current = head;
//        traverse to the node before the kth node
        for(int i = 1 ; i < k ; i++)
            current = current.next;
//        get the value of the kth node
        int value = current.next.val;
//        move the kth node to the end of the list
        tail.next = current.next;
        tail = tail.next;
        current.next = tail.next;
        tail.next = null;
        return value;
    }

}


/**
 * Your MRUQueue object will be instantiated and called as such:
 * MRUQueue obj = new MRUQueue(n);
 * int param_1 = obj.fetch(k);
 */

/*
Design a queue-like data structure that moves the most recently used element to the end of the queue.
Implement the MRUQueue class:
MRUQueue(int n) constructs the MRUQueue with n elements: [1,2,3,...,n].
int fetch(int k) moves the kth element (1-indexed) to the end of the queue and returns it.
Example 1:
Input:
["MRUQueue", "fetch", "fetch", "fetch", "fetch"]
[[8], [3], [5], [2], [8]]
Output:
[null, 3, 6, 2, 2]
Explanation:
MRUQueue mRUQueue = new MRUQueue(8); // Initializes the queue to [1,2,3,4,5,6,7,8].
mRUQueue.fetch(3); // Moves the 3rd element (3) to the end of the queue to become [1,2,4,5,6,7,8,3] and returns it.
mRUQueue.fetch(5); // Moves the 5th element (6) to the end of the queue to become [1,2,4,5,7,8,3,6] and returns it.
mRUQueue.fetch(2); // Moves the 2nd element (2) to the end of the queue to become [1,4,5,7,8,3,6,2] and returns it.
mRUQueue.fetch(8); // The 8th element (2) is already at the end of the queue so just return it.

Constraints:
1 <= n <= 2000
1 <= k <= n
At most 2000 calls will be made to fetch.

Follow up: Finding an O(n) algorithm per fetch is a bit easy. Can you find an algorithm with a better complexity for each fetch call?
 */

/*
Linked list:
To perform a fetch operation, we traverse the linked list to find thek-th node. Since linked lists do not provide direct access by index, this takesO(n)time in the worst case. Once we locate thek-th node, we update pointers to remove it from its current position and append it to the end, making sure to maintain the list's integrity. More specifically, to fetch, we go to the(k - 1)-th (previous) node, link node k to the tail, update the tail, and return the value.
This avoids shifting elements like in an array, but traversal itself remains anO(n)operation. While better suited for frequent modifications, it still suffers from inefficiency in searching for elements.
 */