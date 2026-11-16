# Complexity Reference for Q1–Q7

These are expected asymptotic costs; confirm practical behaviour with repeated measurements.

## Q1 – Text construction
- Repeated immutable `String` concatenation in a loop can copy an increasing prefix each iteration; for n similarly sized fields, the total copied characters can grow quadratically, O(n²).
- `StringBuilder` append is amortized O(1) per append and O(total output length) overall, commonly described as O(n) for n fields of bounded size.
- `StringBuffer` has similar asymptotic complexity but synchronizes individual operations.
- `StringJoiner` / `String.join` are useful for delimiter-based joining; output construction remains linear in total characters.

## Q2 – Lists and deque
| Operation | ArrayList | LinkedList | ArrayDeque |
|---|---:|---:|---:|
| Add at end | Amortized O(1) | O(1) | Amortized O(1) |
| Add at front | O(n) | O(1) | Amortized O(1) |
| Insert middle by index | O(n) | O(n) to reach node, O(1) link | Not index-oriented |
| Get k-th | O(1) | O(n) | No indexed get |
| Iterate | O(n) | O(n) | O(n) |
| Remove front | O(n) | O(1) | Amortized O(1) |

ArrayList stores an array of references; LinkedList stores node objects with links; ArrayDeque stores elements in a resizable circular array.

## Q3 – Equality and hashing
- HashSet add/contains/remove: expected O(1), worst-case O(n).
- TreeSet operations: O(log n).
- HashMap keyed by registration number: expected O(1).
- HashSet uses `hashCode()` to find a bucket and `equals()` to distinguish equal keys in that bucket.
- Equal objects must have equal hash codes. Fields used by equality/hash must not change while stored in a hash collection.

## Q4 – Maps
- HashMap put/get: expected O(1); iteration O(n).
- TreeMap put/get: O(log n); iteration in sorted key order O(n).
- LinkedHashMap put/get: expected O(1); iteration preserves insertion order O(n).
- Sorted array: binary search O(log n); insertion O(n) due to shifting; sorted report O(n).

## Q5 – Ranking
- Comparator sort: O(n log n), with sorting workspace depending on implementation.
- Comparator chains compare points, then goal difference, then name.
- Stable multi-pass sorting may require three O(n log n) sorts; comparator chaining performs one sort.
- A generic `Leaderboard<T>` can accept `Comparator<? super T>` and return a sorted copy.

## Q6 – Triage
- PriorityQueue offer/poll: O(log n); peek O(1); storage O(n).
- Sorted LinkedList insertion: O(n); removing first O(1).
- Five FIFO queues: enqueue O(1), choose next non-empty severity queue O(1) because there are five fixed levels; storage O(n).
- FIFO tie-break uses a monotonically increasing arrival sequence.
- Starvation can be reduced with an ageing rule that improves effective priority after a defined waiting interval.

## Q7 – Redesign
- Replacing repeated linear lookup in a list, O(n), with a maintained HashMap index gives expected O(1) lookup.
- The index requires O(n) additional storage and consistent updates on insert, update and delete.
