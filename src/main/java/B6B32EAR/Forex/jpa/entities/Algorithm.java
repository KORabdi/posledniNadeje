/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.jpa.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author zero
 */
@Entity
@Table(name = "algorithm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Algorithm.findAll", query = "SELECT a FROM Algorithm a")
    , @NamedQuery(name = "Algorithm.findByIdalgorithm", query = "SELECT a FROM Algorithm a WHERE a.idalgorithm = :idalgorithm")
    , @NamedQuery(name = "Algorithm.findByName", query = "SELECT a FROM Algorithm a WHERE a.name = :name")
    , @NamedQuery(name = "Algorithm.findByDescription", query = "SELECT a FROM Algorithm a WHERE a.description = :description")
    , @NamedQuery(name = "Algorithm.findByWeight", query = "SELECT a FROM Algorithm a WHERE a.weight = :weight")})
public class Algorithm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idalgorithm")
    private Integer idalgorithm;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Column(name = "weight")
    private Integer weight;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkalgorithm")
    private Set<Logger> loggerSet;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "forex_algorithm",
            joinColumns = @JoinColumn(name = "FK_idalgorithm"),
            inverseJoinColumns = @JoinColumn(name = "FK_idforex")
    )
    private Set<Forex> forexSet = new HashSet<>();


    public Algorithm() {
    }

    public Algorithm(Integer idalgorithm) {
        this.idalgorithm = idalgorithm;
    }

    public Integer getIdalgorithm() {
        return idalgorithm;
    }

    public void setIdalgorithm(Integer idalgorithm) {
        this.idalgorithm = idalgorithm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @XmlTransient
    public Set<Logger> getLoggerSet() {
        return loggerSet;
    }

    public void setLoggerSet(Set<Logger> loggerSet) {
        this.loggerSet = loggerSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idalgorithm != null ? idalgorithm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Algorithm)) {
            return false;
        }
        Algorithm other = (Algorithm) object;
        if ((this.idalgorithm == null && other.idalgorithm != null) || (this.idalgorithm != null && !this.idalgorithm.equals(other.idalgorithm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "B6B32EAR.Forex.jpa.entities.Algorithm[ idalgorithm=" + idalgorithm + " ]";
    }
    
}
