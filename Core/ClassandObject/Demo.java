class Calculator {
    // Method to add two numbers
    public int add(int n1, int n2) {
        System.out.println("In add method");
        int res = n1 + n2;
        return res;
    }
}


public class Demo {
    public static void main(String[] args) {
        int num1 = 4;
        int num2 = 5;
        // Creating an object of the Calculator class
        Calculator calc = new Calculator();
        // Calling the add method of Calculator class
        int result = calc.add(num1, num2);
        // Printing the result
        System.out.println("The sum is: " + result);
    }
}