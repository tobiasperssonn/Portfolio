public class Calculator {

    Item[] expr;
    int ip;
    Stack stack;

    public Calculator(Item[] expr) {
        this.expr = expr;
        this.ip = 0;
        this.stack = new Dynamic(1);
    }

    public int run() {
        while ( ip < expr.length ) {
            step();
        }
        return stack.pop();
        
    }

    public void step() {

        Item nxt = expr[ip++];

        switch(nxt.type()) {
            case ADD : {
                int y = stack.pop();
                int x = stack.pop();
                stack.push(x + y);
                break;
            }
            case SUB : {
                int y = stack.pop();
                int x = stack.pop();
                stack.push(x - y);
                break;
            }
            case MUL : {
                int y = stack.pop();
                int x = stack.pop();
                stack.push(x * y);
                break;
            }
            case DIV : {
                int y = stack.pop();
                int x = stack.pop();
                stack.push(x / y);
                break;
            }
            case VALUE : {
                stack.push(nxt.value());
                break;
            }
        }
    }


    public static void main(String[] args) {
        Item[] expr = {
            Item.Value(10),
            Item.Value(2),
            Item.Value(5),
            Item.Mul(),
            Item.Add()
        };

        Calculator calc = new Calculator(expr);

        int res = calc.run();

        System.out.println(" Calculator: res = " + res);

    }
}
