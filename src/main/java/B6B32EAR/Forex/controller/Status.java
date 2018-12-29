/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.controller;

import org.json.simple.JSONObject;

/**
 *
 * @author zero
 */
public class Status extends Exception{
    private int status;
    private String message;
    private JSONObject data = new JSONObject();
    
    public Status(int status) {
        this.status = status;
        switch(this.status){
            case 0:
                this.message = "OK";
                break;
            case 500:
                this.message = "Systémový problém";
                break;
            case 100:
                this.message = "Špatné jméno nebo heslo.";
                break;
            case 300:
                this.message = "Expirace session";
                break;
            case 200:
                this.message = "Špatně vyplněná registrace";
                break;
            case 5:
                this.message = "OK";
                break;
            case 6:
                this.message = "OK";
                break;
            case 7:
                this.message = "OK";
                break;
            
        }
    }
    
    public void put(String key, String value){
        this.data.put(key, value);
    }

    public JSONObject getData() {
        return data;
    }
    
    public Status(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        JSONObject temp = new JSONObject();
        temp.put("data", this.data);
        temp.put("status", status);
        temp.put("message", message);
        return temp.toJSONString();
    }
    
    public JSONObject toJSON(){
        JSONObject temp = new JSONObject();
        temp.put("data", this.data);
        temp.put("status", status);
        temp.put("message", message);
        return temp;
    }
    
}
