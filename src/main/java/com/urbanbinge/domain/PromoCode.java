package com.urbanbinge.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.urbanbinge.domain.util.CustomDateTimeDeserializer;
import com.urbanbinge.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PromoCode.
 */
@Entity
@Table(name = "T_PROMOCODE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PromoCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "promo_amount")
    private Integer promoAmount;

    @Column(name = "is_promo_valid")
    private Boolean isPromoValid;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "promo_validity")
    private DateTime promoValidity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Integer getPromoAmount() {
        return promoAmount;
    }

    public void setPromoAmount(Integer promoAmount) {
        this.promoAmount = promoAmount;
    }

    public Boolean getIsPromoValid() {
        return isPromoValid;
    }

    public void setIsPromoValid(Boolean isPromoValid) {
        this.isPromoValid = isPromoValid;
    }

    public DateTime getPromoValidity() {
        return promoValidity;
    }

    public void setPromoValidity(DateTime promoValidity) {
        this.promoValidity = promoValidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PromoCode promoCode = (PromoCode) o;

        if ( ! Objects.equals(id, promoCode.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PromoCode{" +
                "id=" + id +
                ", promoCode='" + promoCode + "'" +
                ", promoAmount='" + promoAmount + "'" +
                ", isPromoValid='" + isPromoValid + "'" +
                ", promoValidity='" + promoValidity + "'" +
                '}';
    }
}
