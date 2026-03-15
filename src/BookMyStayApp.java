import java.util.*;
import java.io.*;

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(String type, int count) {
        inventory.put(type, count);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

class FilePersistenceService {

    public void saveInventory(RoomInventory inventory, String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
            writer.close();
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                inventory.setInventory(parts[0], Integer.parseInt(parts[1]));
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading inventory.");
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        String filePath = "inventory.txt";

        persistenceService.loadInventory(inventory, filePath);

        System.out.println();
        inventory.displayInventory();

        persistenceService.saveInventory(inventory, filePath);
    }
}