package com.mindex.challenge.data;
import com.mindex.challenge.data.Employee;

// todo, extend Employee class to get the methods?
public class ReportingStructure {
    private int numberOfReports;
    private Employee employee;

    public ReportingStructure(){      
    }

    public int getNumberOfReports(){
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports){
        this.numberOfReports = numberOfReports;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}