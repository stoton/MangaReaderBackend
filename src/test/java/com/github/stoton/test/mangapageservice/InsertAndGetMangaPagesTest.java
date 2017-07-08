package com.github.stoton.test.mangapageservice;


import com.github.stoton.domain.MangaChapter;
import com.github.stoton.domain.MangaPage;
import com.github.stoton.repository.MangaPageRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class InsertAndGetMangaPagesTest {
    private static final String FIRST_MANGA_PAGE = "http://l.mfcdn.net/store/manga/11362/01-002.0/compressed/iopm_002_001.jpg";
    private static final String SECOND_MANGA_PAGE = "http://l.mfcdn.net/store/manga/11362/01-002.0/compressed/iopm_002_002.jpg";
    private static final String THIRD_MANGA_PAGE = "http://l.mfcdn.net/store/manga/11362/01-002.0/compressed/iopm_002_003.jpg";
    private static final long MANGA_CHAPTER_ID = 1;

    @Test
    public void insertAndGetMangaPagesWhenRepositoryIsEmptyTest() {
        MangaPageRepository fakeRepository = new InMemoryFakeRepository();
        MangaChapter mangaChapter = new MangaChapter();
        mangaChapter.setId(MANGA_CHAPTER_ID);

        List<MangaPage> mangaPagesToSave = new ArrayList<>();
        List<String> imagesUrls = new ArrayList<>();
        imagesUrls.add(FIRST_MANGA_PAGE);
        imagesUrls.add(SECOND_MANGA_PAGE);
        imagesUrls.add(THIRD_MANGA_PAGE);

        for (String imageUrl : imagesUrls) {
            MangaPage mangaPage = new MangaPage(imageUrl, mangaChapter);
            mangaPage.setMangaChapter(mangaChapter);
            mangaPagesToSave.add(mangaPage);
        }

        for (MangaPage mangaPage : mangaPagesToSave) {
            fakeRepository.save(mangaPage);
        }

        assertEquals(3, fakeRepository.findMangaPagesById(MANGA_CHAPTER_ID).size());
    }

    @Test
    public void insertAndGetMangaPagesWhenRepositoryIsNotEmptyTest() {
        MangaPageRepository fakeRepository = new InMemoryFakeRepository();

        MangaChapter mangaChapter = new MangaChapter();
        mangaChapter.setId(MANGA_CHAPTER_ID);

        fakeRepository.save(new MangaPage(FIRST_MANGA_PAGE, mangaChapter));
        fakeRepository.save(new MangaPage(SECOND_MANGA_PAGE, mangaChapter));
        fakeRepository.save(new MangaPage(THIRD_MANGA_PAGE, mangaChapter));

        List<MangaPage> mangaPages = fakeRepository.findMangaPagesById(MANGA_CHAPTER_ID);
        assertEquals(3, mangaPages.size());
    }

    private class InMemoryFakeRepository implements MangaPageRepository {
        List<MangaPage> mangaPages = new ArrayList<>();

        @Override
        public void save(MangaPage mangaPage) {
            mangaPages.add(mangaPage);
        }

        @Override
        public List<MangaPage> findMangaPagesById(long id) {
            return mangaPages
                    .stream()
                    .filter(mangaPage -> mangaPage.getMangaChapter().getId() == id)
                    .collect(Collectors.toList());
        }
    }
}


