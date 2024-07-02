// CENG383 - PA2
// Author: Duru Karacan 202128022

/* Summary - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

SpellChecker, checks spelling of words input by the user against a dictionary loaded from a file.
It employs a trie (prefix tree) data structure.

 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */

import java.io.*;
import java.util.*;
public class SpellChecker {
    private static int R = 256;
    private Node root = new Node(R);


    // Inserts a word into the trie.
    // Starting from the root, the method goes deeper according to each character in the word.
    // word = The word to be inserted into the trie.
    public void insert(String word) {
        root = insert(root, word, 0);
    }


    // Recursive method to insert a word into the trie.
    // node = The current node in the trie.
    // word = The word being inserted.
    // depth = The depth, representing the character of the word.
    // return node; = The updated node after insertion.
    private Node insert(Node node, String word, int depth) {
        if (node == null) node = new Node(R);
        if (depth == word.length()) {
            node.isWord = true;
            return node;
        }
        char c = word.charAt(depth);
        node.next[c] = insert(node.next[c], word, depth + 1);
        return node;
    }


    // Determines if the trie contains the given word by traversing nodes corresponding to each character of the word.
    // word = The word to check in the trie.
    // return true if the word exists in the trie, otherwise false.
    public boolean contains(String word) {
        Node node = get(root, word, 0);
        return node != null && node.isWord;
    }


    // Recursive method to find the node corresponding to the last character of the given word, if it exists
    // node = The current node
    // word = The word being checked.
    // indexChar = The character index in the word.
    // return The node representing the last character of the word.
    private Node get(Node node, String word, int indexChar) {
        if (node == null) return null;
        if (indexChar == word.length()) return node;
        char c = word.charAt(indexChar);
        return get(node.next[c], word, indexChar + 1);
    }


    //Suggests words from the trie that begin with the given prefix. Collects a maximum of three suggestions.
    // prefix = The prefix for which suggestions are needed.
    // return = A list of up to three suggested words.
    public List<String> suggest(String prefix) {
        List<String> results = new ArrayList<>();
        Node node = get(root, prefix, 0);
        collect(node, new StringBuilder(prefix), results);
        return results;
    }


    //Helper method to collect words from the trie that start with the given prefix. Stops collecting after three words.
    // node = The current node from which to collect words.
    // prefix = The current prefix being formed.
    // results = List to store the results.
    private void collect(Node node, StringBuilder prefix, List<String> results) {
        if (node == null || results.size() >= 3) return;
        if (node.isWord) results.add(prefix.toString());
        for (char c = 0; c < R; c++) {
            prefix.append(c);
            collect(node.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }



    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        if (args.length != 1) {
            System.err.println("dictonary.txt ye erişemiyor");
            return;
        }

        //if the code works properly, we can create SpellChecker object.
        SpellChecker checker = new SpellChecker();

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String word;
            while ((word = br.readLine()) != null) {
                checker.insert(word.trim().toUpperCase());
            }
        }

        System.out.println("\nPress 'enter' key directly to terminate the program");
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - -\n");
        while (true) {
            System.out.print("INPUT -> ");
            String input = scanner.nextLine().trim().toUpperCase();

            //if user press 'enter' key, the  program terminates
            if (input.isEmpty()) {
                System.out.println("\nSpellChecker terminates ...");
                break;
            }

            if (checker.contains(input)) {
                System.out.println("OUTPUT -> correct word");
            } else {
                System.out.print("OUTPUT -> misspelled? ");
                List<String> suggestions = checker.suggest(input);
                if (suggestions.isEmpty()) {
                    System.out.println("no suggestions available!");
                } else {
                    System.out.println(String.join(", ", suggestions));
                }
            }
        }
    }
}
