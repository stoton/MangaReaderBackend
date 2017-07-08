package com.github.stoton.test.mangapageservice;

import com.github.stoton.test.Scraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class MangaPageScraperImpl implements Scraper {
    private static final String READ_IMAGE = "read_img";
    private static final String IMAGE = "img";
    private static final String SOURCE = "src";
    private static final String HTML = ".html";

    @Override
    public List<String> downloadMangaPagesFromWebsite(String fileName, int length) throws IOException {
        List<String> imagesUrls = new ArrayList<>();
        for(int i = 1; i <= length; i++) {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(fileName + i + HTML).getFile());
            Document document = Jsoup.parse(file, "UTF-8");
            Element element = document.getElementsByClass(READ_IMAGE).first();
            String imageUrl = element.select(IMAGE).attr(SOURCE);
            imagesUrls.add(imageUrl);
        }
        return imagesUrls;
    }
}

public class MangaPageScraperTest {
    private static final String FIRST_MANGA_PAGE = "http://l.mfcdn.net/store/manga/11362/01-002.0/compressed/iopm_002_001.jpg";
    private static final String SECOND_MANGA_PAGE = "http://l.mfcdn.net/store/manga/11362/01-002.0/compressed/iopm_002_002.jpg";
    private static final String THIRD_MANGA_PAGE = "http://l.mfcdn.net/store/manga/11362/01-002.0/compressed/iopm_002_003.jpg";

    private static final String FILE_NAME = "mangaPage";
    private static final int LENGTH = 3;

    @Test
    public void downloadMangaPagesFromWebsiteTest() throws IOException {
        Scraper scraper = new MangaPageScraperImpl();
        List<String> actual = scraper.downloadMangaPagesFromWebsite(FILE_NAME, LENGTH);
        List<String> exptected = new ArrayList<>();
        exptected.add(FIRST_MANGA_PAGE);
        exptected.add(SECOND_MANGA_PAGE);
        exptected.add(THIRD_MANGA_PAGE);

        assertEquals(actual, exptected);
    }
}
