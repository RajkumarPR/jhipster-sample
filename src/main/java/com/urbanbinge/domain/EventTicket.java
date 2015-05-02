package com.urbanbinge.domain;

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
 * A EventTicket.
 */
@Entity
@Table(name = "T_EVENTTICKET")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EventTicket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ticket_cost")
    private Integer ticketCost;

    @Column(name = "total_tickets")
    private Integer totalTickets;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "has_offer")
    private Boolean hasOffer;

    @Column(name = "minimun_ticket")
    private Integer minimunTicket;

    @Column(name = "ticket_type")
    private Integer ticketType;

    @Column(name = "ticket_name")
    private String ticketName;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "duration")
    private String duration;

    @ManyToOne
    private Event eventId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(Integer ticketCost) {
        this.ticketCost = ticketCost;
    }

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Boolean getHasOffer() {
        return hasOffer;
    }

    public void setHasOffer(Boolean hasOffer) {
        this.hasOffer = hasOffer;
    }

    public Integer getMinimunTicket() {
        return minimunTicket;
    }

    public void setMinimunTicket(Integer minimunTicket) {
        this.minimunTicket = minimunTicket;
    }

    public Integer getTicketType() {
        return ticketType;
    }

    public void setTicketType(Integer ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Event getEventId() {
        return eventId;
    }

    public void setEventId(Event event) {
        this.eventId = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventTicket eventTicket = (EventTicket) o;

        if ( ! Objects.equals(id, eventTicket.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EventTicket{" +
                "id=" + id +
                ", ticketCost='" + ticketCost + "'" +
                ", totalTickets='" + totalTickets + "'" +
                ", discount='" + discount + "'" +
                ", hasOffer='" + hasOffer + "'" +
                ", minimunTicket='" + minimunTicket + "'" +
                ", ticketType='" + ticketType + "'" +
                ", ticketName='" + ticketName + "'" +
                ", startTime='" + startTime + "'" +
                ", endTime='" + endTime + "'" +
                ", duration='" + duration + "'" +
                '}';
    }
}
