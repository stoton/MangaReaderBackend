package com.github.stoton.repository;

import com.github.stoton.domain.Manga;

import java.util.List;

public interface MangaRepository {
    void saveOrUpdate(Manga manga);
    Manga find(long id);
    List getAllManga();
}
