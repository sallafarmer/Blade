package ca.farmersedge.blade.core.azure

import java.io.{BufferedOutputStream, FileOutputStream}
import java.time.Instant
import java.util.concurrent.Executors

import com.microsoft.azure.eventhubs._
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters._

object TestEventHub extends LazyLogging {

  class Client {
    val connStr = "Endpoint=sb://iothub-ns-iothubsall-458246-7a676be332.servicebus.windows.net/;SharedAccessKeyName=iothubowner;SharedAccessKey=hAJBPRaRWzhgoi6kfpQ+OLHGLN96YyO/x/1UpiypLz4="
    //HostName=iothubsalla.azure-devices.net;DeviceId=a1;SharedAccessKey=7HV7/0wFCsQiRjAc/cJnIfXpTXZNe2irAsu1smsu4Jo="

    private val executorService = Executors.newSingleThreadExecutor
    private val ehClient = EventHubClient.createSync(connStr, executorService)

    def send(payload: String): Unit = {
      val payloadBytes = payload.getBytes("UTF-8")
      val sendEvent = EventData.create(payloadBytes)
      ehClient.sendSync(sendEvent)
    }

  }
  def main(args: Array[String]): Unit = {

    logger.info(s"Starting Event Hub Client")

    val payload = "Test AMQP message from JMS"
 //   val c = new Client
 //   c.send(payload)

    /* val w = new Wrapper();

     val sendEvent = w.create(payloadBytes)



     val ehClient = w.createSync(connStr.toString, executorService)
     ehClient.sendSync(sendEvent)
 */
    // close the client at the end of your program

    val connectionStr = new ConnectionStringBuilder().setNamespaceName("salla-eventhub1").setEventHubName("salla-eventhub10")
      .setSasKeyName("RootManageSharedAccessKey").setSasKey("m5YILRETxAB116ob6Qd1KYoW2egAPCsoGThzmt/M3gA=")

    val partitionId = "0"
    val fileBaseName = "/Users/sridharalla/git/Blade/out.log"


    println(s"Starting eventHub receiver, listing to partition $partitionId and outputing to $fileBaseName")

    val executorService = Executors.newSingleThreadExecutor
    val ehClient = EventHubClient.createSync(connectionStr.toString, executorService)

    val receiver = ehClient.createReceiverSync(EventHubClient.DEFAULT_CONSUMER_GROUP_NAME,  partitionId, EventPosition.fromEnqueuedTime(Instant.EPOCH))

    def saveBytes(bytes: Array[Byte], fileOut: String): Unit = {
      println(s"saving payload to $fileOut")
      val bos = new BufferedOutputStream(new FileOutputStream(fileOut))
      bos.write(bytes)
      bos.close()
    }

    def filename = s"$fileBaseName.eventhub"

    while (true) {

      receiver.receiveSync(10).asScala.foreach{e => println(e);saveBytes(e.getBytes, filename)}
    }


  }
}

