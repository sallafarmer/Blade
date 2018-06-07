package ca.farmersedge.blade.core.elastic

import java.io.File
import java.net.InetAddress

import com.typesafe.config.ConfigFactory
import org.elasticsearch.action.ActionResponse
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.transport.client.PreBuiltTransportClient

object ESClient {

  val pathName = "src/main/resources/application.conf"

  val config = ConfigFactory.parseFile(new File(pathName)).resolve()

  System.setProperty("es.set.netty.runtime.available.processors", "false")

  val settings = Settings
    .builder()
    .put("cluster.name", config.getString("elastic.cluster"))
    .build();
  val client = new PreBuiltTransportClient(settings)
    .addTransportAddress(
      new InetSocketTransportAddress(
        InetAddress.getByName(config.getString("elastic.hosts")),
        9300))

  var docid = ""
  var lastJson = ""

  def publish(appName: String, json: String): ActionResponse = {
    lastJson = json
    val response = client
      .prepareIndex(config.getString("elastic.index"), "job")
      .setId(docid)
      .setSource(json, XContentType.JSON)
      .get()
    response
  }

}
