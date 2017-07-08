package com.github.stoton.service;

import com.github.stoton.domain.Manga;

import java.io.IOException;
import java.util.List;

public interface MangaService {
    void insertManga() throws IOException;
    List<Manga> getDataFromWebsite(String url);
    void setMangaDirectoryUrl(String mangaDirectoryUrl);
}
