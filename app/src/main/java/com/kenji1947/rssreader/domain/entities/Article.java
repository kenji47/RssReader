package com.kenji1947.rssreader.domain.entities;

public class Article {

    public Article() {
    }

    public long id;
    public long feedId;
    public String title;
    public String link;
    public String imageLink;
    public long publicationDate;

    public boolean isNew = true;
    public boolean isFavourite = false;

    public Article(String title, String link, long publicationDate, boolean isNew, boolean isFavourite) {
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
        this.isNew = isNew;
        this.isFavourite = isFavourite;
    }

    public Article(final long id, final long feedId, final String title, final String link, final long publicationDate, final boolean isNew, final boolean isFavourite) {
        this.id = id;
        this.feedId = feedId;
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
        this.isNew = isNew;
        this.isFavourite = isFavourite;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", feedId=" + feedId +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", publicationDate=" + publicationDate +
                '}';
    }

}
