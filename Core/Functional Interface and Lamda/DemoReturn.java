@FunctionalInterface
interface A {
    public int sum(int i, int j);
}

public class DemoReturn {
    public static void main(String[] args) {
        
        // Verbose way
        // A obj = new A(){
        //     public int sum(int i, int j){
        //         return i+j;
        //     }
        // };
        // int result = obj.sum(5,6);
        // System.out.println("Sum = " + result);

        // with Lambda
        A obj = (i,j) -> i+j;

        int result = obj.sum(5,6);
        System.out.println("Sum = " + result);
    }
}
