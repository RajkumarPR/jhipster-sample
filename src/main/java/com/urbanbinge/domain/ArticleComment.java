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
 * A ArticleComment.
 */
@Entity
@Table(name = "T_ARTICLECOMMENT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ArticleComment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "commenter")
    private String commenter;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "commented_on")
    private DateTime commentedOn;

    @ManyToOne
    private ExpertsArticle articleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public DateTime getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(DateTime commentedOn) {
        this.commentedOn = commentedOn;
    }

    public ExpertsArticle getArticleId() {
        return articleId;
    }

    public void setArticleId(ExpertsArticle expertsArticle) {
        this.articleId = expertsArticle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArticleComment articleComment = (ArticleComment) o;

        if ( ! Objects.equals(id, articleComment.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ArticleComment{" +
                "id=" + id +
                ", comment='" + comment + "'" +
                ", commenter='" + commenter + "'" +
                ", isApproved='" + isApproved + "'" +
                ", commentedOn='" + commentedOn + "'" +
                '}';
    }
}
