public class ZipBench {
    public static void main(String[] args) {
        Zip zip = new Zip("C:/Programmering/AlgoData/Hash/postnummer.csv");
        ZipInt zipInt = new ZipInt("C:/Programmering/AlgoData/Hash/postnummer.csv");
        ZipKey zipKey = new ZipKey("C:/Programmering/AlgoData/Hash/postnummer.csv");
        ZipHash zipHash = new ZipHash("C:/Programmering/AlgoData/Hash/postnummer.csv");
        ZipBucket zipBucket = new ZipBucket("C:/Programmering/AlgoData/Hash/postnummer.csv");
        ZipBetter zipBetter = new ZipBetter("C:/Programmering/AlgoData/Hash/postnummer.csv");

        int k = 1000;
        int prefix = 1000;

        //BENCH 1 regular zip ###########################################################################################

        System.out.printf("Searching for 111 15 & 984 99\n");
        System.out.printf("%8s%8s\n", "Linear", "Binary");

        double min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            zip.linear("111 15");
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
        }

        System.out.printf("%8.1f", min/prefix);	 

        min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            zip.binary("111 15");
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
        }

        System.out.printf("%8.1f\n", min/prefix);

        min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            zip.linear("984 99");
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
        }

        System.out.printf("%8.1f", min/prefix);
        
        min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            zip.binary("984 99");
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
        }

        System.out.printf("%8.1f\n", min/prefix);

        System.out.println();	

        //BENCH 2 integer zip ###########################################################################################

        System.out.printf("Searching for 111 15 & 984 99 now using integer\n");
        System.out.printf("%8s%8s\n", "Linear", "Binary");

        min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            zipInt.linear(11115);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
        }

        System.out.printf("%8.1f", min/prefix);	 

        min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            zipInt.binary(11115);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
        }

        System.out.printf("%8.1f\n", min/prefix);

        min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            zipInt.linear(98499);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
        }

        System.out.printf("%8.1f", min/prefix);	 

        min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            zipInt.binary(98499);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
        }

        System.out.printf("%8.1f\n", min/prefix);
        System.out.println();		

        //BENCH 3 key ###########################################################################################
        
        System.out.printf("Searching for 111 15 & 984 99 now using keys\n");
        System.out.printf("%8s\n", "Lookup");

        min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            zipKey.lookup("111 15");
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
        }

        System.out.printf("%8.1f\n", min/prefix);

        min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            zipKey.lookup("984 99");
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
        }

        System.out.printf("%8.1f\n", min/prefix);
        System.out.println();	
        
        //BENCH 4 hash ###########################################################################################

        int[] sizes = {10000,20000,40000,80000,160000,320000,12345,13513,13600,14000};

        System.out.printf("Using different array sizes for the hash\n");

        for ( int n : sizes) {            
            zipHash.collisions(n);
            System.out.println();
        }

        //BENCH 5 buckets & slightly better ################################################################################

        int[] sizes2 = {1000,2000,4000,8000,16000,32000};

        System.out.printf("623 49 using the bucket and the slightly better solution, while also increasing the array sizes\n");
        System.out.printf("%8s%8s%8s\n", "N","Bucket","Better");

        for ( int n : sizes2) { 
            System.out.printf("%8d", n);           
            zipBucket.collisions(n);
            zipBetter.collisions(n);

            System.out.printf("%8d", zipBucket.lookupCount(62349));
            System.out.printf("%8d\n", zipBetter.lookupCount(11120));
        }

    }
}
