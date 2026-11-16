# CO5 complexity reference

| Design | Time | Additional space | Notes |
|---|---:|---:|---|
| Text-file find by ID | O(n) | O(1) scan buffer | Every line may be inspected. |
| Fixed-length RandomAccessFile with known record index | O(1) seek | O(1) | Finding by ID still needs an index; a seek alone does not locate an unknown ID. |
| Buffered line count | O(N) | O(B) | N bytes, B-byte buffer; reduces I/O calls, not asymptotic CPU work. |
| Files.readAllLines | O(N) | O(N) | Convenient for small files; high memory for large logs. |
| HashMap index | O(n + q) build plus q average O(1) lookups | O(n) | Useful for many repeated lookups. |
| DAO vs SQL in GUI/service | Same DB operation complexity | Similar per-call data memory | Main gain is maintainability, testability and reuse. |
| Serialization / CSV / JSON | O(n) save and load | O(n) object or text representation | Serialization is Java-specific; text formats are easier to inspect/migrate. |
| JDBC transaction | O(1) coordination overhead plus statement costs | Driver/server dependent | Atomicity prevents partial wallet transfers. |
| Batch insert | O(n) row work with fewer round trips | O(b) client batch memory | Choose batch size based on measured throughput and memory. |
| Per-campus files | Search all campuses O(total records) | Sum of all file sizes | Cross-campus coordination and concurrent writes are difficult. |
