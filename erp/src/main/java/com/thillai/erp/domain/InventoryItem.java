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
 * A InventoryItem.
 */
@Entity
@Table(name = "T_INVENTORYITEM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InventoryItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "date_time_received", nullable = false)
    private DateTime dateTimeReceived;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "expiry_date", nullable = false)
    private DateTime expiryDate;

    @Column(name = "facility_id")
    private Integer facility_id;

    @NotNull
    @Column(name = "container_id", nullable = false)
    private Integer container_id;

    @NotNull
    @Column(name = "lot_id", nullable = false)
    private Integer lot_id;

    @NotNull
    @Column(name = "uom_id", nullable = false)
    private Integer UOM_id;

    @Column(name = "comments")
    private String comments;

    @Column(name = "quantity_on_hand")
    private Integer quantityOnHand;

    @Column(name = "available_to_promise")
    private Integer availableToPromise;

    @Column(name = "serial_number")
    private String serialNumber;

    @ManyToOne
    private Product product;

    @ManyToOne
    private InventoryItemType inventoryItemType;

    @ManyToOne
    private Party party;

    @ManyToOne
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getDateTimeReceived() {
        return dateTimeReceived;
    }

    public void setDateTimeReceived(DateTime dateTimeReceived) {
        this.dateTimeReceived = dateTimeReceived;
    }

    public DateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(DateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getFacility_id() {
        return facility_id;
    }

    public void setFacility_id(Integer facility_id) {
        this.facility_id = facility_id;
    }

    public Integer getContainer_id() {
        return container_id;
    }

    public void setContainer_id(Integer container_id) {
        this.container_id = container_id;
    }

    public Integer getLot_id() {
        return lot_id;
    }

    public void setLot_id(Integer lot_id) {
        this.lot_id = lot_id;
    }

    public Integer getUOM_id() {
        return UOM_id;
    }

    public void setUOM_id(Integer UOM_id) {
        this.UOM_id = UOM_id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public Integer getAvailableToPromise() {
        return availableToPromise;
    }

    public void setAvailableToPromise(Integer availableToPromise) {
        this.availableToPromise = availableToPromise;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public InventoryItemType getInventoryItemType() {
        return inventoryItemType;
    }

    public void setInventoryItemType(InventoryItemType inventoryItemType) {
        this.inventoryItemType = inventoryItemType;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InventoryItem inventoryItem = (InventoryItem) o;

        if ( ! Objects.equals(id, inventoryItem.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "id=" + id +
                ", dateTimeReceived='" + dateTimeReceived + "'" +
                ", expiryDate='" + expiryDate + "'" +
                ", facility_id='" + facility_id + "'" +
                ", container_id='" + container_id + "'" +
                ", lot_id='" + lot_id + "'" +
                ", UOM_id='" + UOM_id + "'" +
                ", comments='" + comments + "'" +
                ", quantityOnHand='" + quantityOnHand + "'" +
                ", availableToPromise='" + availableToPromise + "'" +
                ", serialNumber='" + serialNumber + "'" +
                '}';
    }
}
