package com.urbanbinge.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PromoCodeAmontRange.
 */
@Entity
@Table(name = "T_PROMOCODEAMONTRANGE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PromoCodeAmontRange implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "lower")
    private Integer lower;

    @Column(name = "higher")
    private Integer higher;

    @Column(name = "threshold")
    private Integer threshold;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLower() {
        return lower;
    }

    public void setLower(Integer lower) {
        this.lower = lower;
    }

    public Integer getHigher() {
        return higher;
    }

    public void setHigher(Integer higher) {
        this.higher = higher;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PromoCodeAmontRange promoCodeAmontRange = (PromoCodeAmontRange) o;

        if ( ! Objects.equals(id, promoCodeAmontRange.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PromoCodeAmontRange{" +
                "id=" + id +
                ", lower='" + lower + "'" +
                ", higher='" + higher + "'" +
                ", threshold='" + threshold + "'" +
                '}';
    }
}
