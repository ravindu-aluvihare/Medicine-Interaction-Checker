package com.medicinecheck.model;

import java.util.List;

public class Medicine {
    private Long id;
    private String name;
    private String generic;
    private String category;
    private List<String> keywords;

    // Constructor
    public Medicine(Long id, String name, String generic, String category, List<String> keywords) {
        this.id = id;
        this.name = name;
        this.generic = generic;
        this.category = category;
        this.keywords = keywords;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneric() {
        return generic;
    }

    public void setGeneric(String generic) {
        this.generic = generic;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}