package services;

import entities.Booking;
import entities.Room;
import entities.User;
import enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;

public class Service {
    ArrayList<Room> rooms;
    ArrayList<User> users;
    ArrayList<Booking> bookings;

    public Service() {
        this.rooms = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        try {
            if (roomPricePerNight <= 0) {
                throw new IllegalArgumentException("Room price must be positive");
            }

            Room room = findRoomByNumber(roomNumber);
            if (room == null) {
                if (!isValidRoomTypePrice(roomType, roomPricePerNight)) {
                    throw new IllegalArgumentException("A room with type '" +
                            roomType.toString() + "' and price " + roomPricePerNight + " already exists!");
                }
                Room newRoom = new Room(roomNumber, roomType, roomPricePerNight);
                rooms.add(newRoom);
            }
        } catch (Exception e) {
            System.out.println("Error setting room: " + e.getMessage());
        }
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        try {
            if (checkIn == null || checkOut == null) {
                throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
            }

            if (checkOut.before(checkIn) || checkOut.equals(checkIn)) {
                throw new IllegalArgumentException("Check-out date must be after check-in date");
            }

            // Find user and room
            User user = findUserById(userId);
            Room room = findRoomByNumber(roomNumber);

            if (user == null) {
                throw new IllegalArgumentException("User not found: " + userId);
            }
            if (room == null) {
                throw new IllegalArgumentException("Room not found: " + roomNumber);
            }

            // Calculate nights and total cost
            long nights = calculateNights(checkIn, checkOut);
            int totalCost = (int)(nights * room.getPricePerNight());

            // Check user balance
            if (user.getBalance() < totalCost) {
                throw new IllegalArgumentException("Insufficient balance. Required: " + totalCost +
                        ", Available: " + user.getBalance());
            }

            // Check room availability
            if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
                throw new IllegalArgumentException("Room is not available for the specified period");
            }

            // Create booking and update user balance
            Booking booking = new Booking(user, room, checkIn, checkOut, totalCost);
            bookings.add(booking);
            user.setBalance(user.getBalance() - totalCost);

            System.out.println("Booking successful: " + booking);

        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    public void printAll() {
        System.out.println("=== ALL ROOMS AND BOOKINGS ===");

        // Sort rooms
        ArrayList<Room> sortedRooms = new ArrayList<>(rooms);
        sortedRooms.sort((r1, r2) -> r2.getCreationDate().compareTo(r1.getCreationDate()));

        System.out.println("\nROOMS:");
        for (Room room : sortedRooms) {
            System.out.println(room);
        }

        // Sort bookings
        ArrayList<Booking> sortedBookings = new ArrayList<>(bookings);
        sortedBookings.sort((b1, b2) -> b2.getBookingDate().compareTo(b1.getBookingDate()));

        System.out.println("\nBOOKINGS:");
        for (Booking booking : sortedBookings) {
            System.out.println(booking);
        }
    }

    public void setUser(int userId, int balance) {
        try {
            if (balance < 0) {
                throw new IllegalArgumentException("Balance cannot be negative");
            }

            User existingUser = findUserById(userId);
            if (existingUser != null) {
                existingUser.setBalance(balance);
            } else {
                User newUser = new User(userId, balance);
                users.add(newUser);
            }
        } catch (Exception e) {
            System.out.println("Error setting user: " + e.getMessage());
        }
    }

    public void printAllUsers() {
        System.out.println("=== ALL USERS ===");

        for (User user : users) {
            System.out.println(user);
        }
    }


    /* Helper methods */
    private User findUserById(int userId) {
        return users.stream().filter(u -> u.getUserId() == userId).findFirst().orElse(null);
    }

    private Room findRoomByNumber(int roomNumber) {
        return rooms.stream().filter(r -> r.getRoomNumber() == roomNumber).findFirst().orElse(null);
    }

    private boolean isValidRoomTypePrice(RoomType roomType, int pricePerNight) {
        for (Room room : rooms) {
            if (room.getType() == roomType && room.getPricePerNight() == pricePerNight) {
                return false;
            }
        }
        return true;
    }

    private boolean isRoomAvailable(int roomNumber, Date checkIn, Date checkOut) {
        for (Booking booking : bookings) {
            if (booking.getRoom().getRoomNumber() == roomNumber) {
                if (!(checkOut.before(booking.getCheckIn()) || checkIn.after(booking.getCheckOut()))) {
                    return false;
                }
            }
        }
        return true;
    }

    private long calculateNights(Date checkIn, Date checkOut) {
        long diffInMillies = checkOut.getTime() - checkIn.getTime();
        return diffInMillies / (1000 * 60 * 60 * 24);
    }

}
