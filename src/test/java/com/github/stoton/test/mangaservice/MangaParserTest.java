package com.github.stoton.test.mangaservice;

import com.github.stoton.domain.Manga;
import com.github.stoton.test.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class MangaParserImpl implements Parser {
    private static final String MANGA_IMAGE = "manga_img";
    private static final String TITLE = "title";
    private static final String A_ATTRIBUTE = "a";
    private static final String IMAGE = "img";
    private static final String SOURCE = "src";
    private static final String REL = "rel";
    private static final String DIV_MANGA_TEXT = "div.manga_text > a";
    private static final String HREF = "href";

    @Override
    public List<Manga> parseDocument(Document document) {
        List<Manga> list = new ArrayList<>();
        Elements elements = document.getElementsByClass(MANGA_IMAGE);
        List<String> titles = document.getElementsByClass(TITLE).select(A_ATTRIBUTE).eachText();
        List<String> imagesUrl = elements.select(IMAGE).eachAttr(SOURCE);
        List<String> ids = elements.eachAttr(REL);
        List<String> chaptersUrl = document.select(DIV_MANGA_TEXT).eachAttr(HREF);

        for(int i = 0; i < titles.size(); i++) {
            Manga manga = new Manga(ids.get(i), titles.get(i), imagesUrl.get(i), chaptersUrl.get(i));
            list.add(manga);
        }
        return list;
    }
}

public class MangaParserTest {
    @Test
    public void mangaParserTestWhenEverythingIsOk() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("mangaFiles/mangaDirectory.html").getFile());
        Document doc = Jsoup.parse(file, "UTF-8");

        Parser parser = new MangaParserImpl();
        List<Manga> actual = parser.parseDocument(doc);
        List<Manga> exptected = new ArrayList<>();
        exptected.add(new Manga("11362", "Onepunch-Man", "http://l.mfcdn.net/store/manga/11362/cover.jpg?v=1498117442", "http://mangafox.me/manga/onepunch_man/"));
        exptected.add(new Manga("16627", "Tales of Demons and Gods", "http://l.mfcdn.net/store/manga/16627/cover.jpg?v=1499325542","http://mangafox.me/manga/tales_of_demons_and_gods/"));
        exptected.add(new Manga("246", "Fairy Tail", "http://l.mfcdn.net/store/manga/246/cover.jpg?v=1499332941", "http://mangafox.me/manga/fairy_tail/"));

        assertEquals(exptected, actual);
    }

    @Test
    public void mangaParserTestWhenDocumentIsNotComplete() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("mangaFiles/notCompleteMangaDirectory.html").getFile());
        Document doc = Jsoup.parse(file, "UTF-8");

        Parser parser = new MangaParserImpl();
        List<Manga> actual = parser.parseDocument(doc);
        List<Manga> exptected = new ArrayList<>();

        assertEquals(exptected, actual);
    }

}