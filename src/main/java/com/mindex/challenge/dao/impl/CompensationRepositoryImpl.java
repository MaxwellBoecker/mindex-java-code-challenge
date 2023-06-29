package com.mindex.challenge.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

@Repository
public class CompensationRepositoryImpl implements CompensationRepository{
    private static final String MONGO_COLLECTION = "compensation";
    private static final String EMPLOYEE_ID = "employee.employeeId";
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureRepositoryImpl.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired 
    EmployeeRepository employeeRepository;

    @Override
    public Compensation insert(Compensation compensation) {
        // the validation could be more robust...
        if(!employeeExists(compensation.getEmployee())){
            LOG.warn("could not find employee with id [{}]", compensation.getEmployee().getEmployeeId());
            return null;
        }

        return mongoTemplate.insert(compensation);
    }
    
    private boolean employeeExists(Employee employee) {
        return employeeRepository.findByEmployeeId(employee.getEmployeeId()) != null;
    }

    public Compensation findByEmployeeId(String employeeId) {
        Query query = createQueryForEmployeeCompensation(employeeId);
        return mongoTemplate.findOne(query, Compensation.class, MONGO_COLLECTION);
    }

    private Query createQueryForEmployeeCompensation(String employeeId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(EMPLOYEE_ID).is(employeeId));

        return query;
    }

}
