package com.dami.hms.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}