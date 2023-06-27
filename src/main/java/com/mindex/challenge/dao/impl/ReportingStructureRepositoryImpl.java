package com.mindex.challenge.dao.impl;

import com.mindex.challenge.data.ReportingStructure;
import org.springframework.beans.factory.annotation.Autowired;
import com.mindex.challenge.dao.ReportingStructureRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import org.springframework.stereotype.Repository;

@Repository
public class ReportingStructureRepositoryImpl implements ReportingStructureRepository {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure findByEmployeeId(String employeeId) {
        ReportingStructure reportingStructure = new ReportingStructure();
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        reportingStructure.setEmployee(employee);       
        return reportingStructure;
    }
}