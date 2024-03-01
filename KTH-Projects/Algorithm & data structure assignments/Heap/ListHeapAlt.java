public class ListHeapAlt {
    Node head;

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

    public ListHeapAlt() {
        head = null;
    }

    // O(n)
    public void add(Integer data, Integer prio) {
        Node newNode = new Node(data, prio, null);
        Node current = head;
        Node prev = null;

        //The list is empty
        if(head == null) {
            head = newNode;
            return;
        } 

        if(head.prio < newNode.prio){
            newNode.next = head;
            head = newNode;
            return;
        }

        else {
            while(current.next != null && current.next.prio < newNode.prio){
                prev = current;
                current = current.next;
            }

            if(prev == null) {
                newNode.next = head;
                head = newNode;
            } else {
                prev.next = newNode;
                newNode.next = current; 
            }
        }
    }

    // O(1)
    public Integer remove() {
        Node removedValue = head;
        head = head.next;

        return removedValue.data;
    }
}