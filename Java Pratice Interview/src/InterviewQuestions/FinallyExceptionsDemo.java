package InterviewQuestions;

public class FinallyExceptionsDemo {

    public static void main(String[] args) {

        // =====================================================================
        // WHAT HAPPENS:
        //   Step 1 → try throws "Exception from try"
        //   Step 2 → catch catches it, then throws "Exception from catch"
        //   Step 3 → finally ALWAYS runs, throws "Exception from finally"
        //   Step 4 → finally's exception WINS and REPLACES catch's exception
        //   Step 5 → JVM prints only: "Exception from finally"
        //
        // OUTPUT:
        //   Exception in thread "main"
        //   java.lang.RuntimeException: Exception from finally
        //
        // KEY RULE:
        //   If finally block throws an exception:
        //   → It REPLACES any exception thrown in try or catch
        //   → The original exceptions are SILENTLY LOST (no trace, no log)
        //   → This is considered very BAD PRACTICE in real projects
        // =====================================================================

        try {
            // STEP 1: This exception is thrown first
            // Execution of try block STOPS here immediately
            // Control jumps to catch block
            throw new RuntimeException("Exception from try");

        } catch (Exception e) {

            // STEP 2: "Exception from try" is caught here in variable 'e'
            // e.getMessage() = "Exception from try"
            //
            // Now catch itself throws a NEW exception
            // Execution of catch block STOPS here immediately
            // Control jumps to finally block
            //
            // NOTE: "Exception from try" is now GONE
            //       It was caught but we didn't save or log it anywhere
            throw new RuntimeException("Exception from catch");

        } finally {

            // STEP 3: finally block ALWAYS runs
            // It runs even if:
            //   → try threw an exception        ✅ runs
            //   → catch threw an exception      ✅ runs
            //   → no exception at all           ✅ runs
            //   → return statement in try/catch ✅ runs (before return!)
            //
            // ONLY cases finally does NOT run:
            //   → System.exit() is called
            //   → JVM crashes
            //   → Infinite loop before finally
            //
            // STEP 4: This exception is thrown from finally
            // It COMPLETELY REPLACES "Exception from catch"
            // "Exception from catch" is now SILENTLY LOST FOREVER
            //
            // This is the exception JVM will show to the user
            throw new RuntimeException("Exception from finally");

            // ⚠️ DANGER: Two exceptions are now lost silently:
            //    1. "Exception from try"   → lost (was caught but discarded)
            //    2. "Exception from catch" → lost (suppressed by finally)
            //    Only "Exception from finally" survives
        }

        // This line is UNREACHABLE — never executes
        // Because finally throws an exception, control never returns here
    }
}

// =====================================================================
// FINAL OUTPUT:
//   Exception in thread "main"
//   java.lang.RuntimeException: Exception from finally
//	     at InterviewQuestions.FinallyExceptionsDemo.main(FinallyExceptionsDemo.java:20)
//
// "Exception from try"   → LOST (caught by catch, not re-thrown)
// "Exception from catch" → LOST (suppressed by finally's exception)
// "Exception from finally" → WINS (last exception thrown always wins)
// =====================================================================