// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to save objects to file
import java.io.Serializable;

// This class stores information about a raw material used in production
// Materials are things like paper pulp, chemicals, or other supplies
public class Material implements Serializable {
    // Unique ID to identify this material
    private String id;
    
    // Name of the material
    private String name;
    
    // Category or type of material (like "paper", "chemical", etc)
    private String category;
    
    // How much of this material we have in stock
    private int currentStock;
    
    // Minimum amount we want to keep in stock before we need more
    private int minThreshold;
    
    // Cost of one unit of this material
    private float unitCost;

    // This creates a new material with all its details
    public Material(String id, String name, String category, int currentStock, int minThreshold, float unitCost) {
        // Save the ID
        this.id = id;
        // Save the name
        this.name = name;
        // Save the category
        this.category = category;
        // Save the current stock
        this.currentStock = currentStock;
        // Save the minimum threshold
        this.minThreshold = minThreshold;
        // Save the unit cost
        this.unitCost = unitCost;
    }

    // Get the material ID
    public String getId() { return id; }
    
    // Get the material name
    public String getName() { return name; }
    
    // Get the material category
    public String getCategory() { return category; }
    
    // Get the current stock amount
    public int getCurrentStock() { return currentStock; }
    
    // Get the minimum threshold
    public int getMinThreshold() { return minThreshold; }
    
    // Get the unit cost
    public float getUnitCost() { return unitCost; }

    // This updates the current stock amount
    public void setCurrentStock(int currentStock) {
        // Save the new stock amount
        this.currentStock = currentStock;
    }

    // This creates a simple text version of the material for display
    @Override
    public String toString() {
        // Return a string like: Material{name='paper', stock=100, cost=$10.0}
        return "Material{" + "name='" + name + '\'' + ", stock=" + currentStock + ", cost=$" + unitCost + '}';
    }
}