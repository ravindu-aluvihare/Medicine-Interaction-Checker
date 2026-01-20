package com.medicinecheck.model;

import java.util.List;

public class Interaction {
    private List<String> drugs;
    private String severity;
    private String description;
    private String recommendation;

    // Constructor
    public Interaction(List<String> drugs, String severity, String description, String recommendation) {
        this.drugs = drugs;
        this.severity = severity;
        this.description = description;
        this.recommendation = recommendation;
    }

    // Getters and Setters
    public List<String> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<String> drugs) {
        this.drugs = drugs;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}