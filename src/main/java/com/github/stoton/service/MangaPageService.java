package com.github.stoton.service;

import com.github.stoton.domain.MangaChapter;

import java.util.List;

public interface MangaPageService {
    List<String> insertAndGetMangaPages(MangaChapter mangaChapter);
    List<String> downloadMangaPages(String firstPageUrl, int length);
}
