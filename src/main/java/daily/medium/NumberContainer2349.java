package daily.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class NumberContainer2349 {
    public static void main(String[] args) {

    }
}

// two hashmap + tree set; time: O(logn), space: O(n)
class NumberContainers {
    Map<Integer, Integer> indexToValue;
    Map<Integer, TreeSet<Integer>> valueToIndices;

    public NumberContainers() {
        indexToValue = new HashMap<>();
        valueToIndices = new HashMap<>();
    }

    public void change(int index, int number) {
        int val = indexToValue.getOrDefault(index, -1);
        indexToValue.put(index, number);
        if(valueToIndices.containsKey(val)) {
            valueToIndices.get(val).remove(index);
            if(valueToIndices.get(val).size() == 0) valueToIndices.remove(val);
        }
        valueToIndices.computeIfAbsent(number, k->new TreeSet<>()).add(index);
    }

    public int find(int number) {
        return valueToIndices.containsKey(number) ? valueToIndices.get(number).first() : -1;
    }
}

// two hashmaps + priority queue; time: O(logn), space: O(n)
class NumberContainers1 {
    Map<Integer, Integer> indexToValue;
    Map<Integer, PriorityQueue<Integer>> valueToIndices;

    public NumberContainers1() {
        indexToValue = new HashMap<>();
        valueToIndices = new HashMap<>();
    }

    public void change(int index, int number) {
        indexToValue.put(index, number);
        valueToIndices.computeIfAbsent(number, k -> new PriorityQueue<>()).offer(index);
    }

    public int find(int number) {
//        if number doesn't exist
        if(!valueToIndices.containsKey(number))
            return -1;
//        get min heap of this number
        PriorityQueue<Integer> minHeap = valueToIndices.get(number);
        while(!minHeap.isEmpty()) {
            int index = minHeap.peek();
//            if index still maps to the target number, then return it
            if(indexToValue.get(index) == number) {
                return index;
            }
//            else remove stale index
            minHeap.poll();
        }
        return -1;
    }
}

/**
 * Your NumberContainers object will be instantiated and called as such:
 * NumberContainers obj = new NumberContainers();
 * obj.change(index,number);
 * int param_2 = obj.find(number);
 */

/*
Design a number container system that can do the following:
Insert or Replace a number at the given index in the system.
Return the smallest index for the given number in the system.
Implement the NumberContainers class:
NumberContainers() Initializes the number container system.
void change(int index, int number) Fills the container at index with the number. If there is already a number at that index, replace it.
int find(int number) Returns the smallest index for the given number, or -1 if there is no index that is filled by number in the system.
Example 1:
Input
["NumberContainers", "find", "change", "change", "change", "change", "find", "change", "find"]
[[], [10], [2, 10], [1, 10], [3, 10], [5, 10], [10], [1, 20], [10]]
Output
[null, -1, null, null, null, null, 1, null, 2]
Explanation
NumberContainers nc = new NumberContainers();
nc.find(10); // There is no index that is filled with number 10. Therefore, we return -1.
nc.change(2, 10); // Your container at index 2 will be filled with number 10.
nc.change(1, 10); // Your container at index 1 will be filled with number 10.
nc.change(3, 10); // Your container at index 3 will be filled with number 10.
nc.change(5, 10); // Your container at index 5 will be filled with number 10.
nc.find(10); // Number 10 is at the indices 1, 2, 3, and 5. Since the smallest index that is filled with 10 is 1, we return 1.
nc.change(1, 20); // Your container at index 1 will be filled with number 20. Note that index 1 was filled with 10 and then replaced with 20.
nc.find(10); // Number 10 is at the indices 2, 3, and 5. The smallest index that is filled with 10 is 2. Therefore, we return 2.
Constraints:
1 <= index, number <= 109
At most 105 calls will be made in total to change and find.
 */