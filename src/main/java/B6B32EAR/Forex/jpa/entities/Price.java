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
@Table(name = "price")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Price.findAll", query = "SELECT p FROM Price p")
    , @NamedQuery(name = "Price.findByIdprice", query = "SELECT p FROM Price p WHERE p.idprice = :idprice")
    , @NamedQuery(name = "Price.findByPrice", query = "SELECT p FROM Price p WHERE p.price = :price")})
public class Price implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprice")
    private Integer idprice;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @JoinColumn(name = "fkposition", referencedColumnName = "idposition")
    @ManyToOne
    private Position fkposition;

    public Price() {
    }

    public Price(Integer idprice) {
        this.idprice = idprice;
    }

    public Integer getIdprice() {
        return idprice;
    }

    public void setIdprice(Integer idprice) {
        this.idprice = idprice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Position getFkposition() {
        return fkposition;
    }

    public void setFkposition(Position fkposition) {
        this.fkposition = fkposition;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprice != null ? idprice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Price)) {
            return false;
        }
        Price other = (Price) object;
        if ((this.idprice == null && other.idprice != null) || (this.idprice != null && !this.idprice.equals(other.idprice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "B6B32EAR.Forex.jpa.entities.Price[ idprice=" + idprice + " ]";
    }
    
}
