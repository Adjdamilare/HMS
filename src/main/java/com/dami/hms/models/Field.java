package com.dami.hms.models;

public class Field {
    private String name;
    private String displayName;
    private String type;
    private boolean required;

    public Field(String name, String displayName, String type, boolean required) {
        this.name = name;
        this.displayName = displayName;
        this.type = type;
        this.required = required;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}