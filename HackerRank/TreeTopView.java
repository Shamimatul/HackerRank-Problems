import java.util.*;
import java.io.*;

class Node {
    Node left;
    Node right;
    int data;

    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

class Solution {

	/*

    class Node
    	int data;
    	Node left;
    	Node right;
	*/
public static void topView(Node root) {
    class Pair {
      Node n;
      int p;

      Pair(Node n, int p) {
        this.n = n;
        this.p = p;
      }
    }

    int[] values = new int[500 + 1];
    for (int i = 0; i < 500 + 1; i++) {
      values[i] = -1;
    }
    LinkedList<Pair> queue = new LinkedList<>();
    queue.push(new Pair(root, 250));

    while (!queue.isEmpty()) {
      Pair current = queue.pop();

      if (values[current.p] == -1) {
        values[current.p] = current.n.data;
      }

      if (current.n.left != null) {
        queue.add(new Pair(current.n.left, current.p - 1));
      }

      if (current.n.right != null) {
        queue.add(new Pair(current.n.right, current.p + 1));
      }
    }

    for (int i = 0; i < 500 + 1; i++) {
      if (values[i] != -1) {
        System.out.print(values[i] + " ");
      }
    }
  }
  public static Node insert(Node root, int data) {
        if(root == null) {
            return new Node(data);
        } else {
            Node cur;
            if(data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        Node root = null;
        while(t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        scan.close();
        topView(root);
    }
}
