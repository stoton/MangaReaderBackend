package com.github.stoton.test;


import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public interface Scraper {
    List<String> downloadMangaPagesFromWebsite(String fileName, int length) throws IOException;
}
