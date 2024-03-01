import java.util.Iterator;
import java.util.Stack;

public class BinaryTree implements Iterable<Integer> {
    public class Node {
        public Integer key;
        public Integer value;
        public Node left, right;

        public Node(Integer key, Integer value) {
            this.key = key;
            this.value = value;
            this.left = this.right = null;
        }

        public void print() {
            if(left != null)
                left.print();
            System.out.println(" key: " + key + "\tvalue: " + value);
            if(right != null)
                right.print();
        }
    }

    Node root;

    public BinaryTree() {
        root = null;
    }

    public void add(Integer key, Integer value) {
        root = addRecursive(root, key, value);

    }

    public Node addRecursive(Node curr, Integer key, Integer value) {
        if(curr == null) {
            return new Node(key, value);
        }

        if(key < curr.key) {
            curr.left = addRecursive(curr.left, key, value);

        }
        else if(key > curr.key) {
            curr.right = addRecursive(curr.right, key, value);
        }
        else {
            curr.value = value;
        }

        return curr;
    }

    public Integer lookup(Integer key) {
        return lookupRecursive(root, key);
    }

    public Integer lookupRecursive(Node curr, Integer key) {
        if(curr == null) {
            return null;
        }

        if(key < curr.key) {
            return lookupRecursive(curr.left, key);
        }
        else if(key > curr.key) {
            return lookupRecursive(curr.right, key);
        }
        else {
            return curr.value;
        }
    }

    public Iterator<Integer> iterator() {
        return new TreeIterator();
    }

    public class TreeIterator implements Iterator<Integer> {
        private Node next;
        private Dynamic stack;
        //private Stack<Node> stack;

        public TreeIterator() {
            next = root;
            stack = new Dynamic(20);

            while (next != null) {
                stack.push(next);
                next = next.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Integer next() {
            if(!hasNext()) {
                return null;
            }

            next = stack.pop();
            Integer res = next.value;

            if(next.right != null){
                while (next.right != null) {
                    stack.push(next.right);
                    next.right = next.right.left;
                }
            }

            return res;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
        
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        
        tree.add(5,105);
        tree.add(2,102);
        tree.add(7,107);
        tree.add(1,101);
        tree.add(8,108);
        tree.add(6,106);
        tree.add(3,103);

        for (int i : tree) {
            System.out.println("next value " + i);
        }
    }
}