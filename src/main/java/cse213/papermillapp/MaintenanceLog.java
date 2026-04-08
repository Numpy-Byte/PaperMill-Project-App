package cse213.papermillapp;

import java.io.Serializable;

public class MaintenanceLog implements Serializable {
    private String date;
    private String machineId;
    private String maintenanceType;
    private String remarks;

    public MaintenanceLog(String date, String machineId, String maintenanceType, String remarks) {
        this.date = date;
        this.machineId = machineId;
        this.maintenanceType = maintenanceType;
        this.remarks = remarks;
    }

    public String getDate() { return date; }
    public String getMachineId() { return machineId; }
    public String getMaintenanceType() { return maintenanceType; }
    public String getRemarks() { return remarks; }
}