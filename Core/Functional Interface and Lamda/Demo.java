@FunctionalInterface
interface A{

    // only one abstract method
    public void show(int i);
    // public void show2();
}

public class Demo {
    public static void main(String[] args) {

        // Verbose way of creating an object of A interface
        // A obj = new A(){
        //     public void show(int i){
        //         System.out.println("In A Class with i = " + i);
        //     }
        // };
        // obj.show(5);

        // Lamda expression way of creating an object of A interface
        A obj = i -> System.out.println("In A Class with i = " + i); // () -> System.out.println("In A Class");
        obj.show(5);
    }
}
