package com.example.dailydoze.Models;

import java.io.Serializable;
import java.util.List;

public class MedicationSchema implements Serializable {
    private String _id;
    private double medDaily;
    private String medName;
    private double medQuantity;
    private String medType;
    private String medLink;
    private List<DoseItem> dose;


    public MedicationSchema(String _id, double medDaily, String medName, double medQuantity, String medType, String medLink, List<DoseItem> dose) {
        this._id = _id;
        this.medDaily = medDaily;
        this.medName = medName;
        this.medQuantity = medQuantity;
        this.medType = medType;
        this.medLink = medLink;
        this.dose = dose;
    }

    public MedicationSchema(){
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public List<DoseItem> getDose() {
        return dose;
    }

    public void setDose(List<DoseItem> dose) {
        this.dose = dose;
    }
}
