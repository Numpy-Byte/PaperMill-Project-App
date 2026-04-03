package cse213.papermillapp;

import java.io.File;
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
            System.out.println("SUCCESS: LoginCredencial.bin created with 7-digit IDs!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}