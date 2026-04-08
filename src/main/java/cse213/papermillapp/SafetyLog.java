package cse213.papermillapp;

import java.io.Serializable;
import java.time.LocalDate;

public class SafetyLog implements Serializable {
    private String machineId;
    private String date;
    private boolean isApproved;

    public SafetyLog(String machineId, boolean isApproved) {
        this.machineId = machineId;
        this.isApproved = isApproved;
        this.date = LocalDate.now().toString(); // Automatically grabs today's date
    }

    public String getMachineId() { return machineId; }
    public String getDate() { return date; }
    public boolean isApproved() { return isApproved; }
}