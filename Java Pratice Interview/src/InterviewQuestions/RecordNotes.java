package InterviewQuestions;

// ╔══════════════════════════════════════════════════════╗
// ║           RECORDS IN JAVA — NOTES + EXAMPLES        ║
// ║                  Java 16+ Feature                   ║
// ╚══════════════════════════════════════════════════════╝


// ═══════════════════════════════════════════════════════
// WHAT IS A RECORD?
// ═══════════════════════════════════════════════════════
//
// A special class designed to hold IMMUTABLE data
// Eliminates boilerplate code for simple data classes
// Introduced: Preview Java 14, Stable Java 16
//
// ONE LINE replaces ~35 lines of boilerplate:
//
//   public record Person(String name, int age) { }
//
// Java AUTO-GENERATES all of these:
//   ✅ Constructor
//   ✅ Getters  → name(), age()  (NOT getName(), getAge()!)
//   ✅ equals()
//   ✅ hashCode()
//   ✅ toString()


// ═══════════════════════════════════════════════════════
// WHY INTRODUCED?
// ═══════════════════════════════════════════════════════
//
// Before records — just 2 fields needed ~35 lines:
//
//   class Person {
//       private final String name;
//       private final int age;
//       public Person(String name, int age) { this.name=name; this.age=age; }
//       public String getName() { return name; }
//       public int getAge()     { return age; }
//       @Override public boolean equals(Object o) { ... }
//       @Override public int hashCode() { ... }
//       @Override public String toString() { ... }
//   }
//
// Problems with old way:
//   ❌ Too much boilerplate code
//   ❌ Easy to forget equals/hashCode → bugs
//   ❌ Hard to read — data buried in ceremony
//   ❌ Every new field = update constructor + getter + equals + hashCode
//
// Record fixes all of this in 1 line ✅


// ═══════════════════════════════════════════════════════
// KEY RULES OF RECORDS
// ═══════════════════════════════════════════════════════
//
// 1. Fields are FINAL (immutable) — cannot change after creation
// 2. Cannot EXTEND another class (records already extend java.lang.Record)
// 3. CAN implement interfaces
// 4. CAN have static fields and static methods
// 5. CAN have instance methods
// 6. CANNOT add instance fields outside the record header
//
// GOLDEN RULE:
//   Need a simple immutable data holder? → Use Record ✅
//   Need mutability or inheritance?      → Use Class  ✅


// ═══════════════════════════════════════════════════════
// RECORD vs CLASS vs LOMBOK
// ═══════════════════════════════════════════════════════
//
//                    Record     Plain Class    Lombok @Data
//  Boilerplate        None       Lots           None
//  Immutable?         YES        Optional       Optional
//  Auto equals/hash   YES        Manual         YES
//  Auto toString      YES        Manual         YES
//  Extend class?      NO         YES            YES
//  Extra fields?      NO         YES            YES
//  Java version       16+        Any            Any (library)


// ═══════════════════════════════════════════════════════
// RECORDS DEFINED (used in examples below)
// ═══════════════════════════════════════════════════════

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

// Basic record — 1 line, all boilerplate auto-generated
record Person(String name, int age) { }

// Record with validation using compact constructor
record Product(String name, double price) {
    // Compact constructor — no parameter list, just { }
    // Runs BEFORE fields are assigned
    public Product {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative!");
        name = name.trim();   // transform before storing
    }
}

// Record with instance method and static method
record Circle(double radius) {

    // Instance method — uses record fields
    double area() {
        return Math.PI * radius * radius;   // can use radius directly
    }

    double perimeter() {
        return 2 * Math.PI * radius;
    }

    // Static method — factory method
    static Circle unit() {
        return new Circle(1.0);   // creates a unit circle
    }
}

// Record implementing an interface
record Point(int x, int y) implements Serializable {

    // Custom method — distance from origin
    double distanceFromOrigin() {
        return Math.sqrt(x * x + y * y);
    }
}

// Record as DTO (Data Transfer Object) — common real-world use
record UserDTO(String username, String email, String role) { }

// Record as API response
record ApiResponse(int status, String message, Object data) { }


// ═══════════════════════════════════════════════════════
// MAIN — ALL EXAMPLES RUN HERE
// ═══════════════════════════════════════════════════════

public class RecordNotes {

    public static void main(String[] args) {

        // ───────────────────────────────────────────────
        // EXAMPLE 1 — Basic Record Usage
        // ───────────────────────────────────────────────
        // NOTES:
        //   → Create with new just like a class
        //   → Access fields with name() age() — NO "get" prefix!
        //   → toString() auto-formatted: Person[name=Alice, age=30]

        System.out.println("=== EXAMPLE 1: Basic Usage ===");

        Person p = new Person("Alice", 30);

        System.out.println(p.name());       // Alice       ← getter, no "get" prefix!
        System.out.println(p.age());        // 30
        System.out.println(p);             // Person[name=Alice, age=30]  ← auto toString


        // ───────────────────────────────────────────────
        // EXAMPLE 2 — equals() and hashCode() Auto-Generated
        // ───────────────────────────────────────────────
        // NOTES:
        //   → equals() compares by VALUE (field content), not by reference
        //   → Two different objects with same data = equal ✅
        //   → Regular class without override would return false here!

        System.out.println("\n=== EXAMPLE 2: equals() and hashCode() ===");

        Person p1 = new Person("Alice", 30);
        Person p2 = new Person("Alice", 30);
        Person p3 = new Person("Bob",   25);

        System.out.println(p1.equals(p2));          // true  ← same data = equal ✅
        System.out.println(p1.equals(p3));          // false ← different data
        System.out.println(p1 == p2);               // false ← different objects in memory
        System.out.println(p1.hashCode() == p2.hashCode()); // true ← same hash ✅


        // ───────────────────────────────────────────────
        // EXAMPLE 3 — Immutability (Fields are Final)
        // ───────────────────────────────────────────────
        // NOTES:
        //   → Fields are final — cannot change after creation
        //   → No setters generated — records are read-only by design
        //   → This makes records safe to share across threads!

        System.out.println("\n=== EXAMPLE 3: Immutability ===");

        Person alice = new Person("Alice", 30);
        System.out.println("Name: " + alice.name());   // Alice

        // alice.name = "Bob";   // ❌ compile error — field is final!
        // alice.setName("Bob"); // ❌ no setter exists — records don't have setters!

        // To "update" — create a new record with new values
        Person olderAlice = new Person(alice.name(), alice.age() + 1);  // new object ✅
        System.out.println("Updated age: " + olderAlice.age());   // 31


        // ───────────────────────────────────────────────
        // EXAMPLE 4 — Compact Constructor (Validation)
        // ───────────────────────────────────────────────
        // NOTES:
        //   → Compact constructor has NO parameter list — just { }
        //   → Runs before fields are stored
        //   → Use to: validate input, normalize/transform data

        System.out.println("\n=== EXAMPLE 4: Compact Constructor ===");

        Product laptop = new Product("  MacBook  ", 1200.0);
        System.out.println(laptop.name());    // "MacBook"  ← trimmed automatically!
        System.out.println(laptop.price());   // 1200.0

        try {
            Product bad = new Product("Mouse", -50.0);   // ❌ negative price!
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
            // Output: Caught: Price cannot be negative!
        }


        // ───────────────────────────────────────────────
        // EXAMPLE 5 — Instance Methods and Static Methods
        // ───────────────────────────────────────────────
        // NOTES:
        //   → Records CAN have instance methods that use their fields
        //   → Records CAN have static methods (factory methods etc.)
        //   → Keeps related logic close to the data

        System.out.println("\n=== EXAMPLE 5: Instance and Static Methods ===");

        Circle c = new Circle(5.0);
        System.out.println("Radius:    " + c.radius());          // 5.0
        System.out.println("Area:      " + c.area());            // 78.53...
        System.out.println("Perimeter: " + c.perimeter());       // 31.41...

        Circle unit = Circle.unit();   // static factory method
        System.out.println("Unit circle radius: " + unit.radius());  // 1.0


        // ───────────────────────────────────────────────
        // EXAMPLE 6 — Record Implementing Interface
        // ───────────────────────────────────────────────
        // NOTES:
        //   → Records CAN implement interfaces ✅
        //   → Records CANNOT extend another class ❌
        //   → Point implements Serializable here

        System.out.println("\n=== EXAMPLE 6: Implementing Interface ===");

        Point point = new Point(3, 4);
        System.out.println("x: " + point.x());                      // 3
        System.out.println("y: " + point.y());                      // 4
        System.out.println("Distance from origin: " + point.distanceFromOrigin()); // 5.0


        // ───────────────────────────────────────────────
        // EXAMPLE 7 — Record as Map Key
        // ───────────────────────────────────────────────
        // NOTES:
        //   → Records are SAFE as Map keys because equals+hashCode are auto-generated
        //   → Regular class without equals/hashCode override = unsafe as map key!
        //   → Common use: coordinates, composite keys

        System.out.println("\n=== EXAMPLE 7: Record as Map Key ===");

        Map<Point, String> cityMap = new HashMap<>();
        cityMap.put(new Point(28, 77), "Delhi");
        cityMap.put(new Point(19, 72), "Mumbai");
        cityMap.put(new Point(13, 80), "Chennai");

        // Lookup with a NEW object — works because equals() compares by value!
        System.out.println(cityMap.get(new Point(28, 77)));   // Delhi ✅
        System.out.println(cityMap.get(new Point(19, 72)));   // Mumbai ✅


        // ───────────────────────────────────────────────
        // EXAMPLE 8 — Records with Streams (Common Real-World Use)
        // ───────────────────────────────────────────────
        // NOTES:
        //   → Records work perfectly with Streams
        //   → Concise and readable — no verbose getters
        //   → Very common in modern Java backends

        System.out.println("\n=== EXAMPLE 8: Records with Streams ===");

        List<Person> people = List.of(
                new Person("Alice",   30),
                new Person("Bob",     25),
                new Person("Charlie", 35),
                new Person("David",   28)
        );

        // Filter people over 27, get their names, sorted
        List<String> result = people.stream()
                .filter(p4 -> p4.age() > 27)         // p.age() instead of p.getAge()
                .map(Person::name)                    // method reference — Person::name
                .sorted()
                .collect(Collectors.toList());

        System.out.println("People over 27: " + result);
        // Output: [Alice, Charlie, David]


        // ───────────────────────────────────────────────
        // EXAMPLE 9 — DTO and API Response Use Case
        // ───────────────────────────────────────────────
        // NOTES:
        //   → Most common real-world use of records
        //   → DTOs carry data between layers (controller ↔ service ↔ DB)
        //   → API responses wrap data cleanly
        //   → Replaces verbose POJO classes entirely

        System.out.println("\n=== EXAMPLE 9: DTO and API Response ===");

        // DTO — carries user data from DB to API layer
        UserDTO user = new UserDTO("alice123", "alice@email.com", "ADMIN");
        System.out.println(user);
        // Output: UserDTO[username=alice123, email=alice@email.com, role=ADMIN]

        // API Response — wraps any response
        ApiResponse success = new ApiResponse(200, "User found", user);
        ApiResponse error   = new ApiResponse(404, "User not found", null);

        System.out.println("Status:  " + success.status());   // 200
        System.out.println("Message: " + success.message());  // User found
        System.out.println("Data:    " + success.data());     // UserDTO[...]

        System.out.println("Error status: " + error.status()); // 404


        // ───────────────────────────────────────────────
        // EXAMPLE 10 — Pattern Matching with Records (Java 21+)
        // ───────────────────────────────────────────────
        // NOTES:
        //   → Java 21 introduced pattern matching for switch
        //   → Records work perfectly with this feature
        //   → Can destructure record fields directly in switch

        System.out.println("\n=== EXAMPLE 10: Pattern Matching (Java 21+) ===");

        Object shape = new Circle(7.0);

        // Pattern matching switch — checks type AND extracts fields at once
        String description = switch (shape) {
            case Circle c2  -> "Circle with radius " + c2.radius()
                    + " and area " + String.format("%.2f", c2.area());
            case Point pt  -> "Point at (" + pt.x() + ", " + pt.y() + ")";
            case Person per -> "Person: " + per.name();
            default        -> "Unknown shape";
        };

        System.out.println(description);
        // Output: Circle with radius 7.0 and area 153.94


        System.out.println("\n=== ALL EXAMPLES DONE ✅ ===");
    }
}


// ═══════════════════════════════════════════════════════
// QUICK CHEAT SHEET
// ═══════════════════════════════════════════════════════
//
//  Define:     public record Person(String name, int age) { }
//  Create:     Person p = new Person("Alice", 30);
//  Access:     p.name()  p.age()          ← no "get" prefix!
//  Print:      p.toString()               → Person[name=Alice, age=30]
//  Compare:    p.equals(p2)               → compares by VALUE ✅
//
//  AUTO-GENERATED:  constructor, getters, equals, hashCode, toString
//  IMMUTABLE:       fields are final — cannot be changed after creation
//  CANNOT:          extend a class, add instance fields outside header
//  CAN:             implement interface, add methods, add static fields
//
//  VALIDATION:      use compact constructor → public Person { if(...) throw... }
//  UPDATE:          create new record  → new Person(old.name(), newAge)
//  MAP KEY:         safe to use — equals+hashCode auto-generated ✅
//  STREAMS:         use p.name() instead of p.getName()
//
// ═══════════════════════════════════════════════════════
// END
// ═══════════════════════════════════════════════════════
