# Complexity and design notes

| Task | Design | Time / space observation |
|---|---|---|
| 1 | Dedicated listeners and layered service/DAO | Dispatch to a specific listener is O(1); payment work is dominated by persistence. |
| 2 | Preloaded department-to-doctor map | Average O(1) map lookup; storing m doctors uses O(m) space. |
| 3 | BorderLayout + FlowLayout + JScrollPane | Layout work is proportional to the small fixed component count; visible result rendering is managed by Swing. |
| 4 | Retained point list | O(n) redraw time and O(n) point storage for n clicks. A BufferedImage trades fixed O(width×height) memory for a single image copy per repaint. |
| 5 | One reusable lambda listener | One listener object can be attached to k event sources; registration is O(k) overall. |
| 6 | Filtered registrations | Returning n matching rows costs O(n) time and O(n) result memory. Paging bounds each page to O(p) for page size p. |
