public class LinkedStack {
    public static void main(String[] args) {
        LinkedList list = new LinkedList(6);

        print(list);

        System.out.println("push!!");
        push(list, 60);
        print(list);

        System.out.println("pop!!");
        pop(list);
        print(list);
        
    }

    public static void push(LinkedList list, int item){
        list.add(item);
    }

    public static void pop(LinkedList list){
        if(list.length() > 0){
            int stackTop = list.first.head;
            list.remove(stackTop);
        }
    }

    public static void print(LinkedList list){
        for(LinkedList.Cell curr = list.first; curr != null; curr = curr.tail){
            System.out.println("addres is "  + curr);
            System.out.println("value is " + curr.head);
        }
    }
}
