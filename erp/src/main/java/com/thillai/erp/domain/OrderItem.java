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
 * A OrderItem.
 */
@Entity
@Table(name = "T_ORDERITEM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "budget_id", nullable = false)
    private Integer budget_id;

    @NotNull
    @Column(name = "budget_item_id", nullable = false)
    private Integer budgetItem_id;

    @Column(name = "product_feadute_id")
    private Integer productFeadute_id;

    @NotNull
    @Column(name = "quote_id", nullable = false)
    private Integer quote_id;

    @NotNull
    @Column(name = "quote_item_id", nullable = false)
    private Integer quoteItem_id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Long unitPrice;

    @NotNull
    @Column(name = "unit_list_price", nullable = false)
    private Long unitListPrice;

    @Column(name = "unit_average_cost")
    private Long unitAverageCost;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "estimated_delivery_date", nullable = false)
    private DateTime estimatedDeliveryDate;

    @Column(name = "item_description")
    private String itemDescription;

    @NotNull
    @Column(name = "corresponding_po_id", nullable = false)
    private Integer correspondingPo_id;

    @ManyToOne
    private OrderHeader order;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBudget_id() {
        return budget_id;
    }

    public void setBudget_id(Integer budget_id) {
        this.budget_id = budget_id;
    }

    public Integer getBudgetItem_id() {
        return budgetItem_id;
    }

    public void setBudgetItem_id(Integer budgetItem_id) {
        this.budgetItem_id = budgetItem_id;
    }

    public Integer getProductFeadute_id() {
        return productFeadute_id;
    }

    public void setProductFeadute_id(Integer productFeadute_id) {
        this.productFeadute_id = productFeadute_id;
    }

    public Integer getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(Integer quote_id) {
        this.quote_id = quote_id;
    }

    public Integer getQuoteItem_id() {
        return quoteItem_id;
    }

    public void setQuoteItem_id(Integer quoteItem_id) {
        this.quoteItem_id = quoteItem_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getUnitListPrice() {
        return unitListPrice;
    }

    public void setUnitListPrice(Long unitListPrice) {
        this.unitListPrice = unitListPrice;
    }

    public Long getUnitAverageCost() {
        return unitAverageCost;
    }

    public void setUnitAverageCost(Long unitAverageCost) {
        this.unitAverageCost = unitAverageCost;
    }

    public DateTime getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(DateTime estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getCorrespondingPo_id() {
        return correspondingPo_id;
    }

    public void setCorrespondingPo_id(Integer correspondingPo_id) {
        this.correspondingPo_id = correspondingPo_id;
    }

    public OrderHeader getOrder() {
        return order;
    }

    public void setOrder(OrderHeader orderHeader) {
        this.order = orderHeader;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

        OrderItem orderItem = (OrderItem) o;

        if ( ! Objects.equals(id, orderItem.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", budget_id='" + budget_id + "'" +
                ", budgetItem_id='" + budgetItem_id + "'" +
                ", productFeadute_id='" + productFeadute_id + "'" +
                ", quote_id='" + quote_id + "'" +
                ", quoteItem_id='" + quoteItem_id + "'" +
                ", quantity='" + quantity + "'" +
                ", unitPrice='" + unitPrice + "'" +
                ", unitListPrice='" + unitListPrice + "'" +
                ", unitAverageCost='" + unitAverageCost + "'" +
                ", estimatedDeliveryDate='" + estimatedDeliveryDate + "'" +
                ", itemDescription='" + itemDescription + "'" +
                ", correspondingPo_id='" + correspondingPo_id + "'" +
                '}';
    }
}
