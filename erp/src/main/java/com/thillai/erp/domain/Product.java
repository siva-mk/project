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
 * A Product.
 */
@Entity
@Table(name = "T_PRODUCT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "manufacturer_party_id")
    private Integer manufacturerPartyId;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "introduction_date", nullable = false)
    private DateTime introductionDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "sales_discontinuation_date", nullable = false)
    private DateTime salesDiscontinuationDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "support_discontinuation_date", nullable = false)
    private DateTime supportDiscontinuationDate;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(name = "internal_name", nullable = false)
    private String internalName;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "comments")
    private String comments;

    @Column(name = "description")
    private String description;

    @Column(name = "inventory_message")
    private String inventoryMessage;

    @NotNull
    @Column(name = "required_inventory", nullable = false)
    private Integer requiredInventory;

    @Column(name = "small_image_url")
    private String smallImageURL;

    @Column(name = "large_imale_url")
    private String largeImaleURL;

    @Column(name = "quantity_uomid")
    private Integer quantityUOMId;

    @Column(name = "quantity_included")
    private Integer quantityIncluded;

    @Column(name = "pieces_included")
    private Integer piecesIncluded;

    @Column(name = "weight_uomid")
    private Integer weightUOMId;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "taxable")
    private Boolean taxable;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "created_on", nullable = false)
    private DateTime createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "modified_on", nullable = false)
    private DateTime modifiedOn;

    @Column(name = "modified_by")
    private String modifiedBy;

    @ManyToOne
    private Producttype producttype;

    @ManyToOne
    private Productcategory productcategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getManufacturerPartyId() {
        return manufacturerPartyId;
    }

    public void setManufacturerPartyId(Integer manufacturerPartyId) {
        this.manufacturerPartyId = manufacturerPartyId;
    }

    public DateTime getIntroductionDate() {
        return introductionDate;
    }

    public void setIntroductionDate(DateTime introductionDate) {
        this.introductionDate = introductionDate;
    }

    public DateTime getSalesDiscontinuationDate() {
        return salesDiscontinuationDate;
    }

    public void setSalesDiscontinuationDate(DateTime salesDiscontinuationDate) {
        this.salesDiscontinuationDate = salesDiscontinuationDate;
    }

    public DateTime getSupportDiscontinuationDate() {
        return supportDiscontinuationDate;
    }

    public void setSupportDiscontinuationDate(DateTime supportDiscontinuationDate) {
        this.supportDiscontinuationDate = supportDiscontinuationDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInventoryMessage() {
        return inventoryMessage;
    }

    public void setInventoryMessage(String inventoryMessage) {
        this.inventoryMessage = inventoryMessage;
    }

    public Integer getRequiredInventory() {
        return requiredInventory;
    }

    public void setRequiredInventory(Integer requiredInventory) {
        this.requiredInventory = requiredInventory;
    }

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public void setSmallImageURL(String smallImageURL) {
        this.smallImageURL = smallImageURL;
    }

    public String getLargeImaleURL() {
        return largeImaleURL;
    }

    public void setLargeImaleURL(String largeImaleURL) {
        this.largeImaleURL = largeImaleURL;
    }

    public Integer getQuantityUOMId() {
        return quantityUOMId;
    }

    public void setQuantityUOMId(Integer quantityUOMId) {
        this.quantityUOMId = quantityUOMId;
    }

    public Integer getQuantityIncluded() {
        return quantityIncluded;
    }

    public void setQuantityIncluded(Integer quantityIncluded) {
        this.quantityIncluded = quantityIncluded;
    }

    public Integer getPiecesIncluded() {
        return piecesIncluded;
    }

    public void setPiecesIncluded(Integer piecesIncluded) {
        this.piecesIncluded = piecesIncluded;
    }

    public Integer getWeightUOMId() {
        return weightUOMId;
    }

    public void setWeightUOMId(Integer weightUOMId) {
        this.weightUOMId = weightUOMId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getTaxable() {
        return taxable;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public DateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public DateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(DateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Producttype getProducttype() {
        return producttype;
    }

    public void setProducttype(Producttype producttype) {
        this.producttype = producttype;
    }

    public Productcategory getProductcategory() {
        return productcategory;
    }

    public void setProductcategory(Productcategory productcategory) {
        this.productcategory = productcategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        if ( ! Objects.equals(id, product.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", manufacturerPartyId='" + manufacturerPartyId + "'" +
                ", introductionDate='" + introductionDate + "'" +
                ", salesDiscontinuationDate='" + salesDiscontinuationDate + "'" +
                ", supportDiscontinuationDate='" + supportDiscontinuationDate + "'" +
                ", productName='" + productName + "'" +
                ", internalName='" + internalName + "'" +
                ", brandName='" + brandName + "'" +
                ", comments='" + comments + "'" +
                ", description='" + description + "'" +
                ", inventoryMessage='" + inventoryMessage + "'" +
                ", requiredInventory='" + requiredInventory + "'" +
                ", smallImageURL='" + smallImageURL + "'" +
                ", largeImaleURL='" + largeImaleURL + "'" +
                ", quantityUOMId='" + quantityUOMId + "'" +
                ", quantityIncluded='" + quantityIncluded + "'" +
                ", piecesIncluded='" + piecesIncluded + "'" +
                ", weightUOMId='" + weightUOMId + "'" +
                ", weight='" + weight + "'" +
                ", taxable='" + taxable + "'" +
                ", createdOn='" + createdOn + "'" +
                ", createdBy='" + createdBy + "'" +
                ", modifiedOn='" + modifiedOn + "'" +
                ", modifiedBy='" + modifiedBy + "'" +
                '}';
    }
}
