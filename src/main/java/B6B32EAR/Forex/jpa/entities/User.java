/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.jpa.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author zero
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByIduser", query = "SELECT u FROM User u WHERE u.iduser = :iduser")
    , @NamedQuery(name = "User.findByMail", query = "SELECT u FROM User u WHERE u.mail = :mail")
    , @NamedQuery(name = "User.findBySystempassword", query = "SELECT u FROM User u WHERE u.systempassword = :systempassword")
    , @NamedQuery(name = "User.findByNumber", query = "SELECT u FROM User u WHERE u.number = :number")
    , @NamedQuery(name = "User.findByXtbpassword", query = "SELECT u FROM User u WHERE u.xtbpassword = :xtbpassword")
    , @NamedQuery(name = "User.sessionValidate", query = "SELECT u FROM User u WHERE u.idsession=:idsession AND u.iduser=:iduser")
})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iduser")
    private Integer iduser;
    @Size(max = 2147483647)
    @Column(name = "mail")
    private String mail;
    @Size(max = 2147483647)
    @Column(name = "systempassword")
    private String systempassword;
    @Size(max = 2147483647)
    @Column(name = "xtbnumber")
    private String number;
    @Size(max = 2147483647)
    @Column(name = "xtbpassword")
    private String xtbpassword;
    @Size(max = 2147483647)
    @Column(name = "idsession")
    private String idsession;
    @Size(max = 2147483647)
    @Column(name = "sessiondata")
    private String sessiondata;
    @Column(name = "sessionexpire")
    private Timestamp sessionexpire;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkuser")
    private Set<UserForex> userForexSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkuser")
    @OrderBy("closed DESC")
    private Set<Position> positionSet;
    
    public boolean validate(){
        return !(mail.isEmpty() || systempassword.isEmpty());
    }
    
    @Transient
    public String action="";

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    public User() {
    }

    public String getIdsession() {
        return idsession;
    }

    public void setIdsession(String idsession) {
        this.idsession = idsession;
    }

    public String getSessiondata() {
        return sessiondata;
    }

    public void setSessiondata(String sessiondata) {
        this.sessiondata = sessiondata;
    }

    public Timestamp getSessionexpire() {
        return sessionexpire;
    }

    public void setSessionexpire(Timestamp sessionexpire) {
        this.sessionexpire = sessionexpire;
    }

    public User(Integer iduser) {
        this.iduser = iduser;
    }

    public Integer getIduser() {
        return iduser;
    }

    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSystempassword() {
        return systempassword;
    }

    public void setSystempassword(String systempassword) {
        this.systempassword = systempassword;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getXtbpassword() {
        return xtbpassword;
    }

    public void setXtbpassword(String xtbpassword) {
        this.xtbpassword = xtbpassword;
    }

    @XmlTransient
    public Set<UserForex> getUserForexSet() {
        return userForexSet;
    }

    public void setUserForexSet(Set<UserForex> userForexSet) {
        this.userForexSet = userForexSet;
    }

    @XmlTransient
    public Set<Position> getPositionSet() {
        return positionSet;
    }

    public void setPositionSet(Set<Position> positionSet) {
        this.positionSet = positionSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iduser != null ? iduser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.iduser == null && other.iduser != null) || (this.iduser != null && !this.iduser.equals(other.iduser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "B6B32EAR.Forex.jpa.entities.User[ iduser=" + iduser + " ]";
    }
    
}
