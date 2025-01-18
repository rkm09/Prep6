package common;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public int val;
    public List<Node> children;
    public Node() {
        children = new ArrayList<>();
    }
    public Node(int val) {
        this.val = val;
        children = new ArrayList<>();
    }
    public Node(int val, List<Node> children) {
        this.val = val;
        this.children = children;
    }
}