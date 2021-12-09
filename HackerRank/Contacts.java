import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


public class Solution {

    public static class TrieNode {
        private TrieNode parent;
        private TrieNode[] children;
        private boolean isLeaf;
        private boolean isWord;
        private char character;
        private int count;


        public TrieNode() {
           children = new TrieNode[26];
           isLeaf = true;
           isWord = false;
           count = 0;
        }

        public TrieNode(char character) {
            this();
            this.character = character;
            this.count = 1;
        }


        protected void addWord(String word) {
            isLeaf = false;
            int charPos = word.charAt(0) - 'a';

            if (children[charPos] == null) {
                children[charPos] = new TrieNode(word.charAt(0));
                children[charPos].parent = this;
            } else {
                children[charPos].count++;
            }

            if (word.length() > 1) {
                children[charPos].addWord(word.substring(1));
            } else {
                children[charPos].isWord = true;
            }
        }

        protected int queryCount(String word) {
            int charPos = word.charAt(0) - 'a';

            if ((word.length() == 1) && (children[charPos] != null)) {
                return children[charPos].count;
            } else if (children[charPos] == null)
                return 0;
            else
                return children[charPos].queryCount(word.substring(1));
        }


        protected TrieNode getNode(char c) {
           return children[c - 'a'];
       }
   }

    public static class Trie {
        private TrieNode root;


        public Trie() {
            root = new TrieNode();
        }


        public void addWord(String word) {
            root.addWord(word.toLowerCase());
        }

        public int queryCount(String word) {
          if (word.length() == 0)
            return 0;
        else
            return root.queryCount(word.toLowerCase());
       }
   }

    public static void main(String[] args) {
        Trie trie = new Trie();

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            String op = sc.next();
            String word = sc.next();
            if (op.equals("add")) {
                trie.addWord(word);
            } else {
                System.out.println(trie.queryCount(word));
            }
        }
    }
}
