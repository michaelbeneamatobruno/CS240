package spell;

/**
 * Created by testtake on 5/15/18.
 */

public class Trie implements ITrie {
    public Node root;
    public int wordCount;
    public int nodeCount;

    public Trie() {
        root = new Node();
        wordCount = 0;
        nodeCount = 1;
    }

    public void add(String word) {
        Node current = root;
        for (char i:word.toCharArray()) {
            if (current.isNew(i)) {
                nodeCount++;
            }
            current = current.add(i);
        }
        if (current.getValue() == 0) {
            wordCount++;
        }
        current.increment();
    }
    public INode find(String word) {
        Node current = root;
        for (char i:word.toCharArray()) {
            if (current != null)
            {
                current = current.getChild(i);
            }
            else{
                return null;
            }
        }
        if (current.getValue() == 0) {
            return current;
        }
        return current;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder out = new StringBuilder();
        Node current = root;
        toStringHelper(current, sb, out);
        return out.toString();
    }
    public void toStringHelper(Node currentNode, StringBuilder sb, StringBuilder out) {
        if (currentNode == null) return;
        if (currentNode.getValue() > 0) out.append(sb.toString() + "\n");
        for (int i = 0; i < 26; i++) {
            char currentChar = (char)(i+97);
            sb.append(currentChar);
            toStringHelper(currentNode.getChild(currentChar), sb, out);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
    @Override
    public int hashCode() {
        return 31*nodeCount*wordCount;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() != this.getClass()) return false;
        Trie object = (Trie) o;
//        if (object.getNodeCount() != getNodeCount()) return false;
//        if (object.getWordCount() != getWordCount()) return false;
        if (!checkEachNode(root, object.root)) return false;
        return true;
    }
    public boolean checkEachNode(Node thisNode, Node checkNode) {
        if (thisNode == null || checkNode == null) return (thisNode == null && checkNode == null);
        if (thisNode.getValue() != checkNode.getValue()) return false;
        for (int i = 0; i < 25; i++) {
            char child = (char) (i + 97);
            if (!checkEachNode(thisNode.getChild(child), checkNode.getChild(child))) {
                return false;
            }
        }
        return true;
    }
    public int getWordCount() {
        return wordCount;
    }
    public int getNodeCount() {
        return nodeCount;
    }
}
