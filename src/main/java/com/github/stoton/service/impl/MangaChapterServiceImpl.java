package com.github.stoton.service.impl;

import com.github.stoton.domain.Manga;
import com.github.stoton.domain.MangaChapter;
import com.github.stoton.repository.MangaChapterRepository;
import com.github.stoton.repository.MangaRepository;
import com.github.stoton.service.MangaChapterService;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MangaChapterServiceImpl implements MangaChapterService {
    private final static Logger logger = Logger.getLogger(MangaChapterServiceImpl.class);
    private static final String FROM_H3 = "h3 > a";
    private static final String FROM_H4 = "h4 > a";
    private static final String HREF = "href";
    private static final String TOTAL_PAGES = "var total_pages=\\d+";
    private static final String INTEGER = "\\d+";
    private static final String ERROR_WITH_CONNECTION = "Connection lost - error 503. Wait for next try.";

    @Autowired
    private MangaChapterRepository mangaChapterRepository;

    @Autowired
    private MangaRepository mangaRepository;

    @Override
    public void insertMangaChapters() {
        Document document;

        List<Manga> mangaList = mangaRepository.getAllManga();
        try {
            for (Manga manga : mangaList) {
                document = Jsoup.connect(manga.getUrl()).get();
                List<String> elementsFromH3 = document.select(FROM_H3).eachAttr(HREF);
                List<String> elementsFromH4 = document.select(FROM_H4).eachAttr(HREF);

                String mangaUrl = manga.getUrl();

                elementsFromH3 = elementsFromH3.stream()
                        .filter(s -> s.contains(mangaUrl))
                        .collect(Collectors.toList());
                elementsFromH4 = elementsFromH4.stream()
                        .filter(s -> s.contains(mangaUrl))
                        .collect(Collectors.toList());

                List<String> chaptersUrls = new ArrayList<>();
                chaptersUrls.addAll(elementsFromH3);
                chaptersUrls.addAll(elementsFromH4);

                List<MangaChapter> mangaChapterList = buildMangaChaptersList(chaptersUrls, manga);

                int chaptersCount = 1;
                for (MangaChapter mangaChapter : mangaChapterList) {
                    boolean isScraped = false;
                    while (!isScraped) {
                        try {
                            document = Jsoup.connect(mangaChapter.getUrl()).get();
                            isScraped = true;
                        } catch (Exception e) {
                            logger.error(ERROR_WITH_CONNECTION);
                        }
                    }

                    Pattern pagesPattern = Pattern.compile(TOTAL_PAGES);
                    Pattern lengthPattern = Pattern.compile(INTEGER);

                    Matcher matcher = pagesPattern.matcher(document.toString());

                    String length = "";

                    while (matcher.find()) {
                        length = matcher.group();
                    }

                    matcher = lengthPattern.matcher(length);

                    while (matcher.find()) {
                        length = matcher.group();
                    }

                    int fullLength = Integer.valueOf(length);
                    mangaChapter.setLength(fullLength);
                    mangaChapter.setNumber(chaptersCount);
                    mangaChapterRepository.saveOrUpdate(mangaChapter);
                    chaptersCount++;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private List<MangaChapter> buildMangaChaptersList(List<String> chaptersURL, Manga manga) {
        List<MangaChapter> mangaChapterList = new ArrayList<>();

        for (String url : chaptersURL) {
            MangaChapter mangaChapter = new MangaChapter();
            mangaChapter.setManga(manga);
            mangaChapter.setUrl(url);
            mangaChapterList.add(mangaChapter);
        }
        return mangaChapterList;
    }
}