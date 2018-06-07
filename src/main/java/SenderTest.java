import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SenderTest {

    public static void main(String[] args)
            throws EventHubException, ExecutionException, InterruptedException, IOException {
        System.out.println("SenderTest");

        //Endpoint=sb://salla1.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=iNAHAuk8DgLhGCNMplyumXqnDPfQcYZcdEN81mOO4NE=

       // String connStr = "HostName=iothubsalla.azure-devices.net;DeviceId=a1;SharedAccessKey=7HV7/0wFCsQiRjAc/cJnIfXpTXZNe2irAsu1smsu4Jo=";
        final ConnectionStringBuilder connStr = new ConnectionStringBuilder()
                .setNamespaceName("salla1") // to target National clouds - use .setEndpoint(URI)
                .setEventHubName("salla1")
                .setSasKeyName("RootManageSharedAccessKey")
                .setSasKey("iNAHAuk8DgLhGCNMplyumXqnDPfQcYZcdEN81mOO4NE=");

        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Each EventHubClient instance spins up a new TCP/SSL connection, which is expensive.
        // It is always a best practice to reuse these instances. The following sample shows this.
        final EventHubClient ehClient = EventHubClient.createSync(connStr.toString(), executorService);;

        try {
            for (int i = 0; i < 100; i++) {

                String payload = "FarmersEdge Device Telemetry " + Integer.toString(i);

                byte[] payloadBytes = payload.getBytes();
                EventData sendEvent = EventData.create(payloadBytes);

                ehClient.sendSync(sendEvent);
            }
        } finally {
            ehClient.closeSync();
            executorService.shutdown();
        }

    }

}
