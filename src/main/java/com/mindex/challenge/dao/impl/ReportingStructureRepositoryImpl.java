package com.mindex.challenge.dao.impl;

import com.mindex.challenge.data.ReportingStructure;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mindex.challenge.dao.ReportingStructureRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import org.springframework.stereotype.Repository;

@Repository
public class ReportingStructureRepositoryImpl implements ReportingStructureRepository {
    private int numberOfReports;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    public ReportingStructure findByEmployeeId(String employeeId) {
        ReportingStructure result = new ReportingStructure();
        result = addEmployeeToResult(employeeId, result);

        findAllDirectReports(result.getEmployee().getDirectReports());       
        result.setNumberOfReports(numberOfReports);

        cleanup();
        return result;
    }

    private ReportingStructure addEmployeeToResult(String employeeId, ReportingStructure reportingStructure){
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        reportingStructure.setEmployee(employee);
        return reportingStructure;
    }

    // Design Decision: Implementing the recursive functions below to expand the direct reports seemed funny at first. But the reason why I went for it was 
    // because adding the DbRef and Id annotations on the Employee class, so that I could get every employee with one query, brought back the full info for all nested employees.
    // I thought maybe this data could be too much for what we needed to return to the end user. 
    // We want the current employee and the number of reports, don't need all the other full employees' details as well
    private void findAllDirectReports(List<Employee> reports) {
        if(reports == null) {
            return;
        }
        processReports(reports);
    }

    private void processReports(List<Employee> reports) {
        for (Iterator<Employee> directReports = reports.iterator(); directReports.hasNext();){
            addDirectReport();
            Employee currentEmployee = getCurrentEmployee(directReports);

            if(hasDirectReports(currentEmployee)) {
                findAllDirectReports(currentEmployee.getDirectReports());
            }
        }
    }

    public void addDirectReport(){
        this.numberOfReports += 1;
    }

    private Employee getCurrentEmployee(Iterator<Employee> directReports) {
        Employee currentEmployee = directReports.next();
        return expandEmployeeInfo(currentEmployee);
    }
    
    // necessary to expand info because Employees returned in the directReports field from a Employee.findByEmployeeId() call only have employeeId enumerated
    private Employee expandEmployeeInfo(Employee employee) {
        return employeeRepository.findByEmployeeId(employee.getEmployeeId());
    }

    private boolean hasDirectReports(Employee employee) {
        List<Employee> currentEmployeeDirectReports = employee.getDirectReports();
        return currentEmployeeDirectReports != null && !currentEmployeeDirectReports.isEmpty();
    }

    private void cleanup(){
        this.numberOfReports = 0;
    }

}