package com.thillai.erp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ShipmentType.
 */
@Entity
@Table(name = "T_SHIPMENTTYPE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShipmentType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "shipmen_type", nullable = false)
    private String shipmenType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShipmenType() {
        return shipmenType;
    }

    public void setShipmenType(String shipmenType) {
        this.shipmenType = shipmenType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShipmentType shipmentType = (ShipmentType) o;

        if ( ! Objects.equals(id, shipmentType.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ShipmentType{" +
                "id=" + id +
                ", shipmenType='" + shipmenType + "'" +
                '}';
    }
}
