package com.example.dailydoze.Models;

import java.util.List;

public class CreateMedRequest {
    private String username;
    private double medDaily;
    private String medName;
    private double medQuantity;
    private String medType;
    private String medLink;
    private List<DoseItem> doses;

    public CreateMedRequest(String username, double medDaily, String medName, double medQuantity, String medType, String medLink, List<DoseItem> doses) {
        this.username = username;
        this.medDaily = medDaily;
        this.medName = medName;
        this.medQuantity = medQuantity;
        this.medType = medType;
        this.medLink = medLink;
        this.doses = doses;
    }

    public CreateMedRequest(){
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getMedDaily() {
        return medDaily;
    }

    public void setMedDaily(double medDaily) {
        this.medDaily = medDaily;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public double getMedQuantity() {
        return medQuantity;
    }

    public void setMedQuantity(double medQuantity) {
        this.medQuantity = medQuantity;
    }

    public String getMedType() {
        return medType;
    }

    public void setMedType(String medType) {
        this.medType = medType;
    }

    public String getMedLink() {
        return medLink;
    }

    public void setMedLink(String medLink) {
        this.medLink = medLink;
    }

    public List<DoseItem> getDoses() {
        return doses;
    }

    public void setDoses(List<DoseItem> doses) {
        this.doses = doses;
    }
}
