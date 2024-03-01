public class StaticStack {
    int[] stack;
    int top = 0;
    int size = 0;

    public DynamicStack() {
        size = 2;
        stack = new int[size];
        top = 0;
    }

    public void push(int val) {
        // what if top == size?
        if (top == size) {
            System.out.println(" Increase from " + size 0 " to " + (size*2));
            int[] copy = new int[size*2];
            for(int i = 0; i < size; i++;) {
                copy[i] = stack[i];
            }
            stack = copy;
            size = size*2;
        }
        stack[top] = val;
        top++;
    }

    public int pop() {
        top--;
        return stack[top];
    }

    public static void main(String[] args) {

        DynamicStack s = new DynamicStack();

        s.push(32);
        System.out.println("push : 32")
        s.push(33);
        System.out.println("push : 33")
        s.push(34);
        System.out.println("push : 34")

        System.out.println("pop :" + s.pop());
        System.out.println("pop :" + s.pop());
        System.out.println("pop :" + s.pop());
    }
}
