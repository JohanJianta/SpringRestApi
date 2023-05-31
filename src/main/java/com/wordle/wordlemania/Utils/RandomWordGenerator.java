package com.wordle.wordlemania.Utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
// import java.util.concurrent.ExecutionException;

public class RandomWordGenerator {

    public static CompletableFuture<String> getRandomWord() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://random-word-api.herokuapp.com/word?length=6"))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(response -> response.replaceAll("[^A-Za-z]", "").toUpperCase());
    }
}
