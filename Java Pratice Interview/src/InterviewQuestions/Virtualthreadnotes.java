package InterviewQuestions;

public class Virtualthreadnotes {
}

// ╔══════════════════════════════════════════════════════╗
// ║         VIRTUAL THREADS — SHORT & SIMPLE NOTES      ║
// ║                     Java 21+                        ║
// ╚══════════════════════════════════════════════════════╝


// ═══════════════════════════════════════════════════════
// WHAT IS A VIRTUAL THREAD?
// ═══════════════════════════════════════════════════════
//
// Lightweight thread managed by JVM (not OS)
// Stable since Java 21
//
// Platform Thread  =  Java thread → OS thread (1-to-1)  → expensive
// Virtual Thread   =  Many Java threads → Few OS threads → cheap
//
//   Platform Thread : ~1MB,  max ~10,000
//   Virtual Thread  : ~few KB, max MILLIONS


// ═══════════════════════════════════════════════════════
// THE PROBLEM IT SOLVES
// ═══════════════════════════════════════════════════════
//
// Platform thread doing a DB call:
//   [WORK][.....WAITING DB 100ms.....][WORK]
//          thread idle but using 1MB RAM — WASTE!
//
// Virtual thread doing a DB call:
//   Blocks → UNMOUNTS from carrier → carrier picks another VT
//   Resumes → MOUNTS back when done
//   Carrier thread NEVER sits idle ✅


// ═══════════════════════════════════════════════════════
// HOW IT WORKS (3 steps)
// ═══════════════════════════════════════════════════════
//
// 1. Virtual thread MOUNTS on a Carrier Thread to run
// 2. On blocking (DB/IO/sleep) → UNMOUNTS → carrier picks another VT
// 3. On unblock → MOUNTS back → resumes from where it stopped
//
// Carrier Thread = small pool of platform threads (= CPU core count)
//                  managed by ForkJoinPool automatically


// ═══════════════════════════════════════════════════════
// CREATING VIRTUAL THREADS
// ═══════════════════════════════════════════════════════
//
// Way 1 — single virtual thread
//   Thread.ofVirtual().start(() -> doWork());
//
// Way 2 — shortest
//   Thread.startVirtualThread(() -> doWork());
//
// Way 3 — RECOMMENDED for many tasks
//   ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor();
//   exec.submit(() -> doWork());   // each task = own virtual thread
//
// Check if virtual:
//   Thread.currentThread().isVirtual()   // true or false


// ═══════════════════════════════════════════════════════
// WHEN TO USE / NOT USE
// ═══════════════════════════════════════════════════════
//
// ✅ USE — IO-bound (task WAITS for something):
//   DB queries, HTTP calls, File IO, Message queues, Thread.sleep()
//
// ❌ DON'T USE — CPU-bound (task COMPUTES continuously):
//   Heavy math, image processing, encryption
//   → Use parallelStream() or ForkJoinPool instead


// ═══════════════════════════════════════════════════════
// PITFALLS
// ═══════════════════════════════════════════════════════
//
// ⚠️ PINNING — biggest pitfall!
//   synchronized block with blocking code → VT gets PINNED to carrier
//   Carrier cannot pick up other VTs → defeats the purpose!
//
//   ❌ synchronized(lock) { Thread.sleep(x); }   // PINNED!
//   ✅ reentrantLock.lock(); Thread.sleep(x); lock.unlock();  // safe
//
//   Detect pinning: run JVM with -Djdk.tracePinnedThreads=full
//
// ⚠️ Don't POOL virtual threads
//   ❌ Executors.newFixedThreadPool(100)           // wrong for VTs
//   ✅ Executors.newVirtualThreadPerTaskExecutor() // correct ✅
//
// ⚠️ ThreadLocal → use ScopedValue instead (Java 21+)
//   Millions of VTs with ThreadLocal = high memory


// ═══════════════════════════════════════════════════════
// STRUCTURED CONCURRENCY (Java 21+)
// ═══════════════════════════════════════════════════════
//
// Run multiple tasks at the SAME TIME, wait together
//
//   try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
//       var t1 = scope.fork(() -> fetchUser());    // virtual thread
//       var t2 = scope.fork(() -> fetchOrders());  // virtual thread
//       scope.join();
//       return t1.get() + t2.get();
//   }
//   // Sequential: 200ms + 150ms = 350ms
//   // Concurrent: max(200ms, 150ms) = 200ms ✅
//
//   ShutdownOnFailure → wait for ALL  (need all results)
//   ShutdownOnSuccess → stop at FIRST (need fastest result)


// ═══════════════════════════════════════════════════════
// QUICK CHEAT SHEET
// ═══════════════════════════════════════════════════════
//
//  Platform Thread  |  Virtual Thread
//  ~1MB memory      |  ~few KB
//  ~10,000 max      |  Millions
//  OS managed       |  JVM managed
//  Blocks = idle    |  Blocks = unmounts (efficient)
//
//  1000 tasks x 100ms IO:
//  Platform (pool 100) → ~1000ms
//  Virtual             → ~100ms ✅ (10x faster!)
//
//  GOLDEN RULE:
//  Code WAITS?     → Virtual Threads ✅
//  Code COMPUTES?  → parallelStream() ✅
//
//  ANALOGY:
//  Platform = 1 bus per passenger (most idle at red lights)
//  Virtual  = few taxis, millions of rides, never idle
//
// ═══════════════════════════════════════════════════════
// END
// ═══════════════════════════════════════════════════════