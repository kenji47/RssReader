package com.kenji1947.rssreader.data.api.parser;

import com.kenji1947.rssreader.data.api.model.ApiFeed;

import java.io.InputStream;


public interface ParserWrapper {

    ApiFeed parseOrThrow(final InputStream inputStream, final String feedUrl) throws Exception;

    ApiFeed parse(final InputStream inputStream, final String feedUrl);
}
