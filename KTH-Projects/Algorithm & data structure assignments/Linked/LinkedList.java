class LinkedList {
    Cell first;

    public class Cell {
        int head;
        Cell tail;

        Cell(int val, Cell tl) {
            head = val;
            tail = tl;
        }
    }

    LinkedList(int n) {
        Cell last = null;
        for(int i = 0; i < n; i++){
            last = new Cell(i, last);
        }
        first = last;
    }

    public void add(int item) {
        first = new Cell(item, first);
    }

    public int length() {
        int counter = 0;

        for(Cell curr = first; curr != null; curr = curr.tail){
            counter++;
        }

        return counter;
    }

    public boolean find(int item) {
        for(Cell curr = first; curr != null; curr = curr.tail){
            if(curr.head == item){
                return true;
            }
            curr = curr.tail;
        }

        return false;
    }

    public void remove(int item) {
        Cell prv = null;

        for(Cell curr = first; curr != null; curr = curr.tail){
            if(curr.head == item){
                if(prv != null){
                    prv.tail = curr.tail;
                } else {
                    first = curr.tail;
                }
                return;
            }
            prv = curr;
        }
    }

    public void append(LinkedList b) {
        Cell nxt = this.first;
        Cell prv = null;

        while (nxt != null && nxt.tail != null) {
        prv = nxt;
        nxt = nxt.tail;
        }

        if(prv != null){
            prv.tail = b.first;
        } else {
            this.first = b.first;
        }

        b.first = null;

    }
}
