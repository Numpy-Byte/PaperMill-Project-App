// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to store a list of products
import java.util.ArrayList;

// An InventoryOfficer is a type of employee who manages products in the warehouse
public class InventoryOfficer extends Employee {
    // List of products currently in inventory
    private ArrayList<Product> inventoryList;

    // This creates a new Inventory Officer
    public InventoryOfficer(int id, String name, String pw, String dept, String designation, float salary) {
        // Call the parent Employee class to set basic details
        super(id, name, pw, dept, designation, salary);
        // Create an empty list for storing products
        this.inventoryList = new ArrayList<>();
    }

    // This updates the inventory officer's profile
    @Override
    public void updateProfile() {
        // Print a message to show the profile was updated
        System.out.println("Inventory Officer Profile");
    }

    // This adds a product to the inventory
    public boolean processProduct(Product p1) {
        // Check if the product has expired
        if (p1.isExpired()) {
            // Don't add expired products, return false
            return  false;
        } else {
            // Add the good product to the list and return true
            inventoryList.add(p1);
            return true;
        }
    }
}
