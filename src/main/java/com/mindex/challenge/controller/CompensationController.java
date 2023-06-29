package com.mindex.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private CompensationService compensationService;

    @GetMapping("/compensation/{id}")
    public ResponseEntity<Compensation> read(@PathVariable String id) {
        LOG.debug("Received compensation read request for [{}]", id);

        Compensation found = compensationService.read(id);
       
        return responseForRead(found);
    }

    @PostMapping("/compensation")
    public ResponseEntity<Compensation> create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);

        Compensation saved = compensationService.create(compensation);

        return responseForCreate(saved);
    }

    private ResponseEntity<Compensation> responseForRead(Compensation dbResult) {
        if(nullDBResult(dbResult)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return successResponse(dbResult);
    }

    private boolean nullDBResult(Compensation dbResult){
        return dbResult == null;
    }

    private ResponseEntity<Compensation> responseForCreate(Compensation dbResult) {
        if(nullDBResult(dbResult)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return successResponse(dbResult);
    }

    private ResponseEntity<Compensation> successResponse(Compensation dbResult) {
        return new ResponseEntity<>(dbResult, HttpStatus.OK);
    }
}
