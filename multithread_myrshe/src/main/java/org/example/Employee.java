package org.example;

public class Employee {
    private String name;
    private int maximumHours;
    private int workedHours;

    public Employee(String name, int maxWorkHours) {
        this.name = name;
        this.maximumHours = maxWorkHours;
    }

    public String getName() {
        return name;
    }

    public int getMaximumHours() {
        return maximumHours;
    }

    public int getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(int workedHours) {
        this.workedHours = workedHours;
    }

    public void setMaximumHours(int maximumHours) {
        this.maximumHours = maximumHours;
    }
}
