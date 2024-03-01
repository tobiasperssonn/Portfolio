public class Item {
    private Itemtype type;
    private int value = 0;

    //constructor for the VALUE operand
    public Item(Itemtype type, int value) {
        this.type = type;
        this.value = value;

    }

    //constructor for the operands with only a Itemtype. (+-*/)
    public Item(Itemtype type) {
        this.type = type;
        this.value = 0;
    }

    public static Item Add(){
        return new Item(Itemtype.ADD);
    }
    public static Item Sub(){
        return new Item(Itemtype.SUB);
    }
    public static Item Mul(){
        return new Item(Itemtype.MUL);
    }
    public static Item Div(){
        return new Item(Itemtype.DIV);
    }
    public static Item Value(int number){
        return new Item(Itemtype.VALUE, number);
    }

    
    //getter methods for the value and the type
    public int value() {
        return value;
    }

    public Itemtype type() {
        return type;
    }

    
    public enum Itemtype {
        ADD,
        SUB,
        MUL,
        DIV,
        VALUE
    }
}