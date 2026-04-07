package cse213.papermillapp;

import java.io.Serializable;

public class Worker implements Serializable {
    private String workerId;
    private String name;
    private String assignedMachine;

    public Worker(String workerId, String name, String assignedMachine) {
        this.workerId = workerId;
        this.name = name;
        this.assignedMachine = assignedMachine;
    }

    public String getWorkerId() { return workerId; }
    public String getName() { return name; }
    public String getAssignedMachine() { return assignedMachine; }

    public void setAssignedMachine(String assignedMachine) { this.assignedMachine = assignedMachine; }
}