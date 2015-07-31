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
 * A InvoiceItem.
 */
@Entity
@Table(name = "T_INVOICEITEM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InvoiceItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "invoice_item_seq_id")
    private Integer invoiceItemSeq_id;

    @Column(name = "invoice_item_type_id")
    private Integer invoiceItemType_id;

    @Column(name = "product_feature_id")
    private Integer productFeature_id;

    @Column(name = "uom_id")
    private Integer UOM_id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Invoice invoice;

    @ManyToOne
    private InventoryItem inventoryItem;

    @ManyToOne
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInvoiceItemSeq_id() {
        return invoiceItemSeq_id;
    }

    public void setInvoiceItemSeq_id(Integer invoiceItemSeq_id) {
        this.invoiceItemSeq_id = invoiceItemSeq_id;
    }

    public Integer getInvoiceItemType_id() {
        return invoiceItemType_id;
    }

    public void setInvoiceItemType_id(Integer invoiceItemType_id) {
        this.invoiceItemType_id = invoiceItemType_id;
    }

    public Integer getProductFeature_id() {
        return productFeature_id;
    }

    public void setProductFeature_id(Integer productFeature_id) {
        this.productFeature_id = productFeature_id;
    }

    public Integer getUOM_id() {
        return UOM_id;
    }

    public void setUOM_id(Integer UOM_id) {
        this.UOM_id = UOM_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvoiceItem invoiceItem = (InvoiceItem) o;

        if ( ! Objects.equals(id, invoiceItem.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + id +
                ", invoiceItemSeq_id='" + invoiceItemSeq_id + "'" +
                ", invoiceItemType_id='" + invoiceItemType_id + "'" +
                ", productFeature_id='" + productFeature_id + "'" +
                ", UOM_id='" + UOM_id + "'" +
                ", quantity='" + quantity + "'" +
                ", amount='" + amount + "'" +
                ", description='" + description + "'" +
                '}';
    }
}
