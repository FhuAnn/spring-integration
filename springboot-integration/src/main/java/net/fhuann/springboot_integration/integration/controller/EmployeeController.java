package net.fhuann.springboot_integration.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.fhuann.springboot_integration.integration.gateway.EmployeeGateway;
import net.fhuann.springboot_integration.integration.model.Employee;

@RestController
@RequestMapping("/integrate")
public class EmployeeController {
    @Autowired
    public EmployeeGateway employeeGateway;

    // ###################### SERVICE ACTIVATORS ######################
    @GetMapping(value = "{name}")
    public String getEmployeeName(@PathVariable("name") String name) {
        return employeeGateway.getEmployeeName(name);
    }

    @PostMapping(value = "/hireEmployee")
    public Employee hireEmployee(@RequestBody Employee employee) {
        Message<Employee> response = employeeGateway.hireEmployee(employee);
        return response.getPayload();
    }

    // ###################### TRANSFORMERS #####################
    @GetMapping(value = "/processEmployeeStatus/{status}")
    public String processEmployeeStatus(@PathVariable("status") String status) {
        return employeeGateway.processEmployeeStatus(status);
    }

    // ###################### SPLITTERS #####################
    @GetMapping(value = "/getManagerList/{managers}")
    public String getManagerList(@PathVariable("managers") String managers) {
        return employeeGateway.getManagerList(managers);
    }
}
