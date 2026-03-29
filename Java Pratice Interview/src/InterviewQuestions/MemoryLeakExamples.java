package InterviewQuestions;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// =====================================================================
// MEMORY LEAK EXAMPLES IN JAVA
// =====================================================================
// CORE CONCEPT:
//   Garbage Collector (GC) removes objects that have NO references.
//   Memory Leak happens when objects are no longer NEEDED but still
//   have references pointing to them — so GC cannot remove them.
//
//   GC Rule: "If someone holds a reference → I keep the object"
//   Memory Leak: "I hold a reference but I don't actually need it"
// =====================================================================

public class MemoryLeakExamples {

    // =========================================================================
    // MEMORY LEAK 1: STATIC COLLECTION
    // =========================================================================
    // WHY IT LEAKS:
    //   - 'cache' is a STATIC field
    //   - Static fields belong to the CLASS, not to any object
    //   - Static fields live for the ENTIRE lifetime of the JVM
    //   - Every String added via addData() stays in this list FOREVER
    //   - GC sees: "static field holds reference → keep the object"
    //   - GC can NEVER remove these Strings → memory grows endlessly
    //
    // REAL WORLD IMPACT:
    //   - In main(), addData() is called 100,000 times
    //   - 100,000 Strings added → none ever removed
    //   - If each String is ~50 bytes → ~5MB stuck forever
    //   - In production with millions of entries → OutOfMemoryError 💀
    //
    // FIX:
    //   Option 1: cache.clear()  when data is no longer needed
    //   Option 2: Make cache non-static (instance field) so it dies with the object
    //   Option 3: Use WeakHashMap — GC can auto-remove entries when memory is low
    // =========================================================================
    public static List<String> cache = new ArrayList<>();

    public static void addData(String data) {
        cache.add(data);
        // ⚠️ Objects accumulate here and NEVER get released
        // Every call to addData() grows the list
        // No corresponding remove() is ever called
        // Static reference keeps everything alive in memory forever
    }


    // =========================================================================
    // MEMORY LEAK 2: UNCLOSED RESOURCE (FileReader)
    // =========================================================================
    // WHY IT LEAKS:
    //   - FileReader opens an OS-level file handle (native resource)
    //   - Java GC manages HEAP memory (Java objects)
    //   - GC does NOT manage native resources like file handles, DB connections
    //   - If FileReader is never closed → file handle stays open forever
    //   - OS has a LIMIT on open file handles (usually 1024 or 65536)
    //   - If you call readFile() many times → "Too many open files" error 💀
    //
    // STEP BY STEP WHAT HAPPENS:
    //   Step 1: new FileReader("file.txt") → OS opens file handle
    //   Step 2: while loop reads characters
    //   Step 3: IOException occurs (or method ends normally)
    //   Step 4: catch block prints stack trace
    //   Step 5: fileReader goes out of scope
    //   Step 6: GC eventually collects FileReader OBJECT (Java heap)
    //   Step 7: BUT the underlying OS file handle may stay open!
    //           (GC timing is unpredictable — could be minutes/hours later)
    //   Step 8: If readFile() called 1000 times → 1000 open file handles → CRASH
    //
    // FIX:
    //   Option 1 (BEST): try-with-resources — auto closes FileReader
    //     try (FileReader fileReader = new FileReader("file.txt")) { ... }
    //
    //   Option 2: finally block to ensure close()
    //     finally { if (fileReader != null) fileReader.close(); }
    // =========================================================================
    public void readFile() {
        // ⚠️ fileReader opened here but NEVER closed!
        try {
            FileReader fileReader = new FileReader("file.txt");
            // ❌ No close() anywhere — not in try, not in finally!

            int character;
            while ((character = fileReader.read()) != -1) {
                // some operation on each character
                // if exception happens here → fileReader NEVER gets closed!
            }

            // ❌ Even if no exception — close() is still missing here!
            // fileReader.close();   ← this line is absent → resource leak

        } catch (IOException e) {
            e.printStackTrace();
            // ❌ Exception caught but fileReader still not closed!
            // Execution leaves the method with file handle still open
        }

        // fileReader goes out of scope here
        // Java OBJECT will eventually be GC'd (unpredictable timing)
        // But OS file handle may remain open until GC runs — too late!
    }


    public static void main(String[] args) {

        MemoryLeakExamples memoryLeakExamples = new MemoryLeakExamples();

        // =====================================================================
        // DEMONSTRATING LEAK 1: Static Collection growing endlessly
        // =====================================================================
        // WHAT HAPPENS HERE:
        //   - Loop runs 100,000 times
        //   - Each iteration: addData() adds a String to static 'cache' list
        //   - cache is static → lives for entire JVM lifetime
        //   - After loop: 100,000 Strings in memory, none removable by GC
        //   - cache holds strong references → GC cannot touch any of them
        //
        // MEMORY GROWTH:
        //   "Data 0", "Data 1", ... "Data 99999"
        //   Each ~10-15 bytes + String object overhead
        //   Total: several MBs stuck in memory permanently
        // =====================================================================
        for (int i = 0; i < 100000; i++) {
            memoryLeakExamples.addData("Data " + i);
            // ⚠️ 100,000 strings added to static list
            // NONE will ever be garbage collected
            // Static list holds reference to each string forever
        }
        System.out.println("Data added to static list. Memory will not be freed.");
        // At this point: cache.size() = 100,000
        // GC is completely helpless — all have strong static references


        // =====================================================================
        // DEMONSTRATING LEAK 2: Unclosed FileReader
        // =====================================================================
        // readFile() opens FileReader but never closes it
        // File handle leaks — OS resource not returned
        // If called repeatedly → "Too many open files" crash
        // =====================================================================
        memoryLeakExamples.readFile();


        // =====================================================================
        // DEMONSTRATING LEAK 3: Broken equals()/hashCode() in HashSet
        // =====================================================================
        // WHAT HAPPENS HERE:
        //   - Key class has id = "sameId" for all 1000 objects
        //   - You expect Set to deduplicate → size should be 1
        //   - But Set prints size: 1000 (no deduplication happened!)
        //
        // WHY IT LEAKS / MISBEHAVES:
        //   HashSet uses hashCode() + equals() to detect duplicates
        //   If Key class does NOT override hashCode() and equals():
        //     → Java uses DEFAULT hashCode() → based on MEMORY ADDRESS
        //     → Every new Key() has a DIFFERENT memory address
        //     → So every Key() has a DIFFERENT hashCode()
        //     → HashSet thinks all 1000 keys are DIFFERENT → keeps all 1000!
        //     → 1000 objects in Set instead of 1 → memory wasted
        //
        // MEMORY IMPACT:
        //   Intended: 1 Key object in Set
        //   Actual:   1000 Key objects in Set → 999 extra objects leak!
        //   In production: millions of "duplicate" objects fill the heap
        //   GC cannot remove them — Set holds strong references to all!
        //
        // FIX:
        //   Override equals() and hashCode() in Key class based on 'id' field:
        //
        //   @Override
        //   public boolean equals(Object o) {
        //       if (this == o) return true;
        //       if (!(o instanceof Key)) return false;
        //       Key key = (Key) o;
        //       return Objects.equals(id, key.id);
        //   }
        //
        //   @Override
        //   public int hashCode() {
        //       return Objects.hash(id);
        //   }
        //
        //   With fix: Set.size() = 1 ✅ (all 1000 recognized as same key)
        // =====================================================================
        Set<Key> set = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            set.add(new Key("sameId"));
            // ⚠️ All 1000 Key objects have id = "sameId"
            // You expect: Set deduplicates → size = 1
            // Reality:    No equals/hashCode override → size = 1000
            // All treated as different keys → all 1000 objects kept in Set
            // GC cannot remove any — Set holds strong reference to all 1000!
        }

        System.out.println("Set size: " + set.size());
        // ❌ Prints: 1000  (expected 1)
        // All 1000 objects are leaked — Set holds them all
        // Without proper equals/hashCode, HashSet is a memory leak waiting to happen!
    }
}


// =====================================================================
// Key class — intentionally missing equals() and hashCode()
// =====================================================================
// WHY THIS IS PROBLEMATIC:
//   Without overriding equals() and hashCode():
//   - Java uses Object's default implementations
//   - Default hashCode() = based on object memory address (always unique)
//   - Default equals()   = reference equality (a == b, never true for new objects)
//   - So new Key("sameId") != new Key("sameId") in HashSet's eyes
//   - Every new Key is treated as a brand new unique key
//   - HashSet grows to 1000 elements instead of 1
//
// RULE:
//   If you use an object as a key in HashMap/HashSet
//   → ALWAYS override both equals() AND hashCode()
//   → Both must be based on the SAME fields
// =====================================================================
class Key {
    private String id;

    Key(String id) {
        this.id = id;
    }

    // ❌ equals() NOT overridden → uses Object default (memory address comparison)
    // ❌ hashCode() NOT overridden → uses Object default (memory address based)

    // ✅ FIX — uncomment below to fix the memory leak:
    //
    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (!(o instanceof Key)) return false;
    //     Key key = (Key) o;
    //     return Objects.equals(id, key.id);   // compare by VALUE of id
    // }
    //
    // @Override
    // public int hashCode() {
    //     return Objects.hash(id);   // hash based on VALUE of id
    // }
    //
    // With fix: all new Key("sameId") are treated as SAME key → Set size = 1 ✅
}


// =====================================================================
// SUMMARY OF ALL 3 MEMORY LEAKS IN THIS FILE
// =====================================================================
//
// LEAK 1 — Static Collection
//   What:   Static List holds 100,000 Strings forever
//   Why:    Static field = class level = lives until JVM shutdown
//   GC:     Sees static reference → keeps all objects → cannot free
//   Fix:    Clear collection when done / use non-static / use WeakHashMap
//
// LEAK 2 — Unclosed FileReader
//   What:   FileReader opened, never closed
//   Why:    GC handles Java objects, NOT native OS resources
//   GC:     Can collect FileReader object (eventually) but not OS file handle
//   Fix:    Use try-with-resources → auto closes on exit
//
// LEAK 3 — Missing equals/hashCode
//   What:   HashSet stores 1000 objects instead of 1
//   Why:    Default hashCode uses memory address → all objects look unique
//   GC:     Sees Set holding 1000 references → keeps all 1000 objects
//   Fix:    Override equals() and hashCode() based on meaningful fields
//
// GOLDEN RULE:
//   GC only frees objects with NO references.
//   Memory Leak = you hold references to objects you no longer need.
//   GC is helpless when references exist — YOU must release them!
// =====================================================================