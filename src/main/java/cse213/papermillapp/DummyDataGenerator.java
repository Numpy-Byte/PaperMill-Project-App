package cse213.papermillapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

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
            System.out.println("SUCCESS: LoginCredentials.bin created.");
        } catch (Exception e) { e.printStackTrace(); }

        try {
            File file2 = new File("Material.bin");
            ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(file2));

            oos2.writeObject(new Material("RM001", "Wood Pulp", "Raw Material", 500, 100, 45.50f));
            oos2.writeObject(new Material("RM002", "Recycled Paper", "Raw Material", 1200, 200, 15.00f));
            oos2.writeObject(new Material("CH101", "Caustic Soda", "Chemical", 50, 10, 120.00f));
            oos2.writeObject(new Material("CH102", "Bleach", "Chemical", 30, 5, 80.50f));
            oos2.writeObject(new Material("PK201", "Packing Tape", "Packaging", 200, 50, 5.25f));
            oos2.writeObject(new Material("CH103", "Dye (Low Stock Test)", "Chemical", 5, 50, 200.00f));

            oos2.close();
            System.out.println("SUCCESS: Material.bin created with prices and test data.");
        } catch (Exception e) { e.printStackTrace(); }

        try {
            File file3 = new File("FinishedGoods.bin");
            ObjectOutputStream oos3 = new ObjectOutputStream(new FileOutputStream(file3));

            oos3.writeObject(new Product(1001, "Paper Roll", "A4 Premium White", 25.00f));
            oos3.writeObject(new Product(1002, "Paper Roll", "Newspaper Grade", 15.00f));
            oos3.writeObject(new Product(1003, "Box", "Cardboard Packaging", 5.00f));

            oos3.close();
            System.out.println("SUCCESS: FinishedGoods.bin created.");
        } catch (Exception e) { e.printStackTrace(); }

        try {
            File file4 = new File("Machine.bin");
            FileOutputStream fos = new FileOutputStream(file4);
            ObjectOutputStream oos4 = new ObjectOutputStream(fos);

            oos4.writeObject(new Machine("MAC-01", "Pulp Boiler Alpha", "Running"));
            oos4.writeObject(new Machine("MAC-02", "Paper Roller V2", "Running"));
            oos4.writeObject(new Machine("MAC-03", "Chemical Mixer", "Maintenance"));

            oos4.close();
            System.out.println("SUCCESS: Machine.bin created.");
        } catch (Exception e) { e.printStackTrace(); }

        try {
            File file5 = new File("Worker.bin");
            FileOutputStream fis5 = new FileOutputStream(file5);
            ObjectOutputStream oos5 = new ObjectOutputStream(fis5);

            oos5.writeObject(new Worker("W-001", "John Doe", "None"));
            oos5.writeObject(new Worker("W-002", "Jane Smith", "MAC-01"));
            oos5.writeObject(new Worker("W-003", "Mike Johnson", "None"));

            oos5.close();
            System.out.println("SUCCESS: Worker.bin created.");
        } catch (Exception e) { e.printStackTrace(); }

        try {
            File file6 = new File("MaintenanceLog.bin");
            FileOutputStream fos6 = new FileOutputStream(file6);
            ObjectOutputStream oos6 = new ObjectOutputStream(fos6);
            oos6.writeObject(new MaintenanceLog("2023-10-01", "MAC-01", "Repair", "Fixed belt"));
            oos6.writeObject(new MaintenanceLog("2023-10-05", "MAC-02", "Routine", "Oil Change"));
        } catch (Exception e) { e.printStackTrace(); }

        try {
            File file7 = new File("RawMaterialUsage.bin");
            FileOutputStream fos7 = new FileOutputStream(file7);
            ObjectOutputStream oos7 = new ObjectOutputStream(fos7);

            oos7.writeObject(new RawMaterialUsage("MAC-01", 500));
            oos7.writeObject(new RawMaterialUsage("MAC-02", 300));
            oos7.writeObject(new  RawMaterialUsage("MAC-03", 400));
            oos7.close();
            System.out.println("SUCCESS: RawMaterialUsage.bin created.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file8 = new File("EfficiencyData.bin");
            ObjectOutputStream oos8 = new ObjectOutputStream(new FileOutputStream(file8));
            oos8.writeObject(new EfficiencyReport("W-101", "MAC-01", 520, "Excellent"));
            oos8.writeObject(new EfficiencyReport("W-102", "MAC-02", 300, "Good"));
            oos8.writeObject(new EfficiencyReport("W-103", "MAC-03", 150, "Needs Improvement"));
            oos8.close();
            System.out.println("SUCCESS: EfficiencyData.bin created.");
        } catch (Exception e) { e.printStackTrace(); }
    }
}