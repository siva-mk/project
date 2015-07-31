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
 * A OrderHeader.
 */
@Entity
@Table(name = "T_ORDERHEADER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderHeader implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "order_date", nullable = false)
    private DateTime orderDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "entry_date", nullable = false)
    private DateTime entryDate;

    @NotNull
    @Column(name = "visit_id", nullable = false)
    private Integer visit_id;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @NotNull
    @Column(name = "sync_status_id", nullable = false)
    private Integer syncStatus_id;

    @NotNull
    @Column(name = "billing_account_id", nullable = false)
    private Integer billingAccount_id;

    @NotNull
    @Column(name = "origin_facility_id", nullable = false)
    private Integer originFacility_id;

    @Column(name = "grand_total")
    private Long grandTotal;

    @ManyToOne
    private Ordertype ordertype;

    @ManyToOne
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(DateTime orderDate) {
        this.orderDate = orderDate;
    }

    public DateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DateTime entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(Integer visit_id) {
        this.visit_id = visit_id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getSyncStatus_id() {
        return syncStatus_id;
    }

    public void setSyncStatus_id(Integer syncStatus_id) {
        this.syncStatus_id = syncStatus_id;
    }

    public Integer getBillingAccount_id() {
        return billingAccount_id;
    }

    public void setBillingAccount_id(Integer billingAccount_id) {
        this.billingAccount_id = billingAccount_id;
    }

    public Integer getOriginFacility_id() {
        return originFacility_id;
    }

    public void setOriginFacility_id(Integer originFacility_id) {
        this.originFacility_id = originFacility_id;
    }

    public Long getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Long grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Ordertype getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Ordertype ordertype) {
        this.ordertype = ordertype;
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

        OrderHeader orderHeader = (OrderHeader) o;

        if ( ! Objects.equals(id, orderHeader.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderHeader{" +
                "id=" + id +
                ", orderDate='" + orderDate + "'" +
                ", entryDate='" + entryDate + "'" +
                ", visit_id='" + visit_id + "'" +
                ", createdBy='" + createdBy + "'" +
                ", syncStatus_id='" + syncStatus_id + "'" +
                ", billingAccount_id='" + billingAccount_id + "'" +
                ", originFacility_id='" + originFacility_id + "'" +
                ", grandTotal='" + grandTotal + "'" +
                '}';
    }
}
