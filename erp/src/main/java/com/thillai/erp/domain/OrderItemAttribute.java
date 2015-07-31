package com.thillai.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A OrderItemAttribute.
 */
@Entity
@Table(name = "T_ORDERITEMATTRIBUTE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderItemAttribute implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "attribute_name", nullable = false)
    private String attributeName;

    @NotNull
    @Column(name = "attribute_value", nullable = false)
    private String attributeValue;

    @Column(name = "order_item_seq_id")
    private Integer orderItemSeq_id;

    @ManyToOne
    private OrderHeader order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Integer getOrderItemSeq_id() {
        return orderItemSeq_id;
    }

    public void setOrderItemSeq_id(Integer orderItemSeq_id) {
        this.orderItemSeq_id = orderItemSeq_id;
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

        OrderItemAttribute orderItemAttribute = (OrderItemAttribute) o;

        if ( ! Objects.equals(id, orderItemAttribute.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderItemAttribute{" +
                "id=" + id +
                ", attributeName='" + attributeName + "'" +
                ", attributeValue='" + attributeValue + "'" +
                ", orderItemSeq_id='" + orderItemSeq_id + "'" +
                '}';
    }
}
