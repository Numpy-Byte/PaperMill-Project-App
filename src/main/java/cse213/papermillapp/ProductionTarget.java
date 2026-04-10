// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to save objects to file
import java.io.Serializable;

// This class stores information about a production target
// A production target is a goal for how much product should be made
public class ProductionTarget implements Serializable {
    // This ID is used when saving to file, helps Java recognize the version
    private static final long serialVersionUID = 1L;
    
    // The name of the product we want to make
    private String productName;
    
    // How many of this product we want to make
    private int targetQuantity;

    // This creates a new production target with a name and quantity
    public ProductionTarget(String productName, int targetQuantity) {
        // Save the product name
        this.productName = productName;
        // Save the target quantity
        this.targetQuantity = targetQuantity;
    }

    // This gets the product name
    public String getProductName() {
        return productName;
    }

    // This gets the target quantity
    public int getTargetQuantity() {
        return targetQuantity;
    }
}
