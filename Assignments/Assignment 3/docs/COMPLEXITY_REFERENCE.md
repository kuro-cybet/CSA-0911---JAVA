# CO3 Complexity and Concept Reference

## Q1 – Exceptions
- Input validation is O(1) per field and O(1) auxiliary space, excluding input storage.
- Throwing/capturing an exception is substantially more expensive than returning an error code because a stack trace is captured; exact cost depends on JVM and stack depth.
- Checked exceptions must be caught or declared. Unchecked exceptions need not be declared.
- Catch narrower exception types before broader supertypes. A broad catch first makes later narrower catches unreachable.

## Q2 – User-defined exceptions
- Exception creation and propagation are generally O(d) in stack depth for stack-trace capture, plus O(c) retained cause-chain space for c linked exceptions.
- Checked exceptions require catch-or-declare handling. Unchecked exceptions reduce signature noise but can make failure paths less explicit.
- Chaining with `new WrapperException(message, cause)` preserves the lower-level cause.

## Q3 – Resource handling
- Reading f files with a total of B bytes takes O(B) time; list/report storage is O(B) if all lines are retained.
- Correct try-with-resources closes each resource when its try block ends, including exceptional exits.
- Sequentially opening one file at a time holds O(1) file handles. Opening all f files together can hold O(f) handles.
- Suppressed exceptions preserve close failures when another exception is already being thrown.

## Q4 – Threads
- Processing n scripts with p worker threads has idealized work time about O(n/p) for equal independent tasks, plus scheduling and I/O overhead.
- Creating one thread per task can consume O(n) thread stacks. A fixed pool uses O(p) worker thread stacks.
- `start()` schedules a new thread; `run()` is an ordinary method call if invoked directly. `join()` waits for completion.

## Q5 – Synchronization
- Array seat lookup and update are O(1); synchronization adds lock coordination and contention costs.
- An AtomicBoolean per seat uses O(s) space for s seats; compareAndSet is a non-blocking atomic operation.
- ReentrantLock uses O(1) lock metadata per lock; ConcurrentHashMap uses O(s) key/value storage.
- The critical section must include both the availability check and booking update.

## Q6 – Deadlock and communication
- Bounded buffer put/take are O(1) per operation; buffer space is O(5), constant capacity.
- A consistent global lock order prevents circular wait in the wallet-transfer example.
- ArrayBlockingQueue offers blocking put/take with O(1) queue operations in typical implementations and fixed O(capacity) storage.
- tryLock with timeout can avoid indefinite waiting but requires handling failed acquisition and possible retry.

## Q7 – Concurrent result processing
- Each file is processed in O(b) time where b is its content size; total work is O(sum of file sizes).
- With p workers, idealized elapsed work is approximately total work/p, bounded by I/O and scheduling.
- ExecutorService with p workers uses O(p) thread stacks; one thread per file uses O(n) stacks for n files.
- Futures and result collection require O(n) references/results. ConcurrentLinkedQueue supports concurrent result insertion.
- A failed task is captured through Future.get() as ExecutionException; this implementation also converts expected per-file failures into failure Result values so one bad file does not stop the batch.
