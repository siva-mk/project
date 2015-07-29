package com.thillai.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thillai.erp.domain.util.CustomDateTimeDeserializer;
import com.thillai.erp.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Shipment.
 */
@Entity
@Table(name = "T_SHIPMENT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Shipment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "estimate_ship_date", nullable = false)
    private DateTime estimateShipDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "estimated_ready_date", nullable = false)
    private DateTime estimatedReadyDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "estimated_arrival_date", nullable = false)
    private DateTime estimatedArrivalDate;

    @NotNull
    @Column(name = "estimated_ship_cost", nullable = false)
    private Long estimatedShipCost;

    @Column(name = "actual_ship_cost")
    private Long actualShipCost;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "latest_cancel_date", nullable = false)
    private DateTime latestCancelDate;

    @Column(name = "handling_instruction")
    private String handlingInstruction;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "last_updated", nullable = false)
    private DateTime lastUpdated;

    @ManyToOne
    private ShipmentType shipmentType;

    @ManyToOne
    private Party partyTo;

    @ManyToOne
    private Party partyFrom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getEstimateShipDate() {
        return estimateShipDate;
    }

    public void setEstimateShipDate(DateTime estimateShipDate) {
        this.estimateShipDate = estimateShipDate;
    }

    public DateTime getEstimatedReadyDate() {
        return estimatedReadyDate;
    }

    public void setEstimatedReadyDate(DateTime estimatedReadyDate) {
        this.estimatedReadyDate = estimatedReadyDate;
    }

    public DateTime getEstimatedArrivalDate() {
        return estimatedArrivalDate;
    }

    public void setEstimatedArrivalDate(DateTime estimatedArrivalDate) {
        this.estimatedArrivalDate = estimatedArrivalDate;
    }

    public Long getEstimatedShipCost() {
        return estimatedShipCost;
    }

    public void setEstimatedShipCost(Long estimatedShipCost) {
        this.estimatedShipCost = estimatedShipCost;
    }

    public Long getActualShipCost() {
        return actualShipCost;
    }

    public void setActualShipCost(Long actualShipCost) {
        this.actualShipCost = actualShipCost;
    }

    public DateTime getLatestCancelDate() {
        return latestCancelDate;
    }

    public void setLatestCancelDate(DateTime latestCancelDate) {
        this.latestCancelDate = latestCancelDate;
    }

    public String getHandlingInstruction() {
        return handlingInstruction;
    }

    public void setHandlingInstruction(String handlingInstruction) {
        this.handlingInstruction = handlingInstruction;
    }

    public DateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(DateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    public Party getPartyTo() {
        return partyTo;
    }

    public void setPartyTo(Party party) {
        this.partyTo = party;
    }

    public Party getPartyFrom() {
        return partyFrom;
    }

    public void setPartyFrom(Party party) {
        this.partyFrom = party;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Shipment shipment = (Shipment) o;

        if ( ! Objects.equals(id, shipment.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id=" + id +
                ", estimateShipDate='" + estimateShipDate + "'" +
                ", estimatedReadyDate='" + estimatedReadyDate + "'" +
                ", estimatedArrivalDate='" + estimatedArrivalDate + "'" +
                ", estimatedShipCost='" + estimatedShipCost + "'" +
                ", actualShipCost='" + actualShipCost + "'" +
                ", latestCancelDate='" + latestCancelDate + "'" +
                ", handlingInstruction='" + handlingInstruction + "'" +
                ", lastUpdated='" + lastUpdated + "'" +
                '}';
    }
}
