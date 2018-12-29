/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.controller;

import B6B32EAR.Forex.jpa.entities.User;
import B6B32EAR.Forex.services.ManagementService;
import B6B32EAR.Forex.services.UserService;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
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
public class LoginController {

    @PersistenceContext
    protected EntityManager entityManager;
    
    @Autowired
    private UserService users;
    
    @Autowired
    private ManagementService ms;
    
    private Status status = null;
    
    @RequestMapping("/login")
    @ResponseBody
    public JSONObject index(@RequestParam Map<String, String> body) {
        User u = new User();
        u.setMail(body.get("mail"));
        u.setSystempassword(body.get("systempassword"));
        
        try {
            this.status = users.login(u);
        } catch (Status ex) {
            this.status = ex;
            HashMap temp = new HashMap<String, String>();
            temp.put("mail", body.get("mail"));
            temp.put("systempassword", body.get("systempassword"));
            try {
                users.registerUser(temp);
            } catch (Status status1) {
                status1.printStackTrace();
            }
        }
        return this.status.toJSON();
    }

    @RequestMapping("/xtbsession")
    @ResponseBody
    public JSONObject xtbSession(@RequestParam Map<String, String> body) {
        
        try {
            this.status = ms.sendForexSessionId(body);
        } catch (Status ex) {
            this.status = ex;
        }
        return this.status.toJSON();
        
    }

}