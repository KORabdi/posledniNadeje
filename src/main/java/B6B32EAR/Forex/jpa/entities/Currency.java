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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author zero
 */
@Entity
@Table(name = "currency")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Currency.findAll", query = "SELECT c FROM Currency c")
    , @NamedQuery(name = "Currency.findByIdcurrency", query = "SELECT c FROM Currency c WHERE c.idcurrency = :idcurrency")
    , @NamedQuery(name = "Currency.findByName", query = "SELECT c FROM Currency c WHERE c.name = :name")})
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcurrency")
    private Integer idcurrency;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencytwo")
    private Set<Forex> forexSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencyone")
    private Set<Forex> forexSet1;

    public Currency() {
    }

    public Currency(Integer idcurrency) {
        this.idcurrency = idcurrency;
    }

    public Integer getIdcurrency() {
        return idcurrency;
    }

    public void setIdcurrency(Integer idcurrency) {
        this.idcurrency = idcurrency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Set<Forex> getForexSet() {
        return forexSet;
    }

    public void setForexSet(Set<Forex> forexSet) {
        this.forexSet = forexSet;
    }

    @XmlTransient
    public Set<Forex> getForexSet1() {
        return forexSet1;
    }

    public void setForexSet1(Set<Forex> forexSet1) {
        this.forexSet1 = forexSet1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcurrency != null ? idcurrency.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Currency)) {
            return false;
        }
        Currency other = (Currency) object;
        if ((this.idcurrency == null && other.idcurrency != null) || (this.idcurrency != null && !this.idcurrency.equals(other.idcurrency))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "B6B32EAR.Forex.jpa.entities.Currency[ idcurrency=" + idcurrency + " ]";
    }
    
}
