package com.mindex.challenge.dao;

import com.mindex.challenge.data.ReportingStructure;
import org.springframework.stereotype.Repository;


@Repository 
public interface ReportingStructureRepository {
    ReportingStructure findByEmployeeId(String employeeId);
}