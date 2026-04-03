package cse213.papermillapp;

import java.io.Serializable;

public class Supplier implements Serializable {
    private String supplierId;
    private String companyName;
    private String contactNumber;
    private String materialSupplied;

    public Supplier(String supplierId, String companyName, String contactNumber, String materialSupplied) {
        this.supplierId = supplierId;
        this.companyName = companyName;
        this.contactNumber = contactNumber;
        this.materialSupplied = materialSupplied;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getMaterialSupplied() {
        return materialSupplied;
    }

    @Override
    public String toString() {
        return "Supplier ID: " + supplierId + ", Name: " + companyName + ", Contact Number: " + contactNumber + ", Material Supplied: " + materialSupplied;
    }
}
