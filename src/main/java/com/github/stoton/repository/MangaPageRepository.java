package com.github.stoton.repository;

import com.github.stoton.domain.MangaPage;

import java.util.List;

public interface MangaPageRepository {
    void save(MangaPage mangaPage);
    List<MangaPage> findMangaPagesById(long id);
}
