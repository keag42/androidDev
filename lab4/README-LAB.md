# COMP3074 Room Customer Lab

Open this lab4 folder in Android Studio, allow Gradle sync, and run app.
The app preserves the original package, theme, SDK configuration and version catalog.

Implemented:
- Customer entity with generated ID and Boolean active status
- CustomerDao with insert, delete, all-records Flow and name search
- Singleton AppDatabase
- CustomerViewModel and factory with coroutine writes
- Lifecycle-aware Compose collection
- Name/age validation, active switch, add/delete, automatic list updates
- Search and Clear buttons; matching rows use the same list
- Insets and scrolling for smaller screens and the keyboard

The optional update challenge is not included.

## Verify in Android Studio
1. Run with an empty database: see the empty message.
2. Submit a blank name or age: see validation and no inserted row.
3. Add several customers with different names and active states.
4. Confirm generated IDs and automatic list updates.
5. Search for part of a name; Clear restores all records.
6. Delete a displayed record; it disappears automatically.
7. Close and reopen the app; records should remain.
8. Rotate the device; entered form values should remain.

Build verification in the editing environment could not complete: the shared
Gradle cache is not writable and the isolated cache cannot download Gradle
because services.gradle.org is unavailable. No successful build or device run
is claimed. Build and run in Android Studio before submission.

## Reflection
1. Room is an abstraction over SQLite; SQLite still stores the data.
2. @Entity maps a Kotlin class to a table.
3. A DAO defines allowed database operations and queries.
4. Suspend insert/delete methods support asynchronous database writes without blocking the UI.
5. Flow queries emit new results when relevant database tables change.
6. Room generates object mapping instead of requiring handwritten Cursor parsing.
7. Compose observes Flow state and recomposes when data changes.
8. The ViewModel exposes records, manages search, and launches writes.
9. Room reduces boilerplate, checks SQL at compile time, and integrates with coroutines and Flow.
10. Direct SQLite teaches table creation, SQL, cursors, mapping, and manual resource management.
