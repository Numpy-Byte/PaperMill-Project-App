package cse213.papermillapp;

public class Product {
    private int id;
    private String type;
    private String name;
    private float price;

    public Product(int id, String type, String name, float price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public boolean isExpired() {
        return  false;
    }
}
