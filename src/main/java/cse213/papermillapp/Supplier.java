// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to save objects to file
import java.io.Serializable;

// This class stores information about a supplier or vendor
// A supplier provides materials to the paper mill
public class Supplier implements Serializable {
    // The unique ID of this supplier
    private String supplierId;
    
    // The name of the supplier company
    private String companyName;
    
    // The phone number to contact this supplier
    private String contactNumber;
    
    // The type of material this supplier provides
    private String materialSupplied;

    // This creates a new supplier with their details
    public Supplier(String supplierId, String companyName, String contactNumber, String materialSupplied) {
        // Save the supplier ID
        this.supplierId = supplierId;
        // Save the company name
        this.companyName = companyName;
        // Save the contact number
        this.contactNumber = contactNumber;
        // Save what material they supply
        this.materialSupplied = materialSupplied;
    }

    // Get the supplier ID
    public String getSupplierId() {
        return supplierId;
    }

    // Get the company name
    public String getCompanyName() {
        return companyName;
    }

    // Get the contact number
    public String getContactNumber() {
        return contactNumber;
    }

    // Get the material supplied
    public String getMaterialSupplied() {
        return materialSupplied;
    }

    // This creates a simple text version of the supplier for display
    @Override
    public String toString() {
        // Return a string like: Supplier ID: S1, Name: ABC Company, Contact Number: 1234567890, Material Supplied: Paper
        return "Supplier ID: " + supplierId + ", Name: " + companyName + ", Contact Number: " + contactNumber + ", Material Supplied: " + materialSupplied;
    }
}
