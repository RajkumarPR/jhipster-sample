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
 * A GiftAnExperience.
 */
@Entity
@Table(name = "T_GIFTANEXPERIENCE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GiftAnExperience implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_mail")
    private String receiverMail;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "sender_mail")
    private String senderMail;

    @Column(name = "sender_phone")
    private String senderPhone;

    @Column(name = "gift_message")
    private String giftMessage;

    @Column(name = "voucher_code")
    private String voucherCode;

    @Column(name = "voucher_amount")
    private Integer voucherAmount;

    @Column(name = "transaction_id")
    private String transactionId;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "date_of_transaction")
    private DateTime dateOfTransaction;

    @Column(name = "is_voucher_valid")
    private Boolean isVoucherValid;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "voucher_validity")
    private DateTime voucherValidity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMail() {
        return receiverMail;
    }

    public void setReceiverMail(String receiverMail) {
        this.receiverMail = receiverMail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getGiftMessage() {
        return giftMessage;
    }

    public void setGiftMessage(String giftMessage) {
        this.giftMessage = giftMessage;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Integer getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(Integer voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public DateTime getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(DateTime dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public Boolean getIsVoucherValid() {
        return isVoucherValid;
    }

    public void setIsVoucherValid(Boolean isVoucherValid) {
        this.isVoucherValid = isVoucherValid;
    }

    public DateTime getVoucherValidity() {
        return voucherValidity;
    }

    public void setVoucherValidity(DateTime voucherValidity) {
        this.voucherValidity = voucherValidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GiftAnExperience giftAnExperience = (GiftAnExperience) o;

        if ( ! Objects.equals(id, giftAnExperience.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GiftAnExperience{" +
                "id=" + id +
                ", receiverName='" + receiverName + "'" +
                ", receiverMail='" + receiverMail + "'" +
                ", senderName='" + senderName + "'" +
                ", senderMail='" + senderMail + "'" +
                ", senderPhone='" + senderPhone + "'" +
                ", giftMessage='" + giftMessage + "'" +
                ", voucherCode='" + voucherCode + "'" +
                ", voucherAmount='" + voucherAmount + "'" +
                ", transactionId='" + transactionId + "'" +
                ", dateOfTransaction='" + dateOfTransaction + "'" +
                ", isVoucherValid='" + isVoucherValid + "'" +
                ", voucherValidity='" + voucherValidity + "'" +
                '}';
    }
}
