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
 * A OrderPaymentPreference.
 */
@Entity
@Table(name = "T_ORDERPAYMENTPREFERENCE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderPaymentPreference implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "payment_method_id", nullable = false)
    private Integer paymentMethod_id;

    @NotNull
    @Column(name = "max_amount", nullable = false)
    private Long maxAmount;

    @NotNull
    @Column(name = "auth_code", nullable = false)
    private String authCode;

    @NotNull
    @Column(name = "auth_message", nullable = false)
    private String authMessage;

    @ManyToOne
    private OrderHeader order;

    @ManyToOne
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPaymentMethod_id() {
        return paymentMethod_id;
    }

    public void setPaymentMethod_id(Integer paymentMethod_id) {
        this.paymentMethod_id = paymentMethod_id;
    }

    public Long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthMessage() {
        return authMessage;
    }

    public void setAuthMessage(String authMessage) {
        this.authMessage = authMessage;
    }

    public OrderHeader getOrder() {
        return order;
    }

    public void setOrder(OrderHeader orderHeader) {
        this.order = orderHeader;
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

        OrderPaymentPreference orderPaymentPreference = (OrderPaymentPreference) o;

        if ( ! Objects.equals(id, orderPaymentPreference.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderPaymentPreference{" +
                "id=" + id +
                ", paymentMethod_id='" + paymentMethod_id + "'" +
                ", maxAmount='" + maxAmount + "'" +
                ", authCode='" + authCode + "'" +
                ", authMessage='" + authMessage + "'" +
                '}';
    }
}
