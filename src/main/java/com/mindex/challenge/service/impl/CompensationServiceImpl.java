package com.mindex.challenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation){
        LOG.debug("Creating compensation [{}]", compensation);
        return compensationRepository.insert(compensation);
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Reading employee with id [{}]", id);
        return compensationRepository.findByEmployeeId(id);
    }
}
