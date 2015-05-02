package com.urbanbinge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A EventCategorMap.
 */
@Entity
@Table(name = "T_EVENTCATEGORMAP")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EventCategorMap implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private EventCategory categoryId;

    @ManyToOne
    private Event eventId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(EventCategory eventCategory) {
        this.categoryId = eventCategory;
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

        EventCategorMap eventCategorMap = (EventCategorMap) o;

        if ( ! Objects.equals(id, eventCategorMap.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EventCategorMap{" +
                "id=" + id +
                '}';
    }
}
