// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to work with files on the computer
import java.io.File;
// We need this to read from files
import java.io.FileInputStream;
// We need this to write to files
import java.io.FileOutputStream;
// We need this to save objects (like data) to files
import java.io.ObjectOutputStream;

// This class creates test data for all the users and systems in the paper mill application
// It generates fake/dummy data to help test the application
public class DummyDataGenerator {
    // This is the main method - it runs when you start the program
    // It does not take any parameters (String[] args is standard for main)
    // It does not return anything (void means nothing is returned)
    // The public keyword means anyone can run this method
    // The static keyword means we don't need an object to call this
    public static void main(String[] args) {
        // STEP 1: Create login credentials for all 8 users in the system
        // This section generates login information for all users
        // Each user has an ID, password, and role name
        try {
            // Create a new file to store login info
            // This file will save all user login credentials
            File f = new File("LoginCredentials.bin");
            // Create a stream to write objects to the file
            // ObjectOutputStream allows us to write Java objects to a file
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));

            // Write login data for User 1 - Inventory Officer (works with materials/stock)
            // ID: 2430999, Password: 1234, Role: Inventory Officer
            oos.writeObject(new LoginCredentials(2430999, "1234", "Inventory Officer"));
            // Write login data for User 2 - Production Supervisor (manages production/workers)
            // ID: 2430998, Password: 1234, Role: Production Supervisor
            oos.writeObject(new LoginCredentials(2430998, "1234", "Production Supervisor"));
            // Write login data for User 3 - Accountant (handles money stuff)
            // ID: 2430997, Password: 1234, Role: Accountant
            oos.writeObject(new LoginCredentials(2430997, "1234", "Accountant"));
            // Write login data for User 4 - HR Officer (manages employees)
            // ID: 2430996, Password: 1234, Role: HR Officer
            oos.writeObject(new LoginCredentials(2430996, "1234", "HR Officer"));
            // Write login data for User 5 - Plant Manager (manages whole factory)
            // ID: 2430995, Password: 1234, Role: Plant Manager
            oos.writeObject(new LoginCredentials(2430995, "1234", "Plant Manager"));
            // Write login data for User 6 - Maintenance Coordinator (fixes machines)
            // ID: 2430994, Password: 1234, Role: Maintenance Coordinator
            oos.writeObject(new LoginCredentials(2430994, "1234", "Maintenance Coordinator"));
            // Write login data for User 7 - Customer (buys products)
            // ID: 2430993, Password: 1234, Role: Customer
            oos.writeObject(new LoginCredentials(2430993, "1234", "Customer"));
            // Write login data for User 8 - Sales Representative (sells products)
            // ID: 2430992, Password: 1234, Role: Sales Representative
            oos.writeObject(new LoginCredentials(2430992, "1234", "Sales Representative"));

            // Close the file - this saves all the data
            // If we don't close, the file may not save properly
            oos.close();
            // Show success message
            // This prints to console to confirm the file was created
            System.out.println("SUCCESS: LoginCredentials.bin created.");
        } catch (Exception e) {
            // If any error happens, print the error message
            e.printStackTrace();
        }

        // STEP 2: Create materials (raw materials like paper, chemicals, packaging)
        // This section creates the items that are used in production
        // Each material has an ID, name, type, current stock, minimum stock, and price
        try {
            // Create a new file to store material info
            // This file will store all raw materials in the warehouse
            File file2 = new File("Material.bin");
            // Create a stream to write objects to the file
            // This stream will write each material to the file
            ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(file2));

            // Add Wood Pulp - 500 units in stock, minimum 100, costs 45.50 each
            // Material ID: RM001, Type: Raw Material, Current Stock: 500, Min Stock: 100, Price: 45.50
            oos2.writeObject(new Material("RM001", "Wood Pulp", "Raw Material", 500, 100, 45.50f));
            // Add Recycled Paper - 1200 units in stock, minimum 200, costs 15.00 each
            // Material ID: RM002, Type: Raw Material, Current Stock: 1200, Min Stock: 200, Price: 15.00
            oos2.writeObject(new Material("RM002", "Recycled Paper", "Raw Material", 1200, 200, 15.00f));
            // Add Caustic Soda - 50 units in stock, minimum 10, costs 120.00 each
            // Material ID: CH101, Type: Chemical, Current Stock: 50, Min Stock: 10, Price: 120.00
            oos2.writeObject(new Material("CH101", "Caustic Soda", "Chemical", 50, 10, 120.00f));
            // Add Bleach - 30 units in stock, minimum 5, costs 80.50 each
            // Material ID: CH102, Type: Chemical, Current Stock: 30, Min Stock: 5, Price: 80.50
            oos2.writeObject(new Material("CH102", "Bleach", "Chemical", 30, 5, 80.50f));
            // Add Packing Tape - 200 units in stock, minimum 50, costs 5.25 each
            // Material ID: PK201, Type: Packaging, Current Stock: 200, Min Stock: 50, Price: 5.25
            oos2.writeObject(new Material("PK201", "Packing Tape", "Packaging", 200, 50, 5.25f));
            // Add Dye - only 5 units (this is used to test low stock warning) costs 200 each
            // Material ID: CH103, Type: Chemical, Current Stock: 5, Min Stock: 50 (lower than current), Price: 200.00
            // This tests the low stock alert feature
            oos2.writeObject(new Material("CH103", "Dye (Low Stock Test)", "Chemical", 5, 50, 200.00f));

            // Close the file - this saves all the material data
            // This is important - without closing, data may not be saved
            oos2.close();
            // Show success message
            // This confirms that the material file was created successfully
            System.out.println("SUCCESS: Material.bin created with prices and test data.");
        } catch (Exception e) {
            // If any error happens, print the error message
            e.printStackTrace();
        }

        // STEP 3: Create finished goods (products that the factory makes)
        // This section creates products that are made in the factory and ready to sell
        // Each product has an ID, type, name, and selling price
        try {
            // Create a new file to store product info
            // This file will store all finished products
            File file3 = new File("FinishedGoods.bin");
            // Create a stream to write objects to the file
            // This stream will write each product to the file
            ObjectOutputStream oos3 = new ObjectOutputStream(new FileOutputStream(file3));

            // Add Product 1 - A4 Paper Rolls, white color, costs 25.00 each
            // Product ID: 1001, Type: Paper Roll, Name: A4 Premium White, Price: 25.00
            oos3.writeObject(new Product(1001, "Paper Roll", "A4 Premium White", 25.00f));
            // Add Product 2 - Newspaper Grade Paper Rolls, costs 15.00 each
            // Product ID: 1002, Type: Paper Roll, Name: Newspaper Grade, Price: 15.00
            oos3.writeObject(new Product(1002, "Paper Roll", "Newspaper Grade", 15.00f));
            // Add Product 3 - Cardboard Boxes, costs 5.00 each
            // Product ID: 1003, Type: Box, Name: Cardboard Packaging, Price: 5.00
            oos3.writeObject(new Product(1003, "Box", "Cardboard Packaging", 5.00f));

            // Close the file - this saves all the product data
            // This is important - without closing, data may not be saved
            oos3.close();
            // Show success message
            // This confirms that the finished goods file was created successfully
            System.out.println("SUCCESS: FinishedGoods.bin created.");
        } catch (Exception e) {
            // If any error happens, print the error message
            e.printStackTrace();
        }

        // STEP 4: Create machines (equipment used in production)
        // This section creates the factory machines that produce the paper
        // Each machine has an ID, name, and current status
        try {
            // Create a new file to store machine info
            // This file will store all machines in the factory
            File file4 = new File("Machine.bin");
            // Create a file output stream (to write to file)
            // This helps us write data to the machine file
            FileOutputStream fos = new FileOutputStream(file4);
            // Create a stream to write objects to the file
            // This stream will write each machine to the file
            ObjectOutputStream oos4 = new ObjectOutputStream(fos);

            // Add Machine 1 - Pulp Boiler, currently Running
            // Machine ID: MAC-01, Name: Pulp Boiler Alpha, Status: Running
            oos4.writeObject(new Machine("MAC-01", "Pulp Boiler Alpha", "Running"));
            // Add Machine 2 - Paper Roller, currently Running
            // Machine ID: MAC-02, Name: Paper Roller V2, Status: Running
            oos4.writeObject(new Machine("MAC-02", "Paper Roller V2", "Running"));
            // Add Machine 3 - Chemical Mixer, currently in Maintenance
            // Machine ID: MAC-03, Name: Chemical Mixer, Status: Maintenance
            oos4.writeObject(new Machine("MAC-03", "Chemical Mixer", "Maintenance"));

            // Close the file - this saves all the machine data
            // This is important - without closing, data may not be saved
            oos4.close();
            // Show success message
            // This confirms that the machine file was created successfully
            System.out.println("SUCCESS: Machine.bin created.");
        } catch (Exception e) {
            // If any error happens, print the error message
            e.printStackTrace();
        }

        // STEP 5: Create workers (people who work on the machines)
        // This section creates factory workers who operate the machines
        // Each worker has an ID, name, and assigned machine
        try {
            // Create a new file to store worker info
            // This file will store all workers in the factory
            File file5 = new File("Worker.bin");
            // Create a file output stream (to write to file)
            // This helps us write data to the worker file
            FileOutputStream fis5 = new FileOutputStream(file5);
            // Create a stream to write objects to the file
            // This stream will write each worker to the file
            ObjectOutputStream oos5 = new ObjectOutputStream(fis5);

            // Add Worker 1 - John Doe, not assigned to any machine
            // Worker ID: W-001, Name: John Doe, Assigned Machine: None
            oos5.writeObject(new Worker("W-001", "John Doe", "None"));
            // Add Worker 2 - Jane Smith, assigned to machine MAC-01
            // Worker ID: W-002, Name: Jane Smith, Assigned Machine: MAC-01
            oos5.writeObject(new Worker("W-002", "Jane Smith", "MAC-01"));
            // Add Worker 3 - Mike Johnson, not assigned to any machine
            // Worker ID: W-003, Name: Mike Johnson, Assigned Machine: None
            oos5.writeObject(new Worker("W-003", "Mike Johnson", "None"));

            // Close the file - this saves all the worker data
            // This is important - without closing, data may not be saved
            oos5.close();
            // Show success message
            // This confirms that the worker file was created successfully
            System.out.println("SUCCESS: Worker.bin created.");
        } catch (Exception e) {
            // If any error happens, print the error message
            e.printStackTrace();
        }

        // STEP 6: Create maintenance logs (records of machine repairs/maintenance)
        // This section creates maintenance records for machine repairs and services
        // Each maintenance record tracks when machines were serviced
        try {
            // Create a new file to store maintenance information
            // This file will store all maintenance records for machines
            File file6 = new File("MaintenanceLog.bin");
            // Create a file output stream (to write to file)
            // This helps us write data to the maintenance log file
            FileOutputStream fos6 = new FileOutputStream(file6);
            // Create a stream to write objects to the file
            // This stream will write each maintenance record to the file
            ObjectOutputStream oos6 = new ObjectOutputStream(fos6);

            // Add maintenance record - Machine MAC-01, Repair type, Fixed belt
            // Date: 2023-10-01, Machine ID: MAC-01, Type: Repair, Description: Fixed belt
            oos6.writeObject(new MaintenanceLog("2023-10-01", "MAC-01", "Repair", "Fixed belt"));
            // Add maintenance record - Machine MAC-02, Routine type, Oil Change
            // Date: 2023-10-05, Machine ID: MAC-02, Type: Routine, Description: Oil Change
            oos6.writeObject(new MaintenanceLog("2023-10-05", "MAC-02", "Routine", "Oil Change"));

            // Close the file - this saves all the maintenance data
            // This is important - without closing, data may not be saved
            oos6.close();
            // Show success message
            // This confirms that the maintenance log file was created successfully
            System.out.println("SUCCESS: MaintenanceLog.bin created.");
        } catch (Exception e) {
            // If any error happens, print the error message
            e.printStackTrace();
        }

        // STEP 7: Create raw material usage logs (how much material is used by each machine)
        // This section creates records of how much raw material each machine uses
        // This is used to calculate waste and efficiency
        try {
            // Create a new file to store material usage info
            // This file will store how much material each machine has used
            File file7 = new File("RawMaterialUsage.bin");
            // Create a file output stream (to write to file)
            // This helps us write data to the material usage file
            FileOutputStream fos7 = new FileOutputStream(file7);
            // Create a stream to write objects to the file
            // This stream will write each usage record to the file
            ObjectOutputStream oos7 = new ObjectOutputStream(fos7);

            // Machine MAC-01 used 550 units of material, produced 500 (9.1% waste)
            // Machine ID: MAC-01, Material Used: 550 units
            oos7.writeObject(new RawMaterialUsage("MAC-01", 550));
            // Machine MAC-02 used 330 units of material, produced 300 (9.1% waste)
            // Machine ID: MAC-02, Material Used: 330 units
            oos7.writeObject(new RawMaterialUsage("MAC-02", 330));
            // Machine MAC-03 used 440 units of material, produced 400 (9.1% waste)
            // Machine ID: MAC-03, Material Used: 440 units
            oos7.writeObject(new RawMaterialUsage("MAC-03", 440));

            // Close the file - this saves all the usage data
            // This is important - without closing, data may not be saved
            oos7.close();
            // Show success message
            // This confirms that the material usage file was created successfully
            System.out.println("SUCCESS: RawMaterialUsage.bin created with realistic waste data.");
        } catch (Exception e) {
            // If any error happens, print the error message
            e.printStackTrace();
        }

        // STEP 7.5: Create production records that correspond to the raw material usage
        // This creates production output data that matches the material usage above
        // This ensures waste calculations show realistic percentages instead of 100%
        try {
            // Create a new file to store production records
            // This file will store production output for waste calculation
            File fileProd = new File("ProductionRecord.bin");
            // Create a file output stream (to write to file)
            // This helps us write data to the production record file
            FileOutputStream fosProd = new FileOutputStream(fileProd);
            // Create a stream to write objects to the file
            // This stream will write each production record to the file
            ObjectOutputStream oosProd = new ObjectOutputStream(fosProd);

            // Production records that correspond to raw material usage above
            // MAC-01: Used 550, produced 500 (50 waste = 9.1%)
            oosProd.writeObject(new ProductionRecord("MAC-01", 500, "BATCH-001"));
            // MAC-02: Used 330, produced 300 (30 waste = 9.1%)
            oosProd.writeObject(new ProductionRecord("MAC-02", 300, "BATCH-002"));
            // MAC-03: Used 440, produced 400 (40 waste = 9.1%)
            oosProd.writeObject(new ProductionRecord("MAC-03", 400, "BATCH-003"));

            // Close the file - this saves all the production data
            // This is important - without closing, data may not be saved
            oosProd.close();
            // Show success message
            // This confirms that the production record file was created successfully
            System.out.println("SUCCESS: ProductionRecord.bin created with data matching raw material usage.");
        } catch (Exception e) {
            // If any error happens, print the error message
            e.printStackTrace();
        }

        // STEP 8: Create efficiency reports (how well workers and machines are performing)
        // This section creates performance records for workers and their productivity
        // This helps track worker efficiency on machines
        try {
            // Create a new file to store efficiency information
            // This file will store performance data for all workers
            File file8 = new File("EfficiencyData.bin");
            // Create a stream to write objects to the file
            // This stream will write each efficiency record to the file
            ObjectOutputStream oos8 = new ObjectOutputStream(new FileOutputStream(file8));

            // Add efficiency report - Worker W-101 on MAC-01, handled 520 tasks, rated Excellent
            // Worker ID: W-101, Machine ID: MAC-01, Tasks Completed: 520, Rating: Excellent
            oos8.writeObject(new EfficiencyReport("W-101", "MAC-01", 520, "Excellent"));
            // Add efficiency report - Worker W-102 on MAC-02, handled 300 tasks, rated Good
            // Worker ID: W-102, Machine ID: MAC-02, Tasks Completed: 300, Rating: Good
            oos8.writeObject(new EfficiencyReport("W-102", "MAC-02", 300, "Good"));
            // Add efficiency report - Worker W-103 on MAC-03, handled 150 tasks, rated Needs Improvement
            // Worker ID: W-103, Machine ID: MAC-03, Tasks Completed: 150, Rating: Needs Improvement
            oos8.writeObject(new EfficiencyReport("W-103", "MAC-03", 150, "Needs Improvement"));

            // Close the file - this saves all the efficiency data
            // This is important - without closing, data may not be saved
            oos8.close();
            // Show success message
            // This confirms that the efficiency data file was created successfully
            System.out.println("SUCCESS: EfficiencyData.bin created.");
        } catch (Exception e) {
            // If any error happens, print the error message
            e.printStackTrace();
        }
    }
}