package cse213.papermillapp;

import java.io.Serializable;

public class RawMaterialUsage implements Serializable {
    private String machineId;
    private int amountUsed;

    public RawMaterialUsage(String machineId, int amountUsed) {
        this.machineId = machineId;
        this.amountUsed = amountUsed;
    }

    public String getMachineId() { return machineId; }
    public int getAmountUsed() { return amountUsed; }
}