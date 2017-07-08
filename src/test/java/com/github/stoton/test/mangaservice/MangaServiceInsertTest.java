package com.github.stoton.test.mangaservice;

import com.github.stoton.domain.Manga;
import com.github.stoton.repository.MangaRepository;
import com.github.stoton.service.MangaService;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MangaServiceInsertTest {

    @Test
    public void insertMangaTest() throws IOException {
        String url = "http://mangarepository.com";

        MangaRepository fakeRepository = new InMemoryMangaRepositoryImpl();
        MangaService mangaService = mock(MangaService.class);

        doAnswer(invocation -> {
            Manga manga = new Manga("1", "1", "url1", "1");
            manga.setId(1);
            fakeRepository.saveOrUpdate(manga);
            return null;
        }).when(mangaService).insertManga();

        mangaService.insertManga();

        assertEquals(1, fakeRepository.getAllManga().size());
    }

    private class InMemoryMangaRepositoryImpl implements MangaRepository {
        List<Manga> mangaList = new ArrayList<>();

        @Override
        public void saveOrUpdate(Manga manga) {
            mangaList.add(manga);
        }

        @Override
        public Manga find(long id) {
            for(Manga manga : mangaList) {
                if(manga.getId() == id) return manga;
            }
            return null;
        }

        @Override
        public List getAllManga() {
            return mangaList;
        }
    }
}
