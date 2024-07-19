import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

public class HotelManagementSystem extends JFrame {
    private ArrayList<Room> rooms;
    private JTextArea textArea;

    public HotelManagementSystem() {
        rooms = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            rooms.add(new Room(i));
        }

        setTitle("Hotel Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton viewRoomsButton = new JButton("View Rooms");
        JButton bookRoomButton = new JButton("Book Room");
        JButton cancelBookingButton = new JButton("Cancel Booking");

        panel.add(viewRoomsButton);
        panel.add(bookRoomButton);
        panel.add(cancelBookingButton);
        add(panel, BorderLayout.SOUTH);

        viewRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewRooms();
            }
        });

        bookRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookRoom();
            }
        });

        cancelBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelBooking();
            }
        });
    }

    private void viewRooms() {
        textArea.setText("");
        for (Room room : rooms) {
            textArea.append(room.toString() + "\n");
        }
    }

    private void bookRoom() {
        String input = JOptionPane.showInputDialog(this, "Enter room number to book:");
        int roomNumber = Integer.parseInt(input);
        for (Room room : rooms) {
            if (room.roomNumber == roomNumber && !room.isBooked) {
                room.isBooked = true;
                textArea.append("Room " + roomNumber + " booked successfully.\n");
                return;
            }
        }
        textArea.append("Room not available or already booked.\n");
    }

    private void cancelBooking() {
        String input = JOptionPane.showInputDialog(this, "Enter room number to cancel booking:");
        int roomNumber = Integer.parseInt(input);
        for (Room room : rooms) {
            if (room.roomNumber == roomNumber && room.isBooked) {
                room.isBooked = false;
                textArea.append("Booking for room " + roomNumber + " cancelled successfully.\n");
                return;
            }
        }
        textArea.append("Room not found or not booked.\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HotelManagementSystem().setVisible(true);
            }
        });
    }
}
