public abstract class TreeStack {
    public abstract void push(BinaryTree.Node node);
    public abstract BinaryTree.Node pop();
}


//Stack with a dynamic size
class Dynamic extends TreeStack {
    final int INITSIZE;
    int size;
    int i = 0;      

    BinaryTree.Node[] stack;

    public Dynamic(int initSize){       //Constructor for the dynamic stack
        INITSIZE = initSize;        //Initial size for the dynamic stack
        stack = new BinaryTree.Node[INITSIZE];
        size = INITSIZE;
    }

    public void push(BinaryTree.Node node) {

        if(i > size-1) {     //If the stack is full, enlargen it by *2
            stack = resizeStack(stack, size*2);
        }

        stack[i++] = node; //Add the value to the top of the stack and increase the pointer one step
    }
    
    public BinaryTree.Node pop() {
        if(i+1 <= (size/4) && (size/2) > INITSIZE){   //If the number of numbers in the stack is half of the size of the stack, decrease the size of the stack
            stack = resizeStack(stack, (size/2));
        }

        return stack[--i];  //Remove the value at the top of the stack
    }

    public BinaryTree.Node[] resizeStack(BinaryTree.Node[] stack, int newSize) {
        BinaryTree.Node[] temp = new BinaryTree.Node[newSize];      //Crate a stack with a new size

        for(int j = 0; j < i; j++){      //Copy the values from the old stack to the new stack
            temp[j] = stack[j]; 
        }
        
        stack = temp;
        size = newSize;

        return stack;
    }

    public boolean isEmpty(){
        return i == 0;
    }
}

