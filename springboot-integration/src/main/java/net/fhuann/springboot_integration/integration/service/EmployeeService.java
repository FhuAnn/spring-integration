package net.fhuann.springboot_integration.integration.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import net.fhuann.springboot_integration.integration.model.Employee;

@Service
public class EmployeeService {
    // Service Activators
    // Get call
    @ServiceActivator(inputChannel = "${channel.input}")
    public void getEmployeeName(Message<String> name) {
        // Implementation for fetching employee name
        MessageChannel replyChannel = (MessageChannel) name.getHeaders().getReplyChannel();
        replyChannel.send(name);
    }

    // Post call
    @ServiceActivator(inputChannel = "${channel.hire.employee}", outputChannel = "process-hire-emp-channel")
    public Message<Employee> hireEmployee(Message<Employee> employee) {
        return employee;
    }

    @ServiceActivator(inputChannel = "process-hire-emp-channel", outputChannel = "get-emp-status-channel")
    public Message<Employee> processEmployee(Message<Employee> employee) {
        employee.getPayload().setEmployeeStatus("Pernament Role");
        return employee;
    }

    @ServiceActivator(inputChannel = "get-emp-status-channel")
    public void getEmployeeStatus(Message<Employee> employee) {
        MessageChannel replyChannel = (MessageChannel) employee.getHeaders().getReplyChannel();
        replyChannel.send(employee);
    }

    // Transformers
    @Transformer(inputChannel = "${channel.employee.status.channel}", outputChannel = "output-channel")
    public Message<String> convertToUppercase(Message<String> status) {
        String payload = status.getPayload();
        Message<String> upperCaseStatus = MessageBuilder.withPayload(payload.toUpperCase())
                .copyHeaders(status.getHeaders()).build();
        return upperCaseStatus;
    }

    // COMMON OUTPUT CHANNELS
    @ServiceActivator(inputChannel = "output-channel")
    public void consumeStringMessaage(Message<String> message) {
        System.out.println("Consumed message: " + message.getPayload());
        MessageChannel replyChannel = (MessageChannel) message.getHeaders().getReplyChannel();
        replyChannel.send(message);
    }

}
