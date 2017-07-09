package com.github.stoton.service.impl;

import com.github.stoton.domain.Manga;
import com.github.stoton.repository.MangaRepository;
import com.github.stoton.service.MangaService;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MangaServiceImpl implements MangaService {
    private final Logger logger = Logger.getLogger(MangaServiceImpl.class);
    private static final String MANGA_IMAGE = "manga_img";
    private static final String TITLE = "title";
    private static final String A_ATTRIBUTE = "a";
    private static final String IMAGE = "img";
    private static final String SOURCE = "src";
    private static final String REL = "rel";
    private static final String DIV_MANGA_TEXT = "div.manga_text > a";
    private static final String HREF = "href";
    private String mangaDirectoryUrl;

    @Autowired
    private MangaRepository mangaRepository;

    @Override
    public void insertManga() {
            List<Manga> mangaList = getDataFromWebsite(mangaDirectoryUrl);
            for(int i = 1; i <= mangaList.size(); i++) {
                Manga manga = mangaRepository.find(i);
                String imageUrl = mangaList.get(i-1).getImageUrl();
                manga.setImageUrl(imageUrl);
                mangaRepository.saveOrUpdate(manga);
            }
        }

    @Override
    public List<Manga> getDataFromWebsite(String url) {
        List<Manga> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            if(document == null)
                return new ArrayList<>();
            Elements elements = document.getElementsByClass(MANGA_IMAGE);
            List<String> titles = document.getElementsByClass(TITLE).select(A_ATTRIBUTE).eachText();
            List<String> imagesUrl = elements.select(IMAGE).eachAttr(SOURCE);
            List<String> ids = elements.eachAttr(REL);
            List<String> chaptersUrl = document.select(DIV_MANGA_TEXT).eachAttr(HREF);
            list = buildMangaList(ids, titles, imagesUrl, chaptersUrl);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return list;
    }

    @Override
    public void setMangaDirectoryUrl(String mangaDirectoryUrl) {
        this.mangaDirectoryUrl = mangaDirectoryUrl;
    }

    private List<Manga> buildMangaList(List<String> ids, List<String> titles, List<String> imagesUrl, List<String> url) {
        List<Manga> list = new ArrayList<>();
        int size = ids.size();

        for(int i = 0; i < size; i++) {
            Manga manga = new Manga(ids.get(i), titles.get(i), imagesUrl.get(i), url.get(i));
            list.add(manga);
        }
        return list;
    }
}
