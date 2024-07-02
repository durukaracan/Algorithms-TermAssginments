
// Node class for trie structure.
// Each node represents a character in some inserted word.
// 'isWord' is true if the node marks the end of a word.
//  'next' array holds references to child nodes.

public class Node {
    public boolean isWord;
    public Node[] next;

    // Constructor initializes the array of Node references
    public Node(int R) {  // 'R' could be passed to make it more flexible or defined here directly
        this.isWord = false;
        this.next = new Node[R];
    }
}
