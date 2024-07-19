import java.util.ArrayList;
import java.util.Scanner;

class Room {
    int roomNumber;
    boolean isBooked;

    Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isBooked = false;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " - " + (isBooked ? "Booked" : "Available");
    }
}

public class HotelManagementSystem {
    private ArrayList<Room> rooms;

    public HotelManagementSystem() {
        rooms = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            rooms.add(new Room(i));
        }
    }

    public void viewRooms() {
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    public void bookRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.roomNumber == roomNumber && !room.isBooked) {
                room.isBooked = true;
                System.out.println("Room " + roomNumber + " booked successfully.");
                return;
            }
        }
        System.out.println("Room not available or already booked.");
    }

    public void cancelBooking(int roomNumber) {
        for (Room room : rooms) {
            if (room.roomNumber == roomNumber && room.isBooked) {
                room.isBooked = false;
                System.out.println("Booking for room " + roomNumber + " cancelled successfully.");
                return;
            }
        }
        System.out.println("Room not found or not booked.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelManagementSystem system = new HotelManagementSystem();

        while (true) {
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    system.viewRooms();
                    break;
                case 2:
                    System.out.print("Enter room number to book: ");
                    int roomNumberToBook = scanner.nextInt();
                    system.bookRoom(roomNumberToBook);
                    break;
                case 3: 
                    System.out.print("Enter room number to cancel booking: ");
                    int roomNumberToCancel = scanner.nextInt();
                    system.cancelBooking(roomNumberToCancel);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
