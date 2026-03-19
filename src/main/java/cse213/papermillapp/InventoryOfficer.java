package cse213.papermillapp;

import java.util.ArrayList;

public class InventoryOfficer extends Employee {

    private ArrayList<Product> inventoryList;

    public InventoryOfficer(int id, String name, String pw, String dept, String designation, float salary) {
        super(id, name, pw, dept, designation, salary);
        this.inventoryList = new ArrayList<>();
    }

    @Override
    public void updateProfile() {
        System.out.println("Inventory Officer Profile");
    }

    public boolean processProduct(Product p1) {
        if (p1.isExpired()) {
            return  false;
        } else {
            inventoryList.add(p1);
            return true;
        }
    }

}
