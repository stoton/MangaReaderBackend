package com.github.stoton.controller;

import com.github.stoton.domain.MangaChapter;
import com.github.stoton.repository.MangaChapterRepository;
import com.github.stoton.service.MangaPageService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MangaPageController {
    private final Logger logger = Logger.getLogger(MangaPageController.class);

    @Autowired
    private MangaChapterRepository mangaChapterRepository;

    @Autowired
    private MangaPageService mangaPageService;

    @RequestMapping("/chapters/{mangaId:\\d+}/{chapterId:\\d+}")
    public List<String> downloadMangaChapters(@PathVariable long mangaId, @PathVariable long chapterId) {
        int length = mangaChapterRepository.getMangaChapterLength(mangaId);

        MangaChapter mangaChapter = mangaChapterRepository.getMangaChapterById(mangaId, (length+1) - chapterId);

        try {
            return mangaPageService.insertAndGetMangaPages(mangaChapter);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }
}
