package com.github.stoton.test.mangachapterservice;

import com.github.stoton.domain.Manga;
import com.github.stoton.domain.MangaChapter;
import com.github.stoton.repository.MangaChapterRepository;
import com.github.stoton.service.MangaChapterService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class MangaChapterServiceInsertTest {
    private static final int MANGA_ID = 1;


    @Test
    public void insertMangaChapterTest() {
        MangaChapterRepository fakeRepository = new InMemoryMangaChapterRepositoryImpl();
        MangaChapterService mangaChapterService = mock(MangaChapterService.class);

        doAnswer(invocation -> {
            Manga manga = new Manga("1", "1", "url1", "url1");
            manga.setId(MANGA_ID);
            MangaChapter mangaChapter = new MangaChapter("url1", 1, 1, manga);
            fakeRepository.saveOrUpdate(mangaChapter);
            return null;
        }).when(mangaChapterService).insertMangaChapters();

        mangaChapterService.insertMangaChapters();

        assertEquals(1, fakeRepository.getMangaChapters(MANGA_ID).size());

    }

    private class InMemoryMangaChapterRepositoryImpl implements MangaChapterRepository {
        List<MangaChapter> mangaChapters = new ArrayList<>();

        @Override
        public List getMangaChapters(long mangaId) {
            return mangaChapters
                    .stream()
                    .filter(mangaChapter -> mangaChapter.getManga().getId() == mangaId)
                    .collect(Collectors.toList());
        }

        @Override
        public MangaChapter getMangaChapterById(long mangaId, long chapterId) {
            for(MangaChapter mangaChapter : mangaChapters) {
                if(mangaChapter.getManga().getId() == mangaChapter.getManga().getId() && mangaChapter.getId() == chapterId)
                    return mangaChapter;
            }
            return null;
        }

        @Override
        public int getMangaChapterLength(long id) {
            for(MangaChapter mangaChapter: mangaChapters) {
                if(mangaChapter.getId() == id)
                    return mangaChapter.getLength();
            }
            return -1;
        }

        @Override
        public void saveOrUpdate(MangaChapter mangaChapter) {
            mangaChapters.add(mangaChapter);
        }

        @Override
        public List<MangaChapter> getMangaChaptersById(long id) {
            return
                    mangaChapters
                    .stream()
                    .filter(mangaChapter -> mangaChapter.getId() == id)
                    .collect(Collectors.toList());
        }
    }
}
