// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// This class stores information about waste in the production process
// It tracks how much material was used vs how much product was made
public class WasteReport {
    // The ID of the machine
    private String machineId;
    
    // How much raw material was used
    private int rawMaterialUsed;
    
    // How much paper was produced
    private int paperProduced;
    
    // The percentage of waste (material used but not turned into product)
    private double wastePercentage;

    // This creates a new waste report
    public WasteReport(String machineId, int rawMaterialUsed, int paperProduced, double wastePercentage) {
        // Save the machine ID
        this.machineId = machineId;
        // Save the raw material used
        this.rawMaterialUsed = rawMaterialUsed;
        // Save the paper produced
        this.paperProduced = paperProduced;
        // Save the waste percentage
        this.wastePercentage = wastePercentage;
    }

    // Get the machine ID
    public String getMachineId() { return machineId; }
    
    // Get the raw material used
    public int getRawMaterialUsed() { return rawMaterialUsed; }
    
    // Get the paper produced
    public int getPaperProduced() { return paperProduced; }
    
    // Get the waste percentage
    public double getWastePercentage() { return wastePercentage; }
}