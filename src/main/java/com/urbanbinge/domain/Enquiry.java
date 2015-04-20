package com.urbanbinge.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Enquiry.
 */
@Entity
@Table(name = "T_ENQUIRY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Enquiry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "email")
    private String email;

    @Column(name = "enquiry_message")
    private String enquiry_message;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "ticke_names")
    private String tickeNames;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnquiry_message() {
        return enquiry_message;
    }

    public void setEnquiry_message(String enquiry_message) {
        this.enquiry_message = enquiry_message;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getTickeNames() {
        return tickeNames;
    }

    public void setTickeNames(String tickeNames) {
        this.tickeNames = tickeNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Enquiry enquiry = (Enquiry) o;

        if ( ! Objects.equals(id, enquiry.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Enquiry{" +
                "id=" + id +
                ", eventId='" + eventId + "'" +
                ", customerName='" + customerName + "'" +
                ", email='" + email + "'" +
                ", enquiry_message='" + enquiry_message + "'" +
                ", mobileNo='" + mobileNo + "'" +
                ", tickeNames='" + tickeNames + "'" +
                '}';
    }
}
