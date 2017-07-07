package com.github.stoton.service.impl;

import com.github.stoton.domain.MangaChapter;
import com.github.stoton.domain.MangaPage;
import com.github.stoton.repository.MangaPageRepository;
import com.github.stoton.service.MangaPageService;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MangaPageServiceImpl implements MangaPageService {
    private final Logger logger = Logger.getLogger(MangaPageServiceImpl.class);
    private static final int SLASHES_BEFORE_MANGA_PAGE = 7;
    private static final String ERROR_WITH_CONNECTION = "Connection lost - error 503. Wait for next try.";
    private static final String HTML = ".html";
    private static final String READ_IMAGE = "read_img";
    private static final String IMAGE = "img";
    private static final String SOURCE = "src";

    @Autowired
    private MangaPageRepository mangaPageRepository;

    @Override
    public List<String> insertAndGetMangaPages(MangaChapter mangaChapter) {
        long currentMangaChapterId = mangaChapter.getId();
        int length = mangaChapter.getLength();

        List<MangaPage> mangaPages = mangaPageRepository.findMangaPagesById(currentMangaChapterId);

        if(!mangaPages.isEmpty()) {
            List<String> mangaPagesUrls = new ArrayList<>();
            for(MangaPage mangaPage : mangaPages) {
                String mangaPageUrl = mangaPage.getUrl();
                mangaPagesUrls.add(mangaPageUrl);
            }
            return mangaPagesUrls;
        }

        else {
            String urlToProcess = mangaChapter.getUrl();
            String correctUrl = processToRightUrl(urlToProcess);

            List<MangaPage> mangaPagesToSave = new ArrayList<>();
            List<String> imagesUrls = downloadMangaPages(correctUrl, length);

            for (String imageUrl : imagesUrls) {
                MangaPage mangaPage = new MangaPage(imageUrl, mangaChapter);
                mangaPagesToSave.add(mangaPage);
            }

            for (MangaPage mangaPage : mangaPagesToSave) {
                mangaPageRepository.save(mangaPage);
            }
            return imagesUrls;
        }
    }

    @Override
    public List<String> downloadMangaPages(String url, int length)  {
        List<String> imagesUrls = new ArrayList<>();
        for(int i = 1; i <= length; i++) {
            Document document;
            boolean isScraped = false;

            while(!isScraped) {
                try {
                    String urlToConnect = url + i + HTML;
                    document = Jsoup.connect(urlToConnect).get();
                    isScraped = true;
                    Element element = document.getElementsByClass(READ_IMAGE).first();
                    String imageUrl = element.select(IMAGE).attr(SOURCE);
                    imagesUrls.add(imageUrl);
                } catch (Exception e) {
                    logger.error(ERROR_WITH_CONNECTION);
                }
            }
        }
        return imagesUrls;
    }

    private String processToRightUrl(String urlToCut) {
        StringBuilder correctUrl = new StringBuilder();

        int slashes = 0;
        for (int i = 0; i < urlToCut.length(); i++) {
            if (urlToCut.charAt(i) == '/') {
                slashes++;
            }

            if (slashes == SLASHES_BEFORE_MANGA_PAGE) {
                correctUrl.append("/");
                break;
            }
            correctUrl.append(urlToCut.charAt(i));
        }
        return correctUrl.toString();
    }
}
