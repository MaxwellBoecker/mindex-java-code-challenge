package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    
    private String compensationUrl;
    private String compensationIdUrl;
    private String employeeUrl;
    private Employee createdEmployee;

    @Autowired 
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
        employeeUrl = "http://localhost:" + port + "/employee";

        // setup employee
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        
        createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
    }

    @Test
    public void testCreate() {    
        int salary = 127000;
        LocalDateTime effectiveDate = LocalDateTime.now();
        
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(createdEmployee);
        testCompensation.setEffectiveDate(effectiveDate);
        testCompensation.setSalary(salary);
        
        // Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, createdEmployee.getEmployeeId()).getBody();

        assertCompensationEquivalence(createdCompensation, readCompensation);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        Employee expectedEmployee = expected.getEmployee();
        Employee actualEmployee = actual.getEmployee();

        assertEquals(expectedEmployee.getFirstName(), actualEmployee.getFirstName());
        assertEquals(expectedEmployee.getLastName(), actualEmployee.getLastName());
        assertEquals(expectedEmployee.getDepartment(), actualEmployee.getDepartment());
        assertEquals(expectedEmployee.getPosition(), actualEmployee.getPosition());

        assertEquals(expected.getSalary(), actual.getSalary());
        assertNotNull(expected.getEffectiveDate());
    }

    
}
