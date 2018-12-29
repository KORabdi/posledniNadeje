/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.services;

import B6B32EAR.Forex.Broker;
import B6B32EAR.Forex.jpa.dao.UserJpaController;
import B6B32EAR.Forex.jpa.entities.User;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author zero
 */
@Service
public class BrokerPool {
    
    private HashMap<Integer, Broker> brokerlist = new HashMap<>();
    
    @Autowired 
    private UserJpaController ujc;
    private List<User> users;

    public BrokerPool(){
        this.initialize();
    }

    public void initialize(){
        users = ujc.findAll();
        for(User user : users){
            Broker temp = new Broker(user);
            temp.start();
            brokerlist.put(user.getIduser(), temp);
            
        }
    }

    public boolean inPool(User u){
        return(brokerlist.containsKey(u.getIduser()));
    }

    public void addBroker(User user){
        if(!this.inPool(user)){

        }
    }

    public void addBroker(User user, String sessionId){
        if(!this.inPool(user)){

        }
    }

}
