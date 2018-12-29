/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.services;

import B6B32EAR.Forex.controller.Status;
import B6B32EAR.Forex.jpa.dao.UserJpaController;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author zero
 */
@Service
public class ManagementService {
    
    @Autowired
    UserJpaController ujc;
    
    public Status sendForexSessionId(Map<String, String> body) throws Status{
        throw new Status(500);
        
    }
}
