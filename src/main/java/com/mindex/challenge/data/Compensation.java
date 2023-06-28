package com.mindex.challenge.data;

import java.time.LocalDateTime;

public class Compensation {
    private Employee employee;
    private int salary;
    private LocalDateTime effectiveDate;

    public Compensation() {
    }

    public Employee getEmployee(){
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
