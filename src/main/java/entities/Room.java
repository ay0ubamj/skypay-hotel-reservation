package entities;

import enums.RoomType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
public class Room {
    private int roomNumber;
    private RoomType type;
    private int pricePerNight;
    private Date creationDate;

    public Room(int roomNumber, RoomType type, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.creationDate = new Date();
    }

    @Override
    public String toString() {
        return "Room {roomNumber=" + roomNumber + ", type=" + type.name() +
                ", pricePerNight=" + pricePerNight + "}";
    }
}
