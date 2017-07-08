package com.github.stoton.test.mangachapterservice;

import com.github.stoton.domain.Manga;
import com.github.stoton.domain.MangaChapter;
import com.github.stoton.test.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

class MangaChapterParserImpl implements Parser {
    private static final String FROM_H3 = "h3 > a";
    private static final String FROM_H4 = "h4 > a";
    private static final String HREF = "href";
    private static final String TOTAL_PAGES = "var total_pages=\\d+";
    private static final String INTEGER = "\\d+";

    final static Manga manga = new Manga("1", "1", "1", "http://mangafox.me/manga/onepunch_man/");

    @Override
    public List<MangaChapter> parseDocument(Document document) throws IOException {
        List<Manga> mangaList = new ArrayList<>();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("specificMangaChapter.html").getFile());
        Document mangaChapterDocument = Jsoup.parse(file, "UTF-8");

        String mangaUrl = manga.getUrl();

        List<String> elementsFromH3 = document.select(FROM_H3).eachAttr(HREF);
        List<String> elementsFromH4 = document.select(FROM_H4).eachAttr(HREF);

        elementsFromH3 = elementsFromH3.stream()
                .filter(s -> s.contains(mangaUrl))
                .collect(Collectors.toList());

        elementsFromH4 = elementsFromH4.stream()
                .filter(s -> s.contains(mangaUrl))
                .collect(Collectors.toList());

        List<String> chaptersUrls = new ArrayList<>();
        chaptersUrls.addAll(elementsFromH3);
        chaptersUrls.addAll(elementsFromH4);

        List<MangaChapter> mangaChapters = new ArrayList<>();

        chaptersUrls.forEach(url -> {
            MangaChapter mangaChapter = new MangaChapter();
            mangaChapter.setManga(manga);
            mangaChapter.setUrl(url);
            mangaChapters.add(mangaChapter);
        });

        int chaptersCount = 1;
        for (MangaChapter mangaChapter : mangaChapters) {
            Pattern pagesPattern = Pattern.compile(TOTAL_PAGES);
            Pattern lengthPattern = Pattern.compile(INTEGER);

            Matcher matcher = pagesPattern.matcher(mangaChapterDocument.toString());
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
            chaptersCount++;
        }
        return mangaChapters;
    }
}

public class MangaChapterParserTest {
    @Test
    public void testMangaChapterParser() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("mangaChapterDirectory.html").getFile());
        Document doc = Jsoup.parse(file, "UTF-8");

        Parser<MangaChapter> parser = new MangaChapterParserImpl();
        List<MangaChapter> chapterList = parser.parseDocument(doc);
        List<MangaChapter> exptected = new ArrayList<>();
        exptected.add(new MangaChapter("http://mangafox.me/manga/onepunch_man/v01/c001/1.html", 19, 1, MangaChapterParserImpl.manga));

        assertEquals(exptected, chapterList);
    }
}
