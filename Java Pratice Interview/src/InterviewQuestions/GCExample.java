package InterviewQuestions;

public class GCExample {
    public static void main(String[] args) {

        GCExample obj = new GCExample();

        // Make object eligible for GC
        obj = null;

        // Request garbage collection
        System.gc();

        System.out.println("End of main");
    }

    @Override
    protected void finalize() {
        System.out.println("Garbage collected");
    }
}

// =====================================================
// ❌ GC CANNOT BE FORCED
// =====================================================

// System.gc() is just a REQUEST, not a command
// JVM may ignore it

// =====================================================
// ✔ WHEN DOES GC RUN?
// =====================================================

// - JVM decides based on memory needs
// - Runs automatically when heap is low
// - Depends on GC algorithm (G1, ZGC, etc.)

// =====================================================
// ✔ finalize() METHOD
// =====================================================

// Called before object is garbage collected
// BUT:
// - Not guaranteed to run
// - Deprecated in modern Java (Java 9+)

// =====================================================
// ✔ ELIGIBLE FOR GC
// =====================================================

// Object becomes eligible when:
// - No references point to it

// obj = null;  // eligible for GC

// =====================================================
// ✔ BEST PRACTICE
// =====================================================

// - Do NOT rely on System.gc()
// - Let JVM manage memory
// - Avoid finalize(), use try-with-resources instead

// =====================================================
// ✔ INTERVIEW TRICK
// =====================================================

// Q: Will System.gc() definitely run GC?
// A: NO ❌ — JVM may ignore it

// =====================================================
// ✔ REALITY
// =====================================================

// Modern JVMs are highly optimized
// Manual GC calls can hurt performance