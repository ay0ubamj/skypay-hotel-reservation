package entities;

import enums.RoomType;
import lombok.*;
import utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class Booking {
    private int bookingId;
    private User user;
    private Room room;
    private Date checkIn;
    private Date checkOut;
    private int totalCost;
    private Date bookingDate;

    private static int bookingCounter = 1;

    public Booking(User user, Room room, Date checkIn, Date checkOut, int totalCost) {
        this.bookingId = bookingCounter++;
        this.user = user;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalCost = totalCost;
        this.bookingDate =  new Date();
    }

    @Override
    public String toString() {
        return "Booking {" +
                "bookingId=" + bookingId +
                ", user=" + user +
                ", room=" + room +
                ", checkIn=" + DateUtil.formatDate(checkIn) +
                ", checkOut=" + DateUtil.formatDate(checkOut) +
                ", totalCost=" + totalCost +
                ", bookingDate=" + DateUtil.formatDate(bookingDate) +
                '}';
    }
}
