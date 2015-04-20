package com.urbanbinge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Event.
 */
@Entity
@Table(name = "T_EVENT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "event_venue")
    private String eventVenue;

    @Column(name = "cost_includes")
    private String costIncludes;

    @Column(name = "special_instructions")
    private String specialInstructions;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "is_bookable")
    private Boolean isBookable;

    @Column(name = "is_closed")
    private Boolean isClosed;

    @Column(name = "is_only_enquiry")
    private Boolean isOnlyEnquiry;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "start_date")
    private DateTime startDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "end_date")
    private DateTime endDate;

    @Column(name = "event_contact")
    private String eventContact;

    @Column(name = "event_type")
    private Integer eventType;

    @Column(name = "video_link")
    private String videoLink;

    @ManyToOne
    private Cities cityId;

    @ManyToOne
    private User uId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public String getCostIncludes() {
        return costIncludes;
    }

    public void setCostIncludes(String costIncludes) {
        this.costIncludes = costIncludes;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsBookable() {
        return isBookable;
    }

    public void setIsBookable(Boolean isBookable) {
        this.isBookable = isBookable;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Boolean getIsOnlyEnquiry() {
        return isOnlyEnquiry;
    }

    public void setIsOnlyEnquiry(Boolean isOnlyEnquiry) {
        this.isOnlyEnquiry = isOnlyEnquiry;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public String getEventContact() {
        return eventContact;
    }

    public void setEventContact(String eventContact) {
        this.eventContact = eventContact;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public Cities getCityId() {
        return cityId;
    }

    public void setCityId(Cities cities) {
        this.cityId = cities;
    }

    public User getUId() {
        return uId;
    }

    public void setUId(User user) {
        this.uId = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event event = (Event) o;

        if ( ! Objects.equals(id, event.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", description='" + description + "'" +
                ", eventVenue='" + eventVenue + "'" +
                ", costIncludes='" + costIncludes + "'" +
                ", specialInstructions='" + specialInstructions + "'" +
                ", isActive='" + isActive + "'" +
                ", isApproved='" + isApproved + "'" +
                ", isBookable='" + isBookable + "'" +
                ", isClosed='" + isClosed + "'" +
                ", isOnlyEnquiry='" + isOnlyEnquiry + "'" +
                ", startDate='" + startDate + "'" +
                ", endDate='" + endDate + "'" +
                ", eventContact='" + eventContact + "'" +
                ", eventType='" + eventType + "'" +
                ", videoLink='" + videoLink + "'" +
                '}';
    }
}
