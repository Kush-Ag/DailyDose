package com.example.dailydoze.Models;

import java.io.Serializable;

public class DoseItem implements Serializable {
    private String dose;
    private String time;

    public DoseItem(String dose, String time) {
        this.dose = dose;
        this.time = time;
    }

    public DoseItem() {
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
