package B6B32EAR.Forex.config;

import org.springframework.context.annotation.Bean;
import pro.xstore.api.sync.ServerData;
import pro.xstore.api.sync.SyncAPIConnector;

import java.io.IOException;

public class BrokerConfig {

    @Bean
    SyncAPIConnector connector(){
        SyncAPIConnector connector = null;
        try {
            connector = new SyncAPIConnector(ServerData.ServerEnum.DEMO);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return connector;
    }
}
