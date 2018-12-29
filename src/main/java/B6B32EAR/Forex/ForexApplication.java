package B6B32EAR.Forex;

import B6B32EAR.Forex.config.PersistenceConfig;
import B6B32EAR.Forex.config.RestConfig;
import B6B32EAR.Forex.config.ServiceConfig;
import B6B32EAR.Forex.config.WebMvcConfig;
import pro.xstore.api.message.command.APICommandFactory;
import pro.xstore.api.message.error.APICommandConstructionException;
import pro.xstore.api.message.error.APICommunicationException;
import pro.xstore.api.message.error.APIReplyParseException;
import pro.xstore.api.message.records.SymbolRecord;
import pro.xstore.api.message.response.APIErrorResponse;
import pro.xstore.api.message.response.AllSymbolsResponse;
import pro.xstore.api.message.response.LoginResponse;
import pro.xstore.api.sync.Credentials;
import pro.xstore.api.sync.ServerData;
import pro.xstore.api.sync.SyncAPIConnector;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.net.UnknownHostException;

@SpringBootApplication
@Import({PersistenceConfig.class, RestConfig.class, WebMvcConfig.class, ServiceConfig.class})
public class ForexApplication {
	public static void main(String[] args) {
		//SpringApplication.run(ForexApplication.class, args);









        try {

            // Create new connector
            SyncAPIConnector connector = new SyncAPIConnector(ServerData.ServerEnum.DEMO);

            // Create new credentials
            // TODO: Insert your credentials
            Credentials credentials = new Credentials("10580512", "xoh43173");

            // Create and execute new login command
            LoginResponse loginResponse = APICommandFactory.executeLoginCommand(connector, credentials);

            // Check if user logged in correctly
            if(loginResponse.getStatus()) {

                // Print the message on console
                System.out.println("User logged in");

                // Create and execute all symbols command (which gets list of all symbols available for the user)
                AllSymbolsResponse availableSymbols = APICommandFactory.executeAllSymbolsCommand(connector);

                // Print the message on console
                System.out.println("Available symbols:");

                // List all available symbols on console
                for(SymbolRecord symbol : availableSymbols.getSymbolRecords()) {
                    System.out.println("-> " + symbol.getSymbol() + " Ask: " + symbol.getAsk() + " Bid: " + symbol.getBid());
                }

            } else {

                // Print the error on console
                System.err.println("Error: user couldn't log in!");

            }

            // Close connection
            connector.close();
            System.out.println("Connection closed");

            // Catch errors
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (APICommandConstructionException e) {
            e.printStackTrace();
        } catch (APICommunicationException e) {
            e.printStackTrace();
        } catch (APIReplyParseException e) {
            e.printStackTrace();
        } catch (APIErrorResponse e) {
            e.printStackTrace();
        }
    }

}
