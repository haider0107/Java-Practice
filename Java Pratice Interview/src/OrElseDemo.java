import java.util.Optional;

public class OrElseDemo {

    static String computeDefault() {
        System.out.println("  >>> computeDefault() called!");
        return "DefaultUser";
    }

    public static void main(String[] args) {

        // ── CASE 1: Optional has a value ──────────────────────
        System.out.println("=== Optional HAS a value ===");
        Optional<String> present = Optional.of("Alice");

        System.out.println("\n-- orElse() --");
        String r1 = present.orElse(computeDefault());
        System.out.println("Result: " + r1);
        // computeDefault() IS called (wasteful!)

        System.out.println("\n-- orElseGet() --");
        String r2 = present.orElseGet(() -> computeDefault());
        System.out.println("Result: " + r2);
        // computeDefault() is NOT called (efficient!)


        // ── CASE 2: Optional is empty ─────────────────────────
        System.out.println("\n=== Optional is EMPTY ===");
        Optional<String> empty = Optional.empty();

        System.out.println("\n-- orElse() --");
        String r3 = empty.orElse(computeDefault());
        System.out.println("Result: " + r3);
        // computeDefault() called — needed

        System.out.println("\n-- orElseGet() --");
        String r4 = empty.orElseGet(() -> computeDefault());
        System.out.println("Result: " + r4);
        // computeDefault() called — needed


        // ── CASE 3: Simple value — both same ──────────────────
        System.out.println("\n=== Simple default value ===");
        Optional<String> opt = Optional.of("Bob");

        String r5 = opt.orElse("Unknown");                  // fine
        String r6 = opt.orElseGet(() -> "Unknown");         // fine
        System.out.println(r5 + " / " + r6);   // Bob / Bob
    }
}