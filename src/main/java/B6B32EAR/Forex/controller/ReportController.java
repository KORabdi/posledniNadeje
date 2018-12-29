/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.controller;

import B6B32EAR.Forex.ForexApplication;
import B6B32EAR.Forex.jpa.entities.User;
import B6B32EAR.Forex.services.UserService;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
public class ReportController {

    @PersistenceContext
    protected EntityManager entityManager;
    
    @Autowired
    UserService users;
    
    @RequestMapping("/report")
    @ResponseBody
    public JSONObject index(@RequestParam Map<String, String> body) {
        try {
            users.validateSession(body);
        } catch (Status ex) {
            return ex.toJSON();
        }
        Random r = new Random();
        Integer number = r.nextInt(999999);
        Status s = new Status(0);
        s.put("cash", number.toString());
        return s.toJSON();
    }

}