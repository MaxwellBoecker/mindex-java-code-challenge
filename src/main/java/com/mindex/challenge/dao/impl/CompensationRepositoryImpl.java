package com.mindex.challenge.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;

@Repository
public class CompensationRepositoryImpl implements CompensationRepository{
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Compensation insert(Compensation compensation) {
        // TODO: check if the employee exists first
        Compensation saved = mongoTemplate.insert(compensation);
        return saved;

    }

    public Compensation findByEmployeeId(String employeeId) {
        Query findCompensation = new Query();
        findCompensation.addCriteria(Criteria.where("employee.employeeId").is(employeeId));
        Compensation compensation = mongoTemplate.findOne(findCompensation, Compensation.class, "compensation");

        return compensation;
    }
}
