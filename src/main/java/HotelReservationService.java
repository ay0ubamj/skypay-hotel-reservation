import enums.RoomType;
import services.Service;
import utils.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HotelReservationService {
    public static void main(String[] args) throws ParseException {
        Service service = new Service();

        System.out.println("=== HOTEL RESERVATION SYSTEM TEST ===\n");

        // Create 3 rooms
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        // A typo in the doc for the room number 3 type
        service.setRoom(3, RoomType.MASTER, 3000);

        // Create 2 users
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        // Booking the rooms
        service.bookRoom(1, 2, DateUtil.parseDate("30/06/2026"), DateUtil.parseDate("07/07/2026"));
        service.bookRoom(1, 2, DateUtil.parseDate("07/07/2026"), DateUtil.parseDate("30/06/2026"));
        service.bookRoom(1, 1, DateUtil.parseDate("07/07/2026"), DateUtil.parseDate("08/07/2026"));
        service.bookRoom(2, 1, DateUtil.parseDate("07/07/2026"), DateUtil.parseDate("09/07/2026"));
        service.bookRoom(2, 3, DateUtil.parseDate("07/07/2026"), DateUtil.parseDate("08/07/2026"));

        // Set the room
        service.setRoom(1, RoomType.MASTER, 10000);

        service.printAll();
        service.printAllUsers();
    }
}
