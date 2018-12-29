/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zero
 */
@Entity
@Table(name = "user_forex")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserForex.findAll", query = "SELECT u FROM UserForex u")
    , @NamedQuery(name = "UserForex.findByIduserForex", query = "SELECT u FROM UserForex u WHERE u.iduserForex = :iduserForex")
    , @NamedQuery(name = "UserForex.findByPriority", query = "SELECT u FROM UserForex u WHERE u.priority = :priority")})
public class UserForex implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iduser_forex")
    private Integer iduserForex;
    @Column(name = "priority")
    private Integer priority;
    @JoinColumn(name = "fkforex", referencedColumnName = "idforex")
    @ManyToOne(optional = false)
    private Forex fkforex;
    @JoinColumn(name = "fkuser", referencedColumnName = "iduser")
    @ManyToOne(optional = false)
    private User fkuser;

    public UserForex() {
    }

    public UserForex(Integer iduserForex) {
        this.iduserForex = iduserForex;
    }

    public Integer getIduserForex() {
        return iduserForex;
    }

    public void setIduserForex(Integer iduserForex) {
        this.iduserForex = iduserForex;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Forex getFkforex() {
        return fkforex;
    }

    public void setFkforex(Forex fkforex) {
        this.fkforex = fkforex;
    }

    public User getFkuser() {
        return fkuser;
    }

    public void setFkuser(User fkuser) {
        this.fkuser = fkuser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iduserForex != null ? iduserForex.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserForex)) {
            return false;
        }
        UserForex other = (UserForex) object;
        if ((this.iduserForex == null && other.iduserForex != null) || (this.iduserForex != null && !this.iduserForex.equals(other.iduserForex))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "B6B32EAR.Forex.jpa.entities.UserForex[ iduserForex=" + iduserForex + " ]";
    }
    
}
