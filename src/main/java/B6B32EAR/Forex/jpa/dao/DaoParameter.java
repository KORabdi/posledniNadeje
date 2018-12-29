/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.jpa.dao;

import javax.persistence.Parameter;

/**
 *
 * @author zero
 */
public class DaoParameter implements Parameter {
    
    protected Class type;
    protected String name;
    protected Integer position;
    public static Integer number = 0;

    public DaoParameter(String name, Class type) {
        this.type = type;
        this.name = name;
        this.position = number++;
    }
    
    public static void reset(){
        number = 0;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    @Override
    public Class getParameterType() {
        return type;
    }
    
}
