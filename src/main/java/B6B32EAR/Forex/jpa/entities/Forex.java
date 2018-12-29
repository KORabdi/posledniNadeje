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
@Table(name = "forex")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Forex.findAll", query = "SELECT f FROM Forex f")
    , @NamedQuery(name = "Forex.findByIdforex", query = "SELECT f FROM Forex f WHERE f.idforex = :idforex")})
public class Forex implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idforex")
    private Integer idforex;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkforex")
    private Set<UserForex> userForexSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkforex")
    private Set<Position> positionSet;
    @JoinColumn(name = "currencytwo", referencedColumnName = "idcurrency")
    @ManyToOne(optional = false)
    private Currency currencytwo;
    @JoinColumn(name = "currencyone", referencedColumnName = "idcurrency")
    @ManyToOne(optional = false)
    private Currency currencyone;

    public Forex() {
    }

    public Forex(Integer idforex) {
        this.idforex = idforex;
    }

    public Integer getIdforex() {
        return idforex;
    }

    public void setIdforex(Integer idforex) {
        this.idforex = idforex;
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

    public Currency getCurrencytwo() {
        return currencytwo;
    }

    public void setCurrencytwo(Currency currencytwo) {
        this.currencytwo = currencytwo;
    }

    public Currency getCurrencyone() {
        return currencyone;
    }

    public void setCurrencyone(Currency currencyone) {
        this.currencyone = currencyone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idforex != null ? idforex.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Forex)) {
            return false;
        }
        Forex other = (Forex) object;
        if ((this.idforex == null && other.idforex != null) || (this.idforex != null && !this.idforex.equals(other.idforex))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "B6B32EAR.Forex.jpa.entities.Forex[ idforex=" + idforex + " ]";
    }
    
}
