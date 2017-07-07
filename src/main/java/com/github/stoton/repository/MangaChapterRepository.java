package com.github.stoton.repository;

import com.github.stoton.domain.MangaChapter;

import java.util.List;

public interface MangaChapterRepository {
    List getMangaChapters(long manga_id);
    MangaChapter getMangaChapterById(long manga_id, long chapter_id);
    int getMangaChapterLength(long id);
    void saveOrUpdate(MangaChapter mangaChapter);
    List<MangaChapter> getMangaChaptersById(long id);
}
