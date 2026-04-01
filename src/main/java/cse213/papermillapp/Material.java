package cse213.papermillapp;

public class Material {
    private String id;
    private String name;
    private int currentStock;
    private int minThreshold;

    public Material(String id, String name, int currentStock, int minThreshold) {
        this.id = id;
        this.name = name;
        this.currentStock = currentStock;
        this.minThreshold = minThreshold;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public int getMinThreshold() {
        return minThreshold;
    }
}
