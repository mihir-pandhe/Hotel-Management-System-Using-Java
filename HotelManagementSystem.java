import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

class Room implements Serializable {
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
        JButton searchRoomButton = new JButton("Search Room");
        JButton roomSummaryButton = new JButton("Room Summary");

        panel.add(viewRoomsButton);
        panel.add(bookRoomButton);
        panel.add(cancelBookingButton);
        panel.add(searchRoomButton);
        panel.add(roomSummaryButton);
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

        searchRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchRoom();
            }
        });

        roomSummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roomSummary();
            }
        });

        loadRooms();
    }

    @SuppressWarnings("unchecked")
    private void loadRooms() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("rooms.dat"))) {
            rooms = (ArrayList<Room>) ois.readObject();
        } catch (Exception e) {
            for (int i = 1; i <= 10; i++) {
                rooms.add(new Room(i));
            }
        }
    }

    private void saveRooms() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("rooms.dat"))) {
            oos.writeObject(rooms);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                saveRooms();
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
                saveRooms();
                return;
            }
        }
        textArea.append("Room not found or not booked.\n");
    }

    private void searchRoom() {
        String input = JOptionPane.showInputDialog(this, "Enter room number to search:");
        int roomNumber = Integer.parseInt(input);
        for (Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                textArea.setText(room.toString());
                return;
            }
        }
        textArea.setText("Room not found.");
    }

    private void roomSummary() {
        int availableCount = 0;
        int bookedCount = 0;
        for (Room room : rooms) {
            if (room.isBooked) {
                bookedCount++;
            } else {
                availableCount++;
            }
        }
        textArea.setText("Available rooms: " + availableCount + "\nBooked rooms: " + bookedCount);
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
