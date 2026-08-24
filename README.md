## CHEATSHEET

![alt text](docs/imgs/spring_integration.png)

## Spring-integration

Spring Integration is an open source framework, that enables lightweight messaging, within Spring based applications, and, also supports integration with external systems.

- Try to calll endpoint and see how the flow integration works with controller-gateway-serviceActivator
  ![alt text](docs/imgs/hire-post.png)

- Try to call enpoint and learn about tranformers
  ![alt text](docs/imgs/practice-transformer.png)

- Try to call endpoint and how the splitter works: it sent finally manager and seee the logs we have several manager name.
  ![alt text](docs/imgs/image.png)
  ![alt text](docs/imgs/splitter-logs.png)
- Try calling endpoint for filter working demo and learn how the filter work: if the method that have filter annotation return true, the flow will transfer to output channel, otherwise it will be dropped. (SeniorDesignUI not contains "Dev so return false)
  ![alt text](docs/imgs/filter.png)
- Try calling enpoint for combination sematic. 
![alt text](docs/imgs/aggregator.png)
## Components

- Producer, Consumer, channel, message, endpoints.
- Tranformer: A transformer takes a message from a channel and creates a new message containing coverted payload or message structure.
- Splitter: The splitter is a SI component whole role is to partition a message into several parts and send the resulting messages to be processed independently.
- Filter: message filters are used to decide whether a message should be passed along orr dropped bassed on some criteria.
- Aggregator: basically a miror-image of the splitter, the aggregator is a type of mesage handler that recieves, multiple messages and combines them into a single message.

<!-- ## The series


- Spring integration
- SI components
- Project creation
- Project execution

## Components

- Producer, Consumer, channel, message, endpoints.
- Transformer and filter:

* A tranformer coverts the content off a message
* A filter determines if a message can continue its wway to the output channel.

![Tranformer and filter](docs/imgs/image-1.png)

- Router: decides to which channel the message will be sent. -->
