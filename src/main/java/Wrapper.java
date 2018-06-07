import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;

import java.util.concurrent.Executor;

public class Wrapper {
    EventData create(byte [] payloadBytes) {
        return EventData.create(payloadBytes);
    }

    EventHubClient createSync(final String connectionString, final Executor executor) {
        EventHubClient ehClient = null;
        try {
            ehClient = EventHubClient.createSync(connectionString, executor);
        } catch(Exception e) {
            return null;
        }

        return ehClient;
    }
}
