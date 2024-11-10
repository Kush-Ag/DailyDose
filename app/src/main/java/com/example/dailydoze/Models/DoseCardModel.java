package com.example.dailydoze.Models;

public class DoseCardModel {
    private String doseQty, doseTime;

    public DoseCardModel(String doseQty, String doseTime) {
        this.doseQty = doseQty;
        this.doseTime = doseTime;
    }

    public DoseCardModel(){
    }

    public String getDoseQty() {
        return doseQty;
    }

    public void setDoseQty(String doseQty) {
        this.doseQty = doseQty;
    }

    public String getDoseTime() {
        return doseTime;
    }

    public void setDoseTime(String doseTime) {
        this.doseTime = doseTime;
    }
}
