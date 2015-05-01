package com.urbanbinge.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Urbanbinge on 30/04/2015.
 */
@Embeddable
public class EventCategoryMapPK implements Serializable {

    @Column(name="category_id")
    private Long categoryId;

    @Column(name="event_id")
    private Long eventId;

    public EventCategoryMapPK() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventCategoryMapPK)) return false;

        EventCategoryMapPK that = (EventCategoryMapPK) o;

        if (!categoryId.equals(that.categoryId)) return false;
        if (!eventId.equals(that.eventId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = categoryId.hashCode();
        result = 31 * result + eventId.hashCode();
        return result;
    }
}
