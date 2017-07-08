package com.github.stoton.test;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public interface Parser<T> {
    List<T> parseDocument(Document document) throws IOException;
}
