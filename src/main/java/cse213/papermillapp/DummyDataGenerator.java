package cse213.papermillapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DummyDataGenerator {
    public static void main(String[] args) {
        try {
            File f = new File("LoginCredentials.bin");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));

            oos.writeObject(new LoginCredentials(2430999, "1234", "Inventory Officer"));
            oos.writeObject(new LoginCredentials(2430998, "1234", "Production Supervisor"));


            oos.writeObject(new LoginCredentials(2430997, "1234", "Accountant"));
            oos.writeObject(new LoginCredentials(2430996, "1234", "HR Officer"));
            oos.writeObject(new LoginCredentials(2430995, "1234", "Plant Manager"));
            oos.writeObject(new LoginCredentials(2430994, "1234", "Maintenance Coordinator"));
            oos.writeObject(new LoginCredentials(2430993, "1234", "Customer"));
            oos.writeObject(new LoginCredentials(2430992, "1234", "Sales Representative"));

            oos.close();
            System.out.println("SUCCESS: LoginCredencial.bin created with 7-digit IDs!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file2 = new File("Material.bin");
            ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(file2));

            ArrayList<Material> materialList = new ArrayList<>();

            materialList.add(new Material("RM001", "Wood Pulp", "Raw Material", 500, 100));
            materialList.add(new Material("RM002", "Recycled Paper", "Raw Material", 1200, 200));
            materialList.add(new Material("CH101", "Caustic Soda", "Chemical", 50, 10));
            materialList.add(new Material("CH102", "Bleach", "Chemical", 30, 5));
            materialList.add(new Material("PK201", "Packing Tape", "Packaging", 200, 50));

            oos2.writeObject(materialList);
            oos2.close();
            System.out.println("SUCCESS: Material.bin created with 5 starting items.");

        } catch (Exception e) {
            System.out.println("Error Creating Material file.");
            e.printStackTrace();
        }
    }
}