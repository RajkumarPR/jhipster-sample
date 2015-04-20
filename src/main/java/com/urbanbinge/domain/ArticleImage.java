package com.urbanbinge.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ArticleImage.
 */
@Entity
@Table(name = "T_ARTICLEIMAGE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ArticleImage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    private ExpertsArticle articleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

        ArticleImage articleImage = (ArticleImage) o;

        if ( ! Objects.equals(id, articleImage.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ArticleImage{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + "'" +
                '}';
    }
}
