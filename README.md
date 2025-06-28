## 🛠️ Technical Specifications
- JDK Version: Java 17

## Screenshot of prints
The screenshot of printAll(..) and printAllUsers(..) is in the resources folder.

## Design question

1- No this isn't the best approach, because we didn't respect the rule of single responsibility.
We should separate the business logic and we can create this services to satisfy the rule:
- RoomService: Handle room-related operations (setRoom, room availability checks)
- UserService: Handle user-related operations (setUser, balance management)
- BookingService: Handle booking operations (bookRoom, booking validation)
- ReportService: Handle printing and reporting (printAll, printAllUsers)
