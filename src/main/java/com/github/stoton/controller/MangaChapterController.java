package com.github.stoton.controller;

import com.github.stoton.domain.MangaChapter;
import com.github.stoton.repository.MangaChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MangaChapterController {
    @Autowired
    private MangaChapterRepository mangaChapterRepository;

    @RequestMapping("/chapters/{mangaId:\\d+}")
    public List<MangaChapter> getMangaChapters(@PathVariable long mangaId) {
        return mangaChapterRepository.getMangaChaptersById(mangaId);
    }
}
