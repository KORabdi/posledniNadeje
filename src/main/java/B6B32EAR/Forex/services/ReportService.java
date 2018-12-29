/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.services;

import B6B32EAR.Forex.controller.Status;
import B6B32EAR.Forex.jpa.dao.UserJpaController;
import B6B32EAR.Forex.jpa.entities.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author zero
 */
@Service
public class ReportService {
    
    @Autowired
    UserJpaController ujc;
    
    final int PORT = 6576;
    private Socket socket;
    private ObjectOutputStream response;
    private ObjectInputStream request;
    private Status error = null;
    
    public ReportService(){
        try {
            this.socket = new Socket("localhost" , PORT);
            this.response = new ObjectOutputStream(this.socket.getOutputStream());
            this.request = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException ex) {
            this.error = new Status(500, "Systémový problém");
        }
    }
    
    public Status getChange(User u) throws Status {
        if(this.error == null){
            try {
                
                this.response.writeObject("sdad");
            } catch (IOException ex) {
                throw new Status(500, "Systémový problém");
            }
        }
            
        
        return this.error;
    }
}
