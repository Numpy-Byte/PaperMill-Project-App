package cse213.papermillapp;

public class WasteReport {
    private String machineId;
    private int rawMaterialUsed;
    private int paperProduced;
    private double wastePercentage;

    public WasteReport(String machineId, int rawMaterialUsed, int paperProduced, double wastePercentage) {
        this.machineId = machineId;
        this.rawMaterialUsed = rawMaterialUsed;
        this.paperProduced = paperProduced;
        this.wastePercentage = wastePercentage;
    }

    public String getMachineId() { return machineId; }
    public int getRawMaterialUsed() { return rawMaterialUsed; }
    public int getPaperProduced() { return paperProduced; }
    public double getWastePercentage() { return wastePercentage; }
}