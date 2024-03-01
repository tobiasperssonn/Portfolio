import java.util.Iterator;
import java.util.Stack;

public class TreeHeap {
    public class Node {
        public Integer key;
        public Integer value;
        public Integer size;
        public Node left, right;

        public Node(Integer key, Integer value) {
            this.key = key;
            this.value = value;
            this.size = 1;
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

    public TreeHeap() {
        root = null;
    }

    public void add(Integer key, Integer value) {
        addRecursive(root, key, value);
    }

    public void addRecursive(Node curr, Integer key, Integer value) {
        if(root == null) {
            root = new Node(key, value);
            return;
        }

        curr.size++;
        if(value < curr.value) {
            curr.value = value;
        }

        if(curr.left == null) {
            curr.left = new Node(key, value);
            return;
        }

        if(curr.right == null){
            curr.right = new Node(key, value);
            return;
        }

        Integer leftSize = (curr.left != null) ? curr.left.size: 0;
        Integer rightSize = (curr.right != null) ? curr.right.size: 0;

        if(leftSize < rightSize) {
            addRecursive(curr.left, key, value);
        } else {
            addRecursive(curr.right, key, value);
        }
    }

    public Integer remove() {
        if(root == null){
            return null;
        }

        if(root.size == 1){
            Integer temp = root.value;
            root = null;
            return temp;
        }

        Node curr = root;

        curr.size--;

        if(root.left == null) {
            root.value = root.right.value;
            if(root.right.size == 1) {
                root.right = null;
                return root.key;
            } else {
                curr = root.right;
                curr.size--;
            }
        }

        else if(root.right == null) {
            root.value = root.left.value;
            if(root.left.size == 1) {
                root.left = null;
                return root.key;
            } else {
                curr = root.left;
                curr.size--;
            }
        }

        else if(root.right.value < root.left.value) {
            root.value = root.right.value;
            if(root.right.size == 1) {
                root.left = null;
                return root.value;
            } else {
                curr = root.right;
                curr.size--;
            }
        }

        else {
            root.value = root.left.value;
            if(root.left.size == 1) {
                root.left = null;
                return root.key;
            } else {
                curr = root.left;
                curr.size--;
            }
        }

        return root.key;
    }

    public Integer push(Integer incr) {
        if(incr == null) {
            return null;
        }
        root.value += incr;
        return pushRecursive(root, 0);
    }

    public Integer pushRecursive(Node curr, Integer depth) {

        if(curr == null) {
            return depth;
        }

        Integer leftSize = (curr.left != null) ? curr.left.size: 0;
        Integer rightSize = (curr.right != null) ? curr.right.size: 0;

        if(leftSize < rightSize) {
            return pushRecursive(curr.left, depth+1);
        } else {
            return pushRecursive(curr.right, depth+1);
        }
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

        
    public static void main(String[] args) {
        TreeHeap tree = new TreeHeap();
        
        tree.add(5,105);
        tree.add(2,102);
        tree.add(7,107);
        tree.add(1,101);
        tree.add(8,108);
        tree.add(6,106);
        tree.add(3,103);

        tree.remove();
    }
}