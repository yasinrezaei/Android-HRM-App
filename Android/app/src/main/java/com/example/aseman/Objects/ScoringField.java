package com.example.aseman.Objects;

public class ScoringField {
    int id;
    String fieldName;

    public ScoringField(int id, String fieldName) {
        this.id = id;
        this.fieldName = fieldName;
    }

    public ScoringField() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
