package com.github.stoton.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "manga_chapter")

public class MangaChapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private long id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("length")
    private int length;

    @JsonProperty("number")
    private int number;

    @OneToMany(mappedBy = "mangaChapter")
    @JsonIgnore
    private List<MangaPage> mangaPages;


    public MangaChapter() {}

    public MangaChapter(String url, int length, int number, Manga manga) {
        this.url = url;
        this.length = length;
        this.number = number;
        this.manga = manga;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "manga_id")
    private Manga manga;

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getLength() {
        return length;
    }

    public int getNumber() {
        return number;
    }

    public Manga getManga() {
        return manga;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public List<MangaPage> getMangaPages() {
        return mangaPages;
    }

    public void setMangaPages(List<MangaPage> mangaPages) {
        this.mangaPages = mangaPages;
    }

    @Override
    public String toString() {
        return "MangaChapter{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", length=" + length +
                ", number=" + number +
                ", manga=" + manga +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MangaChapter that = (MangaChapter) o;

        if (id != that.id) return false;
        if (length != that.length) return false;
        if (number != that.number) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (mangaPages != null ? !mangaPages.equals(that.mangaPages) : that.mangaPages != null) return false;
        return manga != null ? manga.equals(that.manga) : that.manga == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + length;
        result = 31 * result + number;
        result = 31 * result + (mangaPages != null ? mangaPages.hashCode() : 0);
        result = 31 * result + (manga != null ? manga.hashCode() : 0);
        return result;
    }
}
