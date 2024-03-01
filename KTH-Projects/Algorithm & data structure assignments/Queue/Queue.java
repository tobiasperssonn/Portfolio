public class Queue {
    Node head;
    Node last;

    private class Node {

        Integer item;
        Node next;

        private Node(Integer item, Node list) {

            this.item = item;
            this.next = list;
        }
    }

    public Queue() {
        head = null;
        last = null;
    }

    public void add(Integer item) {
        Node newNode = new Node(item, null);
        //The list is empty
        if(head == null) {
            head = newNode;
            last = newNode;
        } 
        //If list is not empty
        else { 
            last.next = newNode;
            last = newNode;
        }
    }

    public Integer remove() {
        if(head == null) {
            return null;
        }

        Integer removedValue = head.item;
        head = head.next;

        if(head == null) {
            last = null;
        }

        return removedValue;
    }
}