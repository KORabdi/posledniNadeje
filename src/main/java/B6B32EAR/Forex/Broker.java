/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex;

import B6B32EAR.Forex.jpa.entities.User;
import B6B32EAR.Forex.util.CodeAndDecode;
import org.springframework.beans.factory.annotation.Autowired;
import pro.xstore.api.message.command.APICommandFactory;
import pro.xstore.api.message.response.LoginResponse;
import pro.xstore.api.sync.Credentials;
import pro.xstore.api.sync.SyncAPIConnector;

/**
 *
 * @author zero
 */
public class Broker extends Thread {

    User user;

    @Autowired
    SyncAPIConnector connector;

    @Autowired
    CodeAndDecode codeAndDecode;

    Broker(User u){
        this.user = u;
    }

    boolean initialize(){
        //Credentials credentials = new Credentials("10580512", "xoh43173");
        Credentials credentials = new Credentials(this.user.getNumber(), this.codeAndDecode.decrypt(this.user.getXtbpassword()));

        // Create and execute new login command
        LoginResponse loginResponse = APICommandFactory.executeLoginCommand(connector, credentials);

        // Check if user logged in correctly
        return(loginResponse.getStatus());
    }
}
