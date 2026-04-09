package cse213.papermillapp;

import java.io.Serializable;

public class ProductionRecord implements Serializable {
    private String machineId;
    private int producedQuantity;

    public ProductionRecord(String machineId, int producedQuantity) {
        this.machineId = machineId;
        this.producedQuantity = producedQuantity;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public int getProducedQuantity() {
        return producedQuantity;
    }

    public void setProducedQuantity(int producedQuantity) {
        this.producedQuantity = producedQuantity;
    }
}