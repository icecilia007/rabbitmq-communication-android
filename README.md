<h1 align="center"> Android RabbitMQ Communication</h1>

In this project, I used Android Studio Flamingo as the IDE and RabbitMQ server 3.12.1.

# 📁 Access to the project

**You can access the source code of the initial project or download it.**

# :bug: Possible errors:

* Remember to be connected to the same network as your computer, as it can cause errors.
* When I used the default RabbitMQ user, I encountered an error:

 `com.rabbitmq.client.AuthenticationFailureException: ACCESS_REFUSED - Login was refused using authentication mechanism PLAIN. For details see the broker logfile.
  at com.rabbitmq.client.impl.AMQConnection.start(AMQConnection.java:385)
  at com.rabbitmq.client.impl.recovery.RecoveryAwareAMQConnectionFactory.newConnection(RecoveryAwareAMQConnectionFactory.java:64)
  at com.rabbitmq.client.impl.recovery.AutorecoveringConnection.init(AutorecoveringConnection.java:156)
  at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:1213)
  at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:1170)
  at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:1128)
  at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:1290)
  at com.brzas.rabbitmqcommunication.service.RabbitMQService.lambda$startRabbitMQService$0$com-brzas-rabbitmqcommunication-service-RabbitMQService(RabbitMQService.java:101)
  at com.brzas.rabbitmqcommunication.service.RabbitMQService$$ExternalSyntheticLambda1.run(Unknown Source:2)
`
##### If this happens to you, try following the following instructions:

[Create a management user](https://www.ge.com/digital/documentation/proficy-plant-applications/version81/t_gsg_configuring_user_in_RabbitMQ.html)
    

* When I used port 15672, I encountered this error:

`java.util.concurrent.TimeoutException
AMQP Connection WARN com.rabbitmq.client.impl.ForgivingExceptionHandler - An unexpected connection driver error occured (Exception message: Socket closed)
at com.rabbitmq.utility.BlockingCell.get(BlockingCell.java:77)
at com.rabbitmq.utility.BlockingCell.uninterruptibleGet(BlockingCell.java:120)
at com.rabbitmq.utility.BlockingValueOrException.uninterruptibleGetValue(BlockingValueOrException.java:36)
at com.rabbitmq.client.impl.AMQChannel$BlockingRpcContinuation.getReply(AMQChannel.java:502)
at com.rabbitmq.client.impl.AMQConnection.start(AMQConnection.java:326)
at com.rabbitmq.client.impl.recovery.RecoveryAwareAMQConnectionFactory.newConnection(RecoveryAwareAMQConnectionFactory.java:64)
at com.rabbitmq.client.impl.recovery.AutorecoveringConnection.init(AutorecoveringConnection.java:156)
at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:1213)
at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:1170)
at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:1128)
at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:1290)
`

##### If you encounter something similar, it may be that the port is incorrect, so try changing it to 5672.

# :smiley: Appreciations

Here it is, everyone. If you have any questions, feel free to reach out to me. I hope you enjoy it.
:brazil:
