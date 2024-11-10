package com.example.dailydoze.Models;

import java.util.List;

public class FetchedMedicationsResponse {

    private List<MedicationSchema> medicineDetails;

    public FetchedMedicationsResponse(List<MedicationSchema> medicineDetails) {
        this.medicineDetails = medicineDetails;
    }

    public FetchedMedicationsResponse(){
    }

    public List<MedicationSchema> getMedicineDetails() {
        return medicineDetails;
    }

    public void setMedicineDetails(List<MedicationSchema> medicineDetails) {
        this.medicineDetails = medicineDetails;
    }
}
