package com.github.stoton.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "manga_cover")
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String relId;
    private String title;
    private String imageUrl;
    private String url;

    @OneToMany(mappedBy = "manga")
    @JsonIgnore
    private List<MangaChapter> mangaChapters;

    public Manga() {}

    public Manga(String relId, String title, String imageUrl, String url) {
        this.relId = relId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getRelId() {
        return relId;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {return url;}

    public List<MangaChapter> getMangaChapters() {
        return mangaChapters;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMangaChapters(List<MangaChapter> mangaChapters) {
        this.mangaChapters = mangaChapters;
    }

    @Override
    public String toString() {
        return "Manga{" +
                "id=" + id +
                ", relId='" + relId + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", url='" + url + '\'' +
                ", mangaChapters=" + mangaChapters +
                '}';
    }
}
