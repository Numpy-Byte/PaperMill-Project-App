package cse213.papermillapp;

import java.io.Serializable;

public class Material implements Serializable {
    private String id;
    private String name;
    private String category;
    private int currentStock;
    private int minThreshold;
    private float unitCost;

    public Material(String id, String name, String category, int currentStock, int minThreshold, float unitCost) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.currentStock = currentStock;
        this.minThreshold = minThreshold;
        this.unitCost = unitCost;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getCurrentStock() { return currentStock; }
    public int getMinThreshold() { return minThreshold; }
    public float getUnitCost() { return unitCost; }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    @Override
    public String toString() {
        return "Material{" + "name='" + name + '\'' + ", stock=" + currentStock + ", cost=$" + unitCost + '}';
    }
}