package com.example.dailydoze.Models;

import java.io.Serializable;

public class InventoryModel implements Serializable {

    private String medName, medQty, medDays, medId, medType;

    public InventoryModel(String medName, String medQty, String medDays, String medId, String medType) {
        this.medName = medName;
        this.medQty = medQty;
        this.medDays = medDays;
        this.medId = medId;
        this.medType = medType;
    }

    public InventoryModel() {
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedQty() {
        return medQty;
    }

    public void setMedQty(String medQty) {
        this.medQty = medQty;
    }

    public String getMedDays() {
        return medDays;
    }

    public void setMedDays(String medDays) {
        this.medDays = medDays;
    }

    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }

    public String getMedType() {
        return medType;
    }

    public void setMedType(String medType) {
        this.medType = medType;
    }
}
