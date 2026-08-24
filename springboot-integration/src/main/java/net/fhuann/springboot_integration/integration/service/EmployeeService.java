package net.fhuann.springboot_integration.integration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import net.fhuann.springboot_integration.integration.model.Employee;

@Service
public class EmployeeService {
    // ###################### Service Activators #####################
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

    // ###################### TRANSFORMERS #####################
    @Transformer(inputChannel = "${channel.employee.status}", outputChannel = "output-channel")
    public Message<String> convertToUppercase(Message<String> status) {
        String payload = status.getPayload();
        Message<String> upperCaseStatus = MessageBuilder.withPayload(payload.toUpperCase())
                .copyHeaders(status.getHeaders()).build();
        return upperCaseStatus;
    }

    // ###################### SPLITTERS #####################
    @Splitter(inputChannel = "${channel.employee.managers}", outputChannel = "managers-channel")
    List<Message<String>> splitMessage(Message<?> message) {
        List<Message<String>> messages = new ArrayList<Message<String>>();
        String[] msgSplits = message.getPayload().toString().split(",");
        for (String split : msgSplits) {
            Message<String> msg = MessageBuilder.withPayload(split)
                    .copyHeaders(message.getHeaders()).build();
            messages.add(msg);
        }
        return messages;
    }

    // ###################### AGGREGATORS #####################
    @Aggregator(inputChannel = "managers-channel", outputChannel = "output-channel")
    Message<String> getAllManagers(List<Message<String>> messages) {
        StringJoiner joiner = new StringJoiner(" & ", "[", "]");
        for (Message<String> message : messages) {
            joiner.add(message.getPayload());
        }
        String managers = joiner.toString();
        System.out.println("Managers: " + managers);
        Message<String> updatedMsg = MessageBuilder.withPayload(managers).build();
        return updatedMsg;
    }

    // ###################### FILTER #####################
    // when the return is true, the message will be sent to the output channel,
    // otherwise it will be discarded.
    @Filter(inputChannel = "${channel.employee.designation}", outputChannel = "output-channel")
    boolean filter(Message<?> message) {
        String msg = message.getPayload().toString();
        return msg.contains("Dev");
    }

    // ###################### COMMON OUTPUT CHANNELS #####################
    @ServiceActivator(inputChannel = "output-channel")
    public void consumeStringMessaage(Message<String> message) {
        System.out.println("Consumed message: " + message.getPayload());
        MessageChannel replyChannel = (MessageChannel) message.getHeaders().getReplyChannel();
        replyChannel.send(message);
    }

}
