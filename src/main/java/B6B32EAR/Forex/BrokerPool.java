/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex;

import B6B32EAR.Forex.jpa.dao.UserJpaController;
import B6B32EAR.Forex.jpa.entities.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author zero
 */
public class BrokerPool {
    
    private HashMap<Integer, Broker> brokerlist = new HashMap<>();
    
    @Autowired 
    private UserJpaController ujc;
    private List<User> users;
    public void initialize(){
        users = ujc.findAll();
        for(User user : users){
            Broker temp = new Broker();
            temp.start();
            brokerlist.put(user.getIduser(), temp);
            
        }
    }
}
