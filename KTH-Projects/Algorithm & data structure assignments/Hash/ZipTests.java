public class ZipTests {
    public static void main(String[] args) {
        Zip zip = new Zip("C:/Programmering/AlgoData/Hash/postnummer.csv");
        ZipInt zipInt = new ZipInt("C:/Programmering/AlgoData/Hash/postnummer.csv");
        ZipKey zipKey = new ZipKey("C:/Programmering/AlgoData/Hash/postnummer.csv");
        ZipHash zipHash = new ZipHash("C:/Programmering/AlgoData/Hash/postnummer.csv");
        ZipBucket zipBucket = new ZipBucket("C:/Programmering/AlgoData/Hash/postnummer.csv");
        ZipBetter zipBetter = new ZipBetter("C:/Programmering/AlgoData/Hash/postnummer.csv");

        System.out.println(zip.linear("196 38"));
        System.out.println(zip.binary("98499"));

        System.out.println(zipInt.linear(19638));
        System.out.println(zipInt.binary(98499));

        System.out.println(zipKey.lookup("984 99"));

        zipHash.collisions(12345);

        zipBucket.collisions(10);
        System.out.println(zipBucket.lookupCount(19638));

        zipBetter.collisions(10);
        System.out.println(zipBetter.lookupCount(19638));

    }
}
