import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class MethodReferenceDemo {

    static class Employee {
        String name;
        Employee(String n) { this.name = n; }
        void display()     { System.out.println("Employee: " + name); }
        public String toString() { return "Employee(" + name + ")"; }
    }

    // A static method to demonstrate Type 1
    static void printUpperCase(String s) {
        System.out.println(s.toUpperCase());
    }

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        // -------------------------------------------------------
        // TYPE 1: Static Method Reference  →  ClassName::method
        // -------------------------------------------------------
        System.out.println("--- Type 1: Static Method Reference ---");

        // Lambda:           s -> MethodReferenceDemo.printUpperCase(s)
        // Method Reference: MethodReferenceDemo::printUpperCase
        names.stream()
                .forEach(MethodReferenceDemo::printUpperCase);
        // Output: ALICE  BOB  CHARLIE

        // -------------------------------------------------------
        // TYPE 2: Specific Object  →  object::method
        // -------------------------------------------------------
        System.out.println("\n--- Type 2: Specific Object Method Reference ---");

        // Lambda:           name -> System.out.println(name)
        // Method Reference: System.out::println
        names.stream()
                .forEach(System.out::println);
        // Output: Alice  Bob  Charlie

        // -------------------------------------------------------
        // TYPE 3: Arbitrary Object  →  ClassName::instanceMethod
        // -------------------------------------------------------
        System.out.println("\n--- Type 3: Arbitrary Object Method Reference ---");

        // Lambda:           s -> s.toUpperCase()
        // Method Reference: String::toUpperCase
        List<String> upper = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(upper);
        // Output: [ALICE, BOB, CHARLIE]

        // -------------------------------------------------------
        // TYPE 4: Constructor Reference  →  ClassName::new
        // -------------------------------------------------------
        System.out.println("\n--- Type 4: Constructor Reference ---");

        // Lambda:           name -> new Employee(name)
        // Method Reference: Employee::new
        List<Employee> employees = names.stream()
                .map(Employee::new)
                .collect(Collectors.toList());
        employees.forEach(System.out::println);
        // Output: Employee(Alice)  Employee(Bob)  Employee(Charlie)
    }
}