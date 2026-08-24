package net.fhuann.springboot_integration.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

import net.fhuann.springboot_integration.integration.model.Employee;

@MessagingGateway
public interface EmployeeGateway {

    // ###################### SERVICE ACTIVATORS ######################
    // Get call
    @Gateway(requestChannel = "${channel.input}")
    public String getEmployeeName(String name);

    // Post call
    @Gateway(requestChannel = "${channel.hire.employee}")
    public Message<Employee> hireEmployee(Employee employee);

    // ###################### TRANSFORMERS ######################
    @Gateway(requestChannel = "${channel.employee.status}")
    public String processEmployeeStatus(String status);

    // ###################### SPLITTERS #####################
    @Gateway(requestChannel = "${channel.employee.managers}")
    public String getManagerList(String managers);
}
