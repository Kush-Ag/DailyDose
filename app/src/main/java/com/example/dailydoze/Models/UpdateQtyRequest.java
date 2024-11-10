package com.example.dailydoze.Models;

public class UpdateQtyRequest {
    String medicineID;
    double quantity;

    public UpdateQtyRequest(String medicineID, double quantity) {
        this.medicineID = medicineID;
        this.quantity = quantity;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
