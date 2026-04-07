package cse213.papermillapp;

public class ProductionRecord {
    private String machineId;
    private  int ProducedQuantity;

    public ProductionRecord(String machineId, int producedQuantity) {
        this.machineId = machineId;
        ProducedQuantity = producedQuantity;
    }

    public String getMachineId() {
        return machineId;
    }

    public int getProducedQuantity() {
        return ProducedQuantity;
    }
}
