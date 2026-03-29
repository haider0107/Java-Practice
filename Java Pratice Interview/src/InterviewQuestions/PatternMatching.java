package InterviewQuestions;

public class PatternMatching {

    public static void main(String[] args) {

        // Object reference can hold any type (String, Integer, etc.)
        // Here it is storing an Integer (autoboxed from int → Integer)
        Object obj = 100;

        // =====================================================
        // 1. TRADITIONAL APPROACH (Before Java 16)
        // =====================================================

        // instanceof only checks type → DOES NOT cast automatically
        if (obj instanceof String) {
            // ❗ Manual casting required
            // Risk: ClassCastException if used incorrectly
            System.out.println("Its a string " + (String) obj);

        } else if (obj instanceof Integer) {
            // ❗ Again, manual casting required
            System.out.println("Its an Integer " + (Integer) obj);
        }

        // 👉 Problems with old approach:
        // - Verbose code
        // - Repeated casting
        // - Less readable
        // - More chances of runtime errors


        // =====================================================
        // 2. MODERN APPROACH: PATTERN MATCHING WITH SWITCH
        // =====================================================

        // Introduced as preview in Java 17, stable in Java 21
        // Combines: type check + casting + variable declaration

        switch (obj) {

            // ✔ Pattern: "String s"
            // - Checks if obj is String
            // - Automatically casts to String
            // - Assigns to variable 's'
            case String s ->
                    System.out.println("String in switch: " + s.toUpperCase());

            // ✔ Pattern: "Integer i"
            // - No explicit cast needed
            // - 'i' is already Integer
            case Integer i ->
                    System.out.println("Integer in switch: " + (i + 1));

            // ✔ Explicit null handling (VERY IMPORTANT)
            // Old switch would throw NullPointerException
            case null ->
                    System.out.println("Its null");

            // ✔ Default case (fallback)
            // Executes when no pattern matches
            default ->
                    System.out.println("Other type");
        }


        // =====================================================
        // 🧠 KEY NOTES / REVISION POINTS
        // =====================================================

        // 1. Pattern Matching = type check + cast + variable
        //    Example: case Integer i

        // 2. No need for manual casting → safer and cleaner

        // 3. Works well with Object type (dynamic behavior)

        // 4. Order matters → more specific cases should come first

        // 5. null must be handled explicitly in switch

        // 6. Improves readability compared to if-else chains

        // 7. Helps avoid ClassCastException

        // =====================================================
        // 🚀 OUTPUT FOR CURRENT CODE
        // =====================================================

        // obj = 100 → Integer
        // So this case executes:
        // case Integer i -> System.out.println("Integer in switch: " + (i + 1));

        // Output:
        // Integer in switch: 101
    }
}
