package com.mindex.challenge.dao.impl;

import com.mindex.challenge.data.ReportingStructure;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mindex.challenge.dao.ReportingStructureRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class ReportingStructureRepositoryImpl implements ReportingStructureRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureRepositoryImpl.class);
    
    @Autowired
    private EmployeeRepository employeeRepository;

    private int numberOfReports;

    @Override
    public ReportingStructure findByEmployeeId(String employeeId) {
        ReportingStructure reportingStructure = new ReportingStructure();
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        reportingStructure.setEmployee(employee);
        
        findAllDirectReports(employee.getDirectReports());
        
        reportingStructure.setNumberOfReports(numberOfReports);
        numberOfReports = 0;
        return reportingStructure;
    }

    // This recursive function to expand the direct reports seemed funny at first. but the reason why I went for it was 
    // because adding the DbRef and Id annotations on the Employee class brought back the full info for all nested employees
    // and I though maybe this data was too much for what we needed. We want the current employee and the number of reports, don't need all the other stuff as well

    // signature should be Employee, not List
    private void findAllDirectReports(List<Employee> reports) {
        if(reports == null) {
            return;
        }
        for (Iterator<Employee> directReports = reports.iterator(); directReports.hasNext();){
            Employee currentEmployee = directReports.next();
            this.numberOfReports += 1;
            LOG.info("processing employeeId");

            Employee fullEmployeeInfo = expandEmployeeInfo(currentEmployee);

            List<Employee> directEmployeeReports = fullEmployeeInfo.getDirectReports();
            if(directEmployeeReports != null && !fullEmployeeInfo.getDirectReports().isEmpty()) {
                findAllDirectReports(fullEmployeeInfo.getDirectReports());
            }
        }
    }

    private Employee expandEmployeeInfo(Employee employee) {
        return employeeRepository.findByEmployeeId(employee.getEmployeeId());
    }

}