public abstract class Stack {
    public abstract void push(int value);
    public abstract int pop();
}

//Stack with a static size
class Static extends Stack {
    int i = 0;
    int[] stack;

    public Static(int size) {   //Constructor for the static stack
        stack = new int[size];
    }

    public void push(int value) {
        try{
            //System.out.println("push: " + value);
            stack[i++] = value;
        }
        catch(ArrayIndexOutOfBoundsException e){    //If you try to push a value when the stack is full, give a error and exit the program
            System.out.println("Can't push more items, becouse the stack is full!");
            System.out.println("Try increasing the size of the stack");
            System.exit(0);
        }
    }

    public int pop() {
        if(i != 0){
            //System.out.println("pop: " + stack[i-1]);
            return stack[--i];
        }
        else {      //Return 0 if the user tries to pop, when the stack is empty
            return 0;
        }
    }
}

//Stack with a dynamic size
class Dynamic extends Stack {
    final int INITSIZE;
    int size;
    int i = 0;      

    int[] stack;

    public Dynamic(int initSize){       //Constructor for the dynamic stack
        INITSIZE = initSize;        //Initial size for the dynamic stack
        stack = new int[INITSIZE];
        size = INITSIZE;
    }

    public void push(int value) {
        //System.out.println("pushing : " + value);
        //System.out.println("i = " + i);
        //System.out.println("Current stack size : "+ stack.length);

        if(i > size-1) {     //If the stack is full, enlargen it by *2
            //System.out.println("old size: "+ stack.length);
            stack = resizeStack(stack, size*2);
            //System.out.println("new size: "+ stack.length);
        }

        stack[i++] = value; //Add the value to the top of the stack and increase the pointer one step
    }
    
    public int pop() {
        if(i+1 <= (size/4) && (size/2) > INITSIZE){   //If the number of numbers in the stack is half of the size of the stack, decrease the size of the stack
            stack = resizeStack(stack, (size/2));
        }

        return stack[--i];  //Remove the value at the top of the stack
    }

    public int[] resizeStack(int[] stack, int newSize) {
        int[] temp = new int[newSize];      //Crate a stack with a new size

        for(int j = 0; j < i; j++){      //Copy the values from the old stack to the new stack
            temp[j] = stack[j]; 
        }
        
        stack = temp;
        size = newSize;

        return stack;
    }
}

