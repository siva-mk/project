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
 * A OrderStatus.
 */
@Entity
@Table(name = "T_ORDERSTATUS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "order_item_id", nullable = false)
    private Integer orderItem_id;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "status_date_time", nullable = false)
    private DateTime statusDateTime;

    @ManyToOne
    private Status status;

    @ManyToOne
    private OrderHeader order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderItem_id() {
        return orderItem_id;
    }

    public void setOrderItem_id(Integer orderItem_id) {
        this.orderItem_id = orderItem_id;
    }

    public DateTime getStatusDateTime() {
        return statusDateTime;
    }

    public void setStatusDateTime(DateTime statusDateTime) {
        this.statusDateTime = statusDateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public OrderHeader getOrder() {
        return order;
    }

    public void setOrder(OrderHeader orderHeader) {
        this.order = orderHeader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderStatus orderStatus = (OrderStatus) o;

        if ( ! Objects.equals(id, orderStatus.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "id=" + id +
                ", orderItem_id='" + orderItem_id + "'" +
                ", statusDateTime='" + statusDateTime + "'" +
                '}';
    }
}
