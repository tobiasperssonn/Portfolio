public class ListHeap {
    Node head;
    Node last;

    private class Node {

        Integer data;
        Integer prio;
        Node next;

        private Node(Integer data, Integer prio, Node list) {
            this.data = data;
            this.prio = prio;
            this.next = list;
        }
    }

    public ListHeap() {
        head = null;
        last = null;
    }

    // O(1)
    public void add(Integer data, Integer prio) {
        Node newNode = new Node(data, prio, null);

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

    // O(n)
    public Integer remove() {
        //Queue is empty
        if(head == null){
            return null;
        }

        Node current = head;
        Node prev = null;
        Integer removedValue = 0;
        Integer min = Integer.MAX_VALUE;
        Node nodeRemove = null;
        Node nodeRemovePrev = null;
        
        while(current.next != null){
            if(current.prio < min){
                min = current.prio;
                removedValue = current.data;
                nodeRemove = current;
                nodeRemovePrev = prev;
            }
            prev = current;
            current = current.next;
        }

        if(nodeRemove == null){
            head = head.next;
        } else {
            if(nodeRemovePrev == null) {
                head = nodeRemove.next;
            } else {
                nodeRemovePrev.next = nodeRemove.next;
            }
        }

        return removedValue;
    }
}