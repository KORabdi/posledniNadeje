/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.services;

import B6B32EAR.Forex.controller.Status;
import B6B32EAR.Forex.jpa.dao.UserJpaController;
import B6B32EAR.Forex.jpa.entities.User;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author zero
 */
@Service
public class UserService {
    
    @Autowired
    UserJpaController ujc;
    final static int EXPIRE = 5000;
    
    @Autowired 
    @Qualifier("sha512")
    MessageDigest md;
    
    public Status login(User u) throws Status{
        try{
            if(md == null){
                throw new Status(500, "Systémový problém");
            }
            HashMap <String, String> temp = new HashMap<>();
            temp.put("mail", u.getMail());
            temp.put("systempassword", new String(md.digest((u.getSystempassword()+"666").getBytes()), "UTF-8"));

            List<User> exist = ujc.findBy(temp);
            if(exist != null){
                User user = exist.get(0);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                byte[] idsession = md.digest((user.getMail() + timestamp.toString()).getBytes());
                user.setIdsession(idsession.toString());
                timestamp.setTime(timestamp.getTime()+EXPIRE);
                user.setSessionexpire(timestamp);
                ujc.update(user);
                Status s = new Status(0);
                s.put("iduser", user.getIduser().toString());
                s.put("idsession", user.getIdsession());
                if(user.getXtbpassword() != null && user.getXtbpassword().equals("")){
                    s.setStatus(1);
                }
                return s;
            }
            /*User user = new User();
            user.setMail("admin");
            user.setSystempassword(new String(md.digest(("test666").getBytes()), "UTF-8"));
            ujc.persist(user);*/
            throw new Status(100, "Špatné jméno nebo heslo.");
        } catch(UnsupportedEncodingException e){
            throw new Status(500);
        }
    }
    
    public User validateSession(Map<String, String> body) throws Status{
        User u = this.ujc.validateSession(body);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(u.getSessionexpire().after(now)){
            now.setTime(now.getTime()+EXPIRE);
            u.setSessionexpire(now);
            ujc.update(u);
        } else {
            throw new Status(300);
        }
        return u;
        
    }
    
    public User registerUser(Map<String, String> user) throws Status{
        User u = new User();
        try {
            u.setMail(user.get("mail"));
            u.setSystempassword(new String(md.digest((user.get("systempassword")+"666").getBytes()), "UTF-8"));
            if(!u.validate())
                throw new Status(200, "Špatně vyplněná registrace");
            ujc.persist(u);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            throw new Status(500);
        }
        
        
        return u;
    }
    
    public void deleteUser(User u ){
        ujc.remove(u);
    }
    
}
