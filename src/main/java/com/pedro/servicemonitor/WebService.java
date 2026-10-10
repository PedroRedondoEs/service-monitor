package com.pedro.servicemonitor;

public class WebService {

    private String url;

    public WebService(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return url;
    }
}