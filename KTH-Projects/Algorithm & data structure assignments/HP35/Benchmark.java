public class Benchmark {
    public static void benchmark(Stack stack, int ppLoop, int opLoop) {
        for(int i = 0; i < opLoop; i++){
            for(int j = 0; j < ppLoop; j++){  //Push 1000 times
                stack.push(1);
            }
            for(int j = 0; j < ppLoop; j++){  //Pop 1000 times
                stack.pop();
            }

        }
    }

    public static double benchtime(Stack stack, int runs, int opLoop, int ppLoop){
        double min = Double.POSITIVE_INFINITY;

        for(int i = 0; i < runs; i++){   //Do the push and pop operation cycle 100 times
            System.gc();
            double t0 = System.nanoTime();
            benchmark(stack, ppLoop, opLoop);
            double t1 = System.nanoTime();
            double t = (t1 - t0);

            if(t < min){        //Find the minimum time value
                min = t;
            }

        }
        return min;
    }

    static void printBench(int num){
        int runs = 100;
        int opLoop = 1000;
        int ppLoop = num;

        Stack StaticStack = new Static(1024);
        Stack DynamicStack = new Dynamic(4);

        benchtime(StaticStack, runs, opLoop, ppLoop);       //Warmup

        double staticTime = benchtime(StaticStack, runs, opLoop, ppLoop);
        double dynamicTime = benchtime(DynamicStack, runs, opLoop, ppLoop);

        System.out.printf("%d \t %.2f \t %.2f \t %.2f \n", ppLoop, (staticTime/(opLoop*1000)), (dynamicTime/(opLoop*1000)), (dynamicTime/staticTime));

    }

    public static void main(String[] args) {

        System.out.printf("%s\t%s\t%s\t%s\n", "Number", "Static", "Dynamic", "Ratio");
        System.out.println("==============================");
        printBench(5);
        printBench(10);
        printBench(25);
        printBench(50);
        printBench(75);
        printBench(100);
        printBench(250);
        printBench(500);
        printBench(750);
        printBench(1000);
        
        
    }
}
