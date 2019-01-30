package spell;

/**
 * Created by testtake on 5/15/18.
 */

public class Node implements ITrie.INode {
    Node[] nodes;
    int frequency;

    public Node() {
        nodes = new Node[26];
        frequency = 0;
    }

    public int getValue() {
        return frequency;
    }
    public void increment() {
        frequency++;
    }

    public Node add(char child) {
        int index = (int) child - 97;
        if (nodes[index] == null) nodes[index] = new Node();
        return nodes[index];
    }
    public Node getChild(char child) {
        int index = (int) child - 97;
        return nodes[index];
    }
    public boolean isNew(char child) {
        int index = (int) child - 97;
        if (nodes[index] == null) return true;
        return false;
    }
}
