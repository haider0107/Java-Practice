package datatypes;

public class Test {
    public static void main(String[] args) {
//        System.out.println("Hello World");
        char a = 'n';
        System.out.println(a);
        System.out.println((int) Character.MIN_VALUE);
        System.out.println((int) Character.MAX_VALUE);

//        for(int i=0;i<100;i++){
//            System.out.println( i);
//        }

        int[] z = new int[100];
        for(int i=0;i<100;i++){
            z[i] = i;
        }
    }
}
