public class StaticStack {
    int[] stack;
    int top = 0;

    public StaticStack(int size) {
        stack = new int[size];
        top = 0;
    }

    public void push(int val) {
        // what if top == size?
        stack[top] = val;
        top++;
    }

    public int pop() {
        top--;
        return stack[top];
    }

    public static void main(String[] args) {

        StaticStack s = new StaticStack(16);

        s.push(32);
        s.push(33);
        s.push(34);

        System.out.println("pop :" + s.pop());
        System.out.println("pop :" + s.pop());
        System.out.println("pop :" + s.pop());
    }
}
