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
@Table(name = "logger")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logger.findAll", query = "SELECT l FROM Logger l")
    , @NamedQuery(name = "Logger.findByIdlogger", query = "SELECT l FROM Logger l WHERE l.idlogger = :idlogger")
    , @NamedQuery(name = "Logger.findByPriority", query = "SELECT l FROM Logger l WHERE l.priority = :priority")})
public class Logger implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlogger")
    private Integer idlogger;
    @Column(name = "priority")
    private Integer priority;
    @JoinColumn(name = "fkalgorithm", referencedColumnName = "idalgorithm")
    @ManyToOne(optional = false)
    private Algorithm fkalgorithm;
    @JoinColumn(name = "fkposition", referencedColumnName = "idposition")
    @ManyToOne(optional = false)
    private Position fkposition;

    public Logger() {
    }

    public Logger(Integer idlogger) {
        this.idlogger = idlogger;
    }

    public Integer getIdlogger() {
        return idlogger;
    }

    public void setIdlogger(Integer idlogger) {
        this.idlogger = idlogger;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Algorithm getFkalgorithm() {
        return fkalgorithm;
    }

    public void setFkalgorithm(Algorithm fkalgorithm) {
        this.fkalgorithm = fkalgorithm;
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
        hash += (idlogger != null ? idlogger.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logger)) {
            return false;
        }
        Logger other = (Logger) object;
        if ((this.idlogger == null && other.idlogger != null) || (this.idlogger != null && !this.idlogger.equals(other.idlogger))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "B6B32EAR.Forex.jpa.entities.Logger[ idlogger=" + idlogger + " ]";
    }
    
}
