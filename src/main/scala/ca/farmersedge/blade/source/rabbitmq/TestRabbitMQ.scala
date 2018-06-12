package ca.farmersedge.blade.source.rabbitmq

import com.rabbitmq.client._

object TestRabbitMQ {

  def produce() {
    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    val connection: Connection = factory.newConnection
    val channel: Channel = connection.createChannel

    val QUEUE_NAME = "test1"

    channel.queueDeclare(QUEUE_NAME, true, false, false, null)
    val message = "Hello World!"
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes)
    System.out.println(" [x] Sent '" + message + "'")

    channel.close()
    connection.close()

  }

  def consume() {
    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    val connection: Connection = factory.newConnection
    val channel: Channel = connection.createChannel

    val QUEUE_NAME = "test1"

    channel.queueDeclare(QUEUE_NAME, true, false, false, null)

    val consumer = new DefaultConsumer(channel) {
      override def handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: Array[Byte]): Unit = {
        val message = new String(body, "UTF-8")
        println(" [x] Received '" + message + "'")
        super.handleDelivery(consumerTag, envelope, properties, body)
      }
    }

    channel.basicConsume("test1", consumer)
    channel.close()
    connection.close()

  }

  def main(args: Array[String]): Unit = {
    produce()
    produce()
    produce()
    consume()
  }
}
