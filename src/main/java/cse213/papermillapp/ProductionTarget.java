package cse213.papermillapp;

public class ProductionTarget {
    private String productName;
    private int targetQuantity;

    public ProductionTarget(String productName, int targetQuantity) {
        this.productName = productName;
        this.targetQuantity = targetQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getTargetQuantity() {
        return targetQuantity;
    }
}
