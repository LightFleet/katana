package com.norwood.util;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpRequestSerializer
{
    public static HttpRequest unserialize(String req) {
        System.out.println("Got request: " + req);
        String[] httpRequest = req.split(" ");

        String method = httpRequest[0];
        String path = httpRequest[1];
        String uriString;

        if (path.startsWith("http://") || path.startsWith("https://")) {
            uriString = path;
        } else {
            uriString = "https://localhost:8082" + path;
        }

        return HttpRequest.newBuilder()
            .uri(URI.create(uriString))
            .method(method, HttpRequest.BodyPublishers.noBody())
            .build();
    }

    public static String serialize(HttpRequest req) {
        return String.format("%s %s HTTP/1.1", req.method(), getPath(req.uri()));
    }
    
    public static String getPath(URI uri) {
        return uri.getRawPath() + 
            (uri.getRawQuery() != null ? "?" + uri.getRawQuery() : "") +
            (uri.getRawFragment() != null ? "#" + uri.getRawFragment() : "");
    }
}
