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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Manga manga = (Manga) o;

        if (id != manga.id) return false;
        if (relId != null ? !relId.equals(manga.relId) : manga.relId != null) return false;
        if (title != null ? !title.equals(manga.title) : manga.title != null) return false;
        if (imageUrl != null ? !imageUrl.equals(manga.imageUrl) : manga.imageUrl != null) return false;
        if (url != null ? !url.equals(manga.url) : manga.url != null) return false;
        return mangaChapters != null ? mangaChapters.equals(manga.mangaChapters) : manga.mangaChapters == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (relId != null ? relId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (mangaChapters != null ? mangaChapters.hashCode() : 0);
        return result;
    }
}
