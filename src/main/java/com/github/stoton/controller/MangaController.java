package com.github.stoton.controller;

import com.github.stoton.domain.Manga;
import com.github.stoton.repository.MangaRepository;
import com.github.stoton.service.MangaService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class MangaController {
    private static final String MANGA_DIRECTORY = "http://mangafox.me/directory/";
    private final Logger logger = Logger.getLogger(MangaController.class);

    @Autowired
    private MangaService mangaService;

    @Autowired
    private MangaRepository mangaRepository;

    @RequestMapping("/{id:\\d+}")
    public Manga getManga(@PathVariable long id) {
        return mangaRepository.find(id);
    }

    @RequestMapping("/insertManga")
    public void insertManga() {
        try {
            mangaService.setMangaDirectoryUrl(MANGA_DIRECTORY);
            mangaService.insertManga();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    @RequestMapping("/manga/all")
    public List<Manga> getAllMangas() {
        return mangaRepository.getAllManga();
    }

}
