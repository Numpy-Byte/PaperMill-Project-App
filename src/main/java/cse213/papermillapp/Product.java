// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to save objects to file
import java.io.Serializable;

// This class stores information about a finished product
// Products are what the paper mill makes and sells
public class Product implements Serializable {
    // The unique ID of this product
    private int id;
    
    // The type of product (like "paper", "cardboard", etc)
    private String type;
    
    // The name of this product
    private String name;
    
    // The selling price of this product
    private float price;

    // This creates a new product with its details
    public Product(int id, String type, String name, float price) {
        // Save the ID
        this.id = id;
        // Save the type
        this.type = type;
        // Save the name
        this.name = name;
        // Save the price
        this.price = price;
    }

    // This checks if the product has expired
    // For now, it always returns false (products don't expire)
    public boolean isExpired() {
        // Return false because products don't expire in this system
        return  false;
    }
}
