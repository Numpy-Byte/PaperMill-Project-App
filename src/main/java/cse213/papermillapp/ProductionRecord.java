// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to save objects to file
import java.io.Serializable;

// This class stores information about production output
// It keeps track of what a machine made and how many items
public class ProductionRecord implements Serializable {
    // The ID of the machine that made the products
    private String machineId;
    
    // How many items were made by this machine
    private int producedQuantity;

    // The batch number for this production record
    private String batchNumber;

    // This creates a new production record with batch number
    public ProductionRecord(String machineId, int producedQuantity, String batchNumber) {
        // Save the machine ID
        this.machineId = machineId;
        // Save the quantity produced
        this.producedQuantity = producedQuantity;
        // Save the batch number
        this.batchNumber = batchNumber;
    }

    // Get the machine ID
    public String getMachineId() {
        return machineId;
    }

    // Update the machine ID
    public void setMachineId(String machineId) {
        // Save the new machine ID
        this.machineId = machineId;
    }

    // Get the quantity produced
    public int getProducedQuantity() {
        return producedQuantity;
    }

    // Update the quantity produced
    public void setProducedQuantity(int producedQuantity) {
        // Save the new quantity
        this.producedQuantity = producedQuantity;
    }

    // Get the batch number
    public String getBatchNumber() {
        return batchNumber;
    }

    // Update the batch number
    public void setBatchNumber(String batchNumber) {
        // Save the new batch number
        this.batchNumber = batchNumber;
    }
}