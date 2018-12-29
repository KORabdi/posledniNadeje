/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.jpa.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author zero
 */
@Entity
@Table(name = "position")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Position.findAll", query = "SELECT p FROM Position p")
    , @NamedQuery(name = "Position.findByIdposition", query = "SELECT p FROM Position p WHERE p.idposition = :idposition")
    , @NamedQuery(name = "Position.findByClosed", query = "SELECT p FROM Position p WHERE p.closed = :closed")})
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idposition")
    private Integer idposition;
    @Column(name = "closed")
    private Boolean closed;
    @OneToMany(mappedBy = "fkposition")
    private Set<Price> priceSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkposition")
    private Set<Logger> loggerSet;
    @JoinColumn(name = "fkforex", referencedColumnName = "idforex")
    @ManyToOne(optional = false)
    private Forex fkforex;
    @JoinColumn(name = "fkuser", referencedColumnName = "iduser")
    @ManyToOne(optional = false)
    private User fkuser;

    public Position() {
    }

    public Position(Integer idposition) {
        this.idposition = idposition;
    }

    public Integer getIdposition() {
        return idposition;
    }

    public void setIdposition(Integer idposition) {
        this.idposition = idposition;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    @XmlTransient
    public Set<Price> getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(Set<Price> priceSet) {
        this.priceSet = priceSet;
    }

    @XmlTransient
    public Set<Logger> getLoggerSet() {
        return loggerSet;
    }

    public void setLoggerSet(Set<Logger> loggerSet) {
        this.loggerSet = loggerSet;
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
        hash += (idposition != null ? idposition.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Position)) {
            return false;
        }
        Position other = (Position) object;
        if ((this.idposition == null && other.idposition != null) || (this.idposition != null && !this.idposition.equals(other.idposition))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "B6B32EAR.Forex.jpa.entities.Position[ idposition=" + idposition + " ]";
    }
    
}
