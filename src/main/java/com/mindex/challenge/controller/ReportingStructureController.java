package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mindex.challenge.service.ReportingStructureService;
import com.mindex.challenge.data.ReportingStructure;

@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;

    @GetMapping("/reportingStructure/{id}")
    public ResponseEntity<ReportingStructure> read(@PathVariable String id) {
        LOG.debug("Received reportingStructure read request for id [{}]", id);

        ReportingStructure reportingStructure = reportingStructureService.read(id);
        if(reportingStructure == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(reportingStructure, HttpStatus.OK);
    }


}