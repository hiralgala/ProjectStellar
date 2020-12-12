package com.example.restservice;

import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SnippetModel {
    private String name;
    private String url;
    private String expires_in;
    private String snippet;
    private static String hostName = "http://localhost:8080/snippets/";

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.ENGLISH);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }


    public SnippetModel(String name, int expires_in, String snippet) {
        this.name = name;
        this.url = hostName + name;
        this.expires_in = formatter.format(DateUtils.addSeconds(new Date(), expires_in));
        this.snippet = snippet;
    }

    public void accessSnippet() {
        String extend = formatter.format(DateUtils.addSeconds(new Date(), 30));
        this.setExpires_in(extend);

    }
}
