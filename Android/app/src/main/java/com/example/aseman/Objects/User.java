package com.example.aseman.Objects;

public class User {
    int id;
    String username;
    City city;
    Department department;
    boolean isManager; // manager of this department
    boolean isMainManager; // main manager of company

    public User(int id, String username, City city, Department department, boolean isManager, boolean isMainManager) {
        this.id = id;
        this.username = username;
        this.city = city;
        this.department = department;
        this.isManager = isManager;
        this.isMainManager = isMainManager;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public boolean isMainManager() {
        return isMainManager;
    }

    public void setMainManager(boolean mainManager) {
        isMainManager = mainManager;
    }
}
