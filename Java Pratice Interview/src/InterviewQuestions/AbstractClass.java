package InterviewQuestions;

// =====================================================
// ❓ ABSTRACT CLASS - COMPLETE REVISION (SINGLE PAGE)
// =====================================================

// ❌ Can we create object of abstract class?
// NO — Abstract classes cannot be instantiated directly
// Reason: They may contain abstract (incomplete) methods

// =====================================================
// ✔ WHY ABSTRACT CLASS EXISTS?
// =====================================================

// - Acts as a blueprint/template
// - Forces child classes to implement required behavior
// - Supports code reuse via concrete methods

// =====================================================
// ✔ ABSTRACT CLASS EXAMPLE
// =====================================================

abstract class Animal {

    // Abstract method (no implementation)
    abstract void sound();

    // Concrete method (has implementation)
    void sleep() {
        System.out.println("Animal is sleeping");
    }

    // ✔ Abstract class CAN have constructor
    Animal() {
        System.out.println("Animal constructor called");
    }
}

// =====================================================
// ✔ SUBCLASS IMPLEMENTATION
// =====================================================

class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

// =====================================================
// ✔ MAIN CLASS
// =====================================================

public class AbstractClass {
    public static void main(String[] args) {

        // =====================================================
        // ❌ NOT ALLOWED
        // =====================================================

        // Cannot create object of abstract class directly
        // Animal a = new Animal(); // ❌ Compilation Error


        // =====================================================
        // ✔ ALLOWED (USING SUBCLASS)
        // =====================================================

        // Upcasting: reference = parent, object = child
        Animal a = new Dog();

        a.sound();  // Runtime polymorphism → Dog's method
        a.sleep();  // Inherited method from Animal


        // =====================================================
        // ✔ EDGE CASE (REFERENCE ALLOWED)
        // =====================================================

        Animal ref;  // ✅ Allowed (only reference, no object)


        // =====================================================
        // ✔ ADVANCED: ANONYMOUS CLASS
        // =====================================================

        // Creating object of abstract class using anonymous class
        Animal a2 = new Animal() {
            @Override
            void sound() {
                System.out.println("Anonymous animal sound");
            }
        };

        a2.sound();


        // =====================================================
        // 🧠 IMPORTANT NOTES
        // =====================================================

        // 1. Abstract class = blueprint with partial implementation

        // 2. Cannot instantiate directly:
        //    new Animal(); ❌

        // 3. Must use subclass:
        //    Animal a = new Dog(); ✅

        // 4. Upcasting enables runtime polymorphism

        // 5. Abstract class CAN have:
        //    - Constructors
        //    - Variables
        //    - Concrete methods
        //    - Abstract methods

        // 6. Constructor of abstract class is called
        //    when subclass object is created

        // 7. Anonymous class allows direct object creation
        //    by providing implementation on the fly

        // =====================================================
        // 🚀 OUTPUT
        // =====================================================

        // Animal constructor called
        // Dog barks
        // Animal is sleeping
        // Animal constructor called
        // Anonymous animal sound
    }
}
