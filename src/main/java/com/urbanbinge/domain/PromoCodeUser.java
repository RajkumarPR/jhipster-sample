package com.urbanbinge.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PromoCodeUser.
 */
@Entity
@Table(name = "T_PROMOCODEUSER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PromoCodeUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email_used")
    private String emailUsed;

    @Column(name = "promo_code_used")
    private String promoCodeUsed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailUsed() {
        return emailUsed;
    }

    public void setEmailUsed(String emailUsed) {
        this.emailUsed = emailUsed;
    }

    public String getPromoCodeUsed() {
        return promoCodeUsed;
    }

    public void setPromoCodeUsed(String promoCodeUsed) {
        this.promoCodeUsed = promoCodeUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PromoCodeUser promoCodeUser = (PromoCodeUser) o;

        if ( ! Objects.equals(id, promoCodeUser.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PromoCodeUser{" +
                "id=" + id +
                ", emailUsed='" + emailUsed + "'" +
                ", promoCodeUsed='" + promoCodeUsed + "'" +
                '}';
    }
}
