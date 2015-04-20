package com.urbanbinge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A EventLocation.
 */
@Entity
@Table(name = "T_EVENTLOCATION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EventLocation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "event_address")
    private String eventAddress;

    @Column(name = "latitude", precision=10, scale=2)
    private BigDecimal latitude;

    @Column(name = "longitude", precision=10, scale=2)
    private BigDecimal longitude;

    @ManyToOne
    private Event eventId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
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

        EventLocation eventLocation = (EventLocation) o;

        if ( ! Objects.equals(id, eventLocation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EventLocation{" +
                "id=" + id +
                ", location='" + location + "'" +
                ", eventAddress='" + eventAddress + "'" +
                ", latitude='" + latitude + "'" +
                ", longitude='" + longitude + "'" +
                '}';
    }
}
