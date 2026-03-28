import java.util.*;
import java.util.stream.*;

public class StreamExamples {

    // =========================================================================
    // Employee — a simple model class used in real-world examples
    // Fields: name, dept (department), salary
    // =========================================================================
    static class Employee {
        String name;
        String dept;
        int salary;

        Employee(String name, String dept, int salary) {
            this.name = name;
            this.dept = dept;
            this.salary = salary;
        }

        public String getName()  { return name; }
        public String getDept()  { return dept; }
        public int getSalary()   { return salary; }

        @Override
        public String toString() {
            return name + " (" + dept + ", " + salary + ")";
        }
    }

    // =========================================================================
    // Order — used in flatMap real-world example
    // One customer can have MANY items → classic 1-to-many → use flatMap
    // =========================================================================
    static class Order {
        String customer;
        List<String> items;

        Order(String customer, List<String> items) {
            this.customer = customer;
            this.items = items;
        }
    }

    public static void main(String[] args) {

        // ---------------------------------------------------------------------
        // SAMPLE DATA — used throughout all sections
        // ---------------------------------------------------------------------
        List<Integer>  numbers   = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String>   names     = Arrays.asList("Alice", "Bob", "Charlie", "David", "Alice"); // Alice is duplicate
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "IT", 70000),
                new Employee("Bob",   "HR", 40000),
                new Employee("Carol", "IT", 90000),
                new Employee("David", "HR", 55000),
                new Employee("Eve",   "IT", 60000)
        );


        // =====================================================================
        // SECTION 1: LAZY EVALUATION DEMO
        // =====================================================================
        // KEY CONCEPT:
        //   - Intermediate operations (filter, map, etc.) are LAZY
        //   - They do NOT execute when you write them
        //   - They only execute when a TERMINAL operation (collect, count, etc.) is called
        //   - This saves memory and CPU — only processes what is needed
        // =====================================================================
        System.out.println("=================================================");
        System.out.println("  SECTION 1: LAZY EVALUATION DEMO");
        System.out.println("=================================================");

        System.out.println("\n-- Creating stream with filter & map (nothing runs yet) --");

        // NOTICE: Even though we define filter() and map() here,
        // the println inside them will NOT print yet.
        // The stream is just "planned" — not executed.
        Stream<Integer> lazyStream = numbers.stream()
                .filter(n -> {
                    System.out.println("  filter called for: " + n);  // won't print yet!
                    return n % 2 == 0;
                })
                .map(n -> {
                    System.out.println("  map called for: " + n);     // won't print yet!
                    return n * 10;
                });

        System.out.println("  >> Stream defined. No output above? That's LAZY! <<");
        System.out.println("\n-- Now calling terminal operation collect() --");

        // NOW the stream executes — collect() is the trigger
        // Watch how filter and map prints start appearing only now
        List<Integer> lazyResult = lazyStream.collect(Collectors.toList());
        System.out.println("  Result: " + lazyResult);
        // Output: [20, 40, 60, 80, 100]


        // =====================================================================
        // SECTION 2: INTERMEDIATE OPERATIONS
        // =====================================================================
        // KEY CONCEPT:
        //   - Intermediate operations RETURN a new Stream
        //   - So you can CHAIN them one after another
        //   - They are all LAZY (won't execute until terminal is called)
        //   - You can use as many as you want in a pipeline
        // =====================================================================
        System.out.println("\n=================================================");
        System.out.println("  SECTION 2: INTERMEDIATE OPERATIONS");
        System.out.println("=================================================");


        // ----- filter() -----
        // PURPOSE : Keep only elements that match a condition (predicate)
        // INPUT   : Predicate<T>  → a function that returns true/false
        // OUTPUT  : Stream<T>     → only matching elements pass through
        // USE WHEN: You want to remove unwanted elements from the stream
        System.out.println("\n--- filter(): Keep numbers greater than 5 ---");
        List<Integer> filtered = numbers.stream()
                .filter(n -> n > 5)            // keep only n where n > 5
                .collect(Collectors.toList());
        System.out.println("  Input : " + numbers);
        System.out.println("  Output: " + filtered);
        // Output: [6, 7, 8, 9, 10]


        // ----- map() -----
        // PURPOSE : Transform each element into something else (1-to-1)
        // INPUT   : Function<T, R>  → takes one element, returns one result
        // OUTPUT  : Stream<R>       → stream of transformed elements
        // USE WHEN: You want to convert/transform each element
        // NOTE    : Result type can be different from input type
        System.out.println("\n--- map(): Multiply each number by 2 ---");
        List<Integer> mapped = numbers.stream()
                .map(n -> n * 2)               // each number → number * 2
                .collect(Collectors.toList());
        System.out.println("  Input : " + numbers);
        System.out.println("  Output: " + mapped);
        // Output: [2, 4, 6, 8, 10, 12, 14, 16, 18, 20]

        // map() works on Strings too — String::toUpperCase is a method reference
        System.out.println("\n--- map(): Convert names to UPPERCASE ---");
        List<String> upperNames = names.stream()
                .map(String::toUpperCase)      // "alice" → "ALICE" (method reference)
                .collect(Collectors.toList());
        System.out.println("  Input : " + names);
        System.out.println("  Output: " + upperNames);
        // Output: [ALICE, BOB, CHARLIE, DAVID, ALICE]


        // ----- flatMap() -----
        // PURPOSE : Transform each element into a Stream, then FLATTEN all into one Stream
        // INPUT   : Function<T, Stream<R>>  → each element produces a stream
        // OUTPUT  : Stream<R>               → all streams merged into one flat stream
        // USE WHEN: Each element maps to multiple results (1-to-many)
        // KEY DIFF FROM map():
        //   map()     → Stream<Stream<T>>  (stays nested)
        //   flatMap() → Stream<T>          (flattened into one level)
        System.out.println("\n--- flatMap(): Flatten nested lists ---");
        List<List<Integer>> nested = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );
        // map()     would give: [[1,2,3], [4,5,6], [7,8,9]]  ← still nested
        // flatMap() gives:      [1, 2, 3, 4, 5, 6, 7, 8, 9] ← flat!
        List<Integer> flattened = nested.stream()
                .flatMap(Collection::stream)   // each inner list → stream → all merged
                .collect(Collectors.toList());
        System.out.println("  Input : " + nested);
        System.out.println("  Output: " + flattened);
        // Output: [1, 2, 3, 4, 5, 6, 7, 8, 9]


        // ----- distinct() -----
        // PURPOSE : Remove duplicate elements
        // OUTPUT  : Stream<T> with only unique elements
        // NOTE    : Uses equals() to check duplicates
        // USE WHEN: You have repeated values and want only unique ones
        System.out.println("\n--- distinct(): Remove duplicates ---");
        // "Alice" appears twice in the names list
        List<String> distinctNames = names.stream()
                .distinct()                    // removes the second "Alice"
                .collect(Collectors.toList());
        System.out.println("  Input : " + names);
        System.out.println("  Output: " + distinctNames);
        // Output: [Alice, Bob, Charlie, David]


        // ----- sorted() -----
        // PURPOSE : Sort elements in natural order or by a custom comparator
        // OUTPUT  : Stream<T> in sorted order
        // USE WHEN: You need results in a specific order
        System.out.println("\n--- sorted(): Natural order (A-Z) ---");
        List<String> sortedNames = names.stream()
                .distinct()
                .sorted()                      // alphabetical A → Z
                .collect(Collectors.toList());
        System.out.println("  Input : " + names);
        System.out.println("  Output: " + sortedNames);
        // Output: [Alice, Bob, Charlie, David]

        System.out.println("\n--- sorted(): Reverse order (Z-A / 10→1) ---");
        List<Integer> sortedDesc = numbers.stream()
                .sorted(Comparator.reverseOrder())    // descending order
                .collect(Collectors.toList());
        System.out.println("  Input : " + numbers);
        System.out.println("  Output: " + sortedDesc);
        // Output: [10, 9, 8, 7, 6, 5, 4, 3, 2, 1]


        // ----- limit(n) -----
        // PURPOSE : Take only the FIRST n elements, discard the rest
        // OUTPUT  : Stream<T> with at most n elements
        // USE WHEN: You want top-N results, or to avoid processing all data
        // TIP     : Very useful with sorted() to get "top 3 earners" etc.
        System.out.println("\n--- limit(): Take first 3 elements ---");
        List<Integer> limited = numbers.stream()
                .limit(3)                      // take only: 1, 2, 3
                .collect(Collectors.toList());
        System.out.println("  Input : " + numbers);
        System.out.println("  Output: " + limited);
        // Output: [1, 2, 3]


        // ----- skip(n) -----
        // PURPOSE : Skip the FIRST n elements, keep the rest
        // OUTPUT  : Stream<T> without the first n elements
        // USE WHEN: Pagination (skip page 1 items, show page 2)
        // TIP     : skip() + limit() together = pagination pattern!
        //           Example: skip(10).limit(10) = page 2 (items 11-20)
        System.out.println("\n--- skip(): Skip first 7 elements ---");
        List<Integer> skipped = numbers.stream()
                .skip(7)                       // skip 1,2,3,4,5,6,7 → keep 8,9,10
                .collect(Collectors.toList());
        System.out.println("  Input : " + numbers);
        System.out.println("  Output: " + skipped);
        // Output: [8, 9, 10]


        // ----- peek() -----
        // PURPOSE : Look at elements mid-pipeline WITHOUT changing them
        // OUTPUT  : Stream<T> (same as input — peek is non-destructive)
        // USE WHEN: Debugging — you want to print/log values at each pipeline step
        // WARNING : Don't use peek() for actual logic — only for debug/logging
        System.out.println("\n--- peek(): Debug values mid-pipeline ---");
        List<Integer> peeked = numbers.stream()
                .filter(n -> n % 2 == 0)
                .peek(n -> System.out.println("  After filter, before map: " + n))   // debug
                .map(n -> n * 10)
                .peek(n -> System.out.println("  After map: " + n))                  // debug
                .collect(Collectors.toList());
        System.out.println("  Final result: " + peeked);
        // Output shows values at each stage, final result: [20, 40, 60, 80, 100]


        // =====================================================================
        // SECTION 3: TERMINAL OPERATIONS
        // =====================================================================
        // KEY CONCEPT:
        //   - Terminal operations TRIGGER the entire stream pipeline to execute
        //   - They return a REAL RESULT (List, int, boolean, void, etc.)
        //   - After a terminal operation, the stream is CLOSED — cannot reuse it
        //   - There can only be ONE terminal operation per stream
        // =====================================================================
        System.out.println("\n=================================================");
        System.out.println("  SECTION 3: TERMINAL OPERATIONS");
        System.out.println("=================================================");


        // ----- collect() → toList() -----
        // PURPOSE : Gather stream elements into a List
        // RETURNS : List<T>
        // USE WHEN: You want the processed result as an ArrayList
        System.out.println("\n--- collect(): To List ---");
        List<Integer> collectedList = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());      // gather into ArrayList
        System.out.println("  Even numbers list: " + collectedList);
        // Output: [2, 4, 6, 8, 10]


        // ----- collect() → toSet() -----
        // PURPOSE : Gather stream elements into a Set (duplicates auto-removed)
        // RETURNS : Set<T>
        // NOTE    : Order is NOT guaranteed in a Set
        // USE WHEN: You want unique elements collected
        System.out.println("\n--- collect(): To Set (removes duplicates) ---");
        Set<String> collectedSet = names.stream()
                .collect(Collectors.toSet());       // duplicate "Alice" auto-removed by Set
        System.out.println("  Names set: " + collectedSet);
        // Output: [Bob, Alice, Charlie, David]  (order may vary)


        // ----- collect() → joining() -----
        // PURPOSE : Concatenate String elements into one String with a separator
        // RETURNS : String
        // USE WHEN: You want to join strings like CSV, or display as a sentence
        System.out.println("\n--- collect(): Joining strings ---");
        String joined = names.stream()
                .distinct()
                .collect(Collectors.joining(", "));  // join with ", " separator
        System.out.println("  Joined: " + joined);
        // Output: Alice, Bob, Charlie, David


        // ----- collect() → toMap() -----
        // PURPOSE : Collect into a Map using two functions:
        //           1st function → key extractor
        //           2nd function → value extractor
        // RETURNS : Map<K, V>
        // USE WHEN: You want to look up elements by a key (like a dictionary)
        System.out.println("\n--- collect(): To Map (name -> salary) ---");
        Map<String, Integer> empMap = employees.stream()
                .collect(Collectors.toMap(
                        Employee::getName,     // key   = employee name
                        Employee::getSalary    // value = employee salary
                ));
        System.out.println("  Employee Map: " + empMap);
        // Output: {Alice=70000, Bob=40000, Carol=90000, David=55000, Eve=60000}


        // ----- forEach() -----
        // PURPOSE : Perform an action on each element (like an enhanced for loop)
        // RETURNS : void (nothing)
        // USE WHEN: You want to print, log, or perform a side-effect per element
        // NOTE    : Since it returns void, you CANNOT chain anything after forEach
        System.out.println("\n--- forEach(): Print each odd number ---");
        System.out.print("  ");
        numbers.stream()
                .filter(n -> n % 2 != 0)
                .forEach(n -> System.out.print(n + " "));   // prints each odd number
        System.out.println();
        // Output: 1 3 5 7 9


        // ----- count() -----
        // PURPOSE : Count the number of elements remaining in the stream
        // RETURNS : long
        // USE WHEN: You want to know how many elements match a condition
        System.out.println("\n--- count(): Count elements ---");
        long count = numbers.stream()
                .filter(n -> n > 5)
                .count();                     // count how many passed the filter
        System.out.println("  Numbers greater than 5: " + count);
        // Output: 5   (6, 7, 8, 9, 10)


        // ----- reduce() -----
        // PURPOSE : Combine ALL elements into a SINGLE value
        //           reduce(identity, accumulator)
        //           identity    = starting value (0 for sum, 1 for product)
        //           accumulator = how to combine two values step by step
        // RETURNS : T (same type as stream elements)
        // USE WHEN: Sum, product, string concatenation, or any aggregation
        System.out.println("\n--- reduce(): Sum all numbers ---");
        int sum = numbers.stream()
                .reduce(0, Integer::sum);     // 0 + 1 + 2 + 3 + ... + 10
        System.out.println("  Sum of " + numbers + " = " + sum);
        // Output: 55

        System.out.println("\n--- reduce(): Multiply first 5 numbers ---");
        int product = numbers.stream()
                .limit(5)
                .reduce(1, (a, b) -> a * b);  // 1 × 1 × 2 × 3 × 4 × 5
        System.out.println("  Product of first 5 numbers = " + product);
        // Output: 120


        // ----- min() and max() -----
        // PURPOSE : Find the smallest or largest element using a comparator
        // INPUT   : Comparator<T>  → how to compare elements
        // RETURNS : Optional<T>    → wrapped in Optional because stream could be empty
        // USE WHEN: You want the extreme (min or max) value
        System.out.println("\n--- min() and max() ---");
        Optional<Integer> min = numbers.stream().min(Comparator.naturalOrder());
        Optional<Integer> max = numbers.stream().max(Comparator.naturalOrder());
        System.out.println("  Min: " + min.get());   // Output: 1
        System.out.println("  Max: " + max.get());   // Output: 10


        // ----- findFirst() -----
        // PURPOSE : Get the FIRST element that passes through the pipeline
        // RETURNS : Optional<T> — because there might be NO matching element
        // USE WHEN: You just need one element (e.g., first matching record from DB)
        // TIP     : Use findAny() in parallel streams (faster, no ordering guarantee)
        System.out.println("\n--- findFirst(): First even number greater than 5 ---");
        Optional<Integer> first = numbers.stream()
                .filter(n -> n > 5 && n % 2 == 0)
                .findFirst();
        System.out.println("  Result: " + first.get());
        // Output: 6


        // ----- anyMatch() -----
        // PURPOSE : Check if AT LEAST ONE element matches the condition
        // RETURNS : boolean
        // NOTE    : Short-circuits — stops scanning as soon as one match is found (fast!)
        // USE WHEN: You want to know if ANY element satisfies a condition
        System.out.println("\n--- anyMatch(): Is there any number > 9? ---");
        boolean anyMatch = numbers.stream().anyMatch(n -> n > 9);
        System.out.println("  anyMatch(n > 9): " + anyMatch);
        // Output: true  (10 is > 9)


        // ----- allMatch() -----
        // PURPOSE : Check if ALL elements match the condition
        // RETURNS : boolean
        // NOTE    : Short-circuits — stops as soon as one NON-match is found
        // USE WHEN: You want to validate that every element satisfies a rule
        System.out.println("\n--- allMatch(): Are ALL numbers > 0? ---");
        boolean allMatch = numbers.stream().allMatch(n -> n > 0);
        System.out.println("  allMatch(n > 0): " + allMatch);
        // Output: true  (all numbers 1–10 are positive)


        // ----- noneMatch() -----
        // PURPOSE : Check if NO elements match the condition (opposite of anyMatch)
        // RETURNS : boolean
        // USE WHEN: You want to confirm that no element violates a rule
        System.out.println("\n--- noneMatch(): Are NO numbers negative? ---");
        boolean noneMatch = numbers.stream().noneMatch(n -> n < 0);
        System.out.println("  noneMatch(n < 0): " + noneMatch);
        // Output: true  (no negatives in 1–10)


        // ----- toArray() -----
        // PURPOSE : Convert stream to a plain Java array
        // RETURNS : Object[]
        // USE WHEN: Some APIs require arrays instead of Lists
        System.out.println("\n--- toArray() ---");
        Object[] arr = numbers.stream().filter(n -> n % 2 == 0).toArray();
        System.out.println("  Even numbers array: " + Arrays.toString(arr));
        // Output: [2, 4, 6, 8, 10]


        // =====================================================================
        // SECTION 4: map() vs flatMap() — DETAILED COMPARISON
        // =====================================================================
        // KEY CONCEPT:
        //   map()     → 1-to-1   | Each element produces EXACTLY ONE result
        //                          Result can be nested (Stream<Stream<T>>)
        //   flatMap() → 1-to-many | Each element produces ZERO or MORE results
        //                           All results merged into ONE flat Stream<T>
        //
        // SIMPLE RULE:
        //   If mapping function returns a plain value  (String, Integer...) → map()
        //   If mapping function returns a List/Stream                       → flatMap()
        // =====================================================================
        System.out.println("\n=================================================");
        System.out.println("  SECTION 4: map() vs flatMap()");
        System.out.println("=================================================");


        // --- Example 1: Nested List → flat list ---
        System.out.println("\n--- Example 1: Nested List ---");
        List<List<Integer>> nestedList = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );

        // map() — each inner list stays as a list → result is nested List<List<Integer>>
        List<List<Integer>> mapResult = nestedList.stream()
                .map(list -> list)             // 1-to-1: each list → same list
                .collect(Collectors.toList());
        System.out.println("  map()     result: " + mapResult);
        // Output: [[1, 2, 3], [4, 5, 6], [7, 8, 9]]  ← still nested!

        // flatMap() — each inner list opens up → all elements merge into one flat list
        List<Integer> flatMapResult = nestedList.stream()
                .flatMap(list -> list.stream())  // 1-to-many: each list → its elements
                .collect(Collectors.toList());
        System.out.println("  flatMap() result: " + flatMapResult);
        // Output: [1, 2, 3, 4, 5, 6, 7, 8, 9]  ← flat!


        // --- Example 2: Sentences → Words (Most Asked Interview Example!) ---
        // When you split a sentence, you get String[] (array) → 1-to-many → flatMap
        System.out.println("\n--- Example 2: Split sentences into words ---");
        List<String> sentences = Arrays.asList(
                "Hello World",
                "Java Streams",
                "map and flatMap"
        );

        // map() + split → gives Stream<String[]> → each element is an array (nested)
        System.out.println("  map() gives nested String arrays:");
        sentences.stream()
                .map(s -> s.split(" "))        // each sentence → String[] (array of words)
                .forEach(wordArr -> System.out.println("    " + Arrays.toString(wordArr)));
        // Output: [Hello, World]   [Java, Streams]   [map, and, flatMap]  ← nested arrays

        // flatMap() + split → splits AND flattens → gives clean flat list of words
        System.out.println("  flatMap() gives flat list of words:");
        List<String> words = sentences.stream()
                .flatMap(s -> Arrays.stream(s.split(" ")))   // split → stream → flatten
                .collect(Collectors.toList());
        System.out.println("  " + words);
        // Output: [Hello, World, Java, Streams, map, and, flatMap]


        // --- Example 3: Orders → All Items (Classic 1-to-many Real World) ---
        // One Order has MANY items → to get all items flat → use flatMap
        System.out.println("\n--- Example 3: Orders → All Items (1-to-many) ---");
        List<Order> orders = Arrays.asList(
                new Order("Alice", Arrays.asList("Laptop", "Mouse")),
                new Order("Bob",   Arrays.asList("Phone", "Charger", "Cover")),
                new Order("Carol", Arrays.asList("Tablet"))
        );

        // map() — each order → its item list → result is List<List<String>>
        System.out.println("  map() gives nested lists of items:");
        orders.stream()
                .map(order -> order.items)         // each order → List<String>
                .forEach(itemList -> System.out.println("    " + itemList));
        // Output: [Laptop, Mouse]   [Phone, Charger, Cover]   [Tablet]  ← nested

        // flatMap() — each order → stream of its items → all merged into one flat list
        System.out.println("  flatMap() gives all items in one flat list:");
        List<String> allItems = orders.stream()
                .flatMap(order -> order.items.stream())   // each order → items stream → merge
                .collect(Collectors.toList());
        System.out.println("  All items: " + allItems);
        // Output: [Laptop, Mouse, Phone, Charger, Cover, Tablet]

        // Now you can easily chain more operations on the flat result!
        System.out.println("  Unique items sorted alphabetically:");
        List<String> uniqueSortedItems = orders.stream()
                .flatMap(order -> order.items.stream())   // flatten
                .distinct()                               // remove duplicates
                .sorted()                                 // sort A-Z
                .collect(Collectors.toList());
        System.out.println("  " + uniqueSortedItems);
        // Output: [Charger, Cover, Laptop, Mouse, Phone, Tablet]


        // --- map() vs flatMap() Quick Summary ---
        System.out.println("\n--- map() vs flatMap() Summary ---");
        System.out.println("  map()     : 1-to-1   | keeps structure  | for simple transforms");
        System.out.println("  flatMap() : 1-to-many | removes nesting  | when result is List/Stream");


        // =====================================================================
        // SECTION 5: REAL WORLD EXAMPLE — EMPLOYEES
        // =====================================================================
        // Combines multiple intermediate + terminal operations together
        // This is the kind of code you would write in real backend projects
        // =====================================================================
        System.out.println("\n=================================================");
        System.out.println("  SECTION 5: REAL WORLD EMPLOYEE EXAMPLE");
        System.out.println("=================================================");

        System.out.println("\nAll Employees: " + employees);

        // --- Chain: filter + filter + map + sorted + collect ---
        // Find names of IT employees earning > 60000, sorted A-Z
        System.out.println("\n--- IT employees earning > 60000, sorted by name ---");
        List<String> itHighEarners = employees.stream()
                .filter(e -> e.getDept().equals("IT"))     // step 1: keep only IT dept
                .filter(e -> e.getSalary() > 60000)        // step 2: keep salary > 60k
                .map(Employee::getName)                     // step 3: extract name (String)
                .sorted()                                   // step 4: sort A-Z
                .collect(Collectors.toList());              // terminal: gather to list
        System.out.println("  Result: " + itHighEarners);
        // Output: [Alice, Carol]


        // --- map() + reduce() for total salary ---
        System.out.println("\n--- Total salary of all employees ---");
        int totalSalary = employees.stream()
                .map(Employee::getSalary)      // extract salary from each employee (Integer)
                .reduce(0, Integer::sum);      // sum all salaries, starting from 0
        System.out.println("  Total: " + totalSalary);
        // Output: 315000


        // --- mapToInt() + average() ---
        // mapToInt() converts Stream<Employee> → IntStream (primitive int stream)
        // IntStream has special numeric methods: average(), sum(), min(), max()
        // These are not available on regular Stream<Integer>
        System.out.println("\n--- Average salary ---");
        OptionalDouble avgSalary = employees.stream()
                .mapToInt(Employee::getSalary)   // Stream<Employee> → IntStream
                .average();                      // IntStream built-in average()
        System.out.println("  Average: " + avgSalary.getAsDouble());
        // Output: 63000.0


        // --- groupingBy() — Group elements into a Map<Key, List<Value>> ---
        // PURPOSE : Split stream into groups based on a key (like SQL GROUP BY)
        // RETURNS : Map<K, List<T>>
        // USE WHEN: You want to categorize/group data by a property
        System.out.println("\n--- Group employees by department ---");
        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDept));   // group by dept name
        byDept.forEach((dept, emps) ->
                System.out.println("  " + dept + ": " + emps)
        );
        // Output:
        //   IT: [Alice (IT, 70000), Carol (IT, 90000), Eve (IT, 60000)]
        //   HR: [Bob (HR, 40000), David (HR, 55000)]


        // --- max() with Comparator.comparingInt ---
        // Comparator.comparingInt() creates a comparator based on an int field
        // max() uses it to find the employee with the highest salary
        System.out.println("\n--- Highest paid employee ---");
        Optional<Employee> topEarner = employees.stream()
                .max(Comparator.comparingInt(Employee::getSalary));   // compare by salary
        System.out.println("  Top Earner: " + topEarner.get());
        // Output: Carol (IT, 90000)


        // =====================================================================
        // SECTION 6: STREAM CLOSED AFTER TERMINAL — ERROR DEMO
        // =====================================================================
        // KEY CONCEPT:
        //   - A Stream can only be CONSUMED ONCE
        //   - Once a terminal operation runs, the stream is CLOSED forever
        //   - Calling any operation on a closed stream → IllegalStateException
        //   - SOLUTION: Always call .stream() again to get a fresh stream
        // =====================================================================
        System.out.println("\n=================================================");
        System.out.println("  SECTION 6: STREAM CLOSED AFTER TERMINAL");
        System.out.println("=================================================");

        Stream<Integer> onceStream = numbers.stream().filter(n -> n > 5);

        // First use — works perfectly fine
        System.out.println("\n  First use of stream:");
        System.out.println("  " + onceStream.collect(Collectors.toList()));
        // Output: [6, 7, 8, 9, 10]

        // Second use — stream is already closed after collect() above → CRASH!
        System.out.println("\n  Second use of same stream (will throw error):");
        try {
            onceStream.count();  // ❌ stream already consumed — throws exception!
        } catch (IllegalStateException e) {
            System.out.println("  ❌ Exception: " + e.getMessage());
            System.out.println("  ✅ Fix: call numbers.stream() again to get a fresh stream!");
        }

        // ✅ CORRECT WAY — create a brand new stream each time you need one
        long freshCount = numbers.stream()    // fresh stream — no problem!
                .filter(n -> n > 5)
                .count();
        System.out.println("  ✅ Fresh stream count: " + freshCount);
        // Output: 5


        System.out.println("\n=================================================");
        System.out.println("  ALL EXAMPLES COMPLETED SUCCESSFULLY! 🎉");
        System.out.println("=================================================");
    }
}