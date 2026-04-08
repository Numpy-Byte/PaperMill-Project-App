package cse213.papermillapp;

import java.io.Serializable;

public class EfficiencyReport implements Serializable {
    private String workerId;
    private String machineAssigned;
    private int tasksHandled;
    private String efficiencyRating;

    public EfficiencyReport(String workerId, String machineAssigned, int tasksHandled, String efficiencyRating) {
        this.workerId = workerId;
        this.machineAssigned = machineAssigned;
        this.tasksHandled = tasksHandled;
        this.efficiencyRating = efficiencyRating;
    }

    public String getWorkerId() { return workerId; }
    public String getMachineAssigned() { return machineAssigned; }
    public int getTasksHandled() { return tasksHandled; }
    public String getEfficiencyRating() { return efficiencyRating; }
}