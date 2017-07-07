package com.github.stoton.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
@Table(name = "manga_page")
public class MangaPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private  String url;

    @ManyToOne
    @JoinColumn(name = "mangaChapter_id")
    @JsonIgnore
    private MangaChapter mangaChapter;

    public MangaPage() {}

    public MangaPage(String url, MangaChapter mangaChapter) {
        this.url = url;
        this.mangaChapter = mangaChapter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MangaChapter getMangaChapter() {
        return mangaChapter;
    }

    public void setMangaChapter(MangaChapter mangaChapter) {
        this.mangaChapter = mangaChapter;
    }

    @Override
    public String toString() {
        return "MangaPage{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", mangaChapter=" + mangaChapter +
                '}';
    }
}
