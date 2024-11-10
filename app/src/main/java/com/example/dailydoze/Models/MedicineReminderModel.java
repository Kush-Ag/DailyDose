package com.example.dailydoze.Models;

public class MedicineReminderModel {
    private String medName, medDose, medTime;

    public MedicineReminderModel(String medName, String medDose, String medTime) {
        this.medName = medName;
        this.medDose = medDose;
        this.medTime = medTime;
    }

    public MedicineReminderModel() {
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedDose() {
        return medDose;
    }

    public void setMedDose(String medDose) {
        this.medDose = medDose;
    }

    public String getMedTime() {
        return medTime;
    }

    public void setMedTime(String medTime) {
        this.medTime = medTime;
    }
}
