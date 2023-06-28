package com.mindex.challenge.dao;

import org.springframework.stereotype.Repository;

import com.mindex.challenge.data.Compensation;


@Repository
public interface CompensationRepository {
    Compensation insert(Compensation compensation);
    Compensation findByEmployeeId(String employeeId);
}
