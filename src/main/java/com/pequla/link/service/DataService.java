package com.pequla.link.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pequla.link.model.DataModel;
import com.pequla.link.model.ErrorModel;
import lombok.Getter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Getter
public class DataService {

    private static DataService instance;

    private final HttpClient client;
    private final ObjectMapper mapper;

    private DataService() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(3))
                .build();

        // Register json mapper
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    public DataModel getLinkData(String uuid, String guild, String role) throws IOException, InterruptedException {
        String url = "https://link.samifying.com/api/user/" + guild + "/" + role + "/" + uuid;
        return mapper.readValue(get(url), DataModel.class);
    }

    public DataModel getLinkData(String uuid, String guild) throws IOException, InterruptedException {
        String url = "https://link.samifying.com/api/user/" + guild + "/" + uuid;
        return mapper.readValue(get(url), DataModel.class);
    }

    public DataModel getLookupData(String name) throws IOException, InterruptedException {
        String json = get("https://link.samifying.com/api/lookup/" + name);
        return mapper.readValue(json, DataModel.class);
    }

    private String get(String url) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> rsp = client.send(req, HttpResponse.BodyHandlers.ofString());

        // Generating response based of status codes
        int code = rsp.statusCode();
        if (code == 200) {
            // Response is OK
            return rsp.body();
        }
        if (code == 500) {
            throw new RuntimeException(mapper.readValue(rsp.body(), ErrorModel.class).getMessage());
        }
        throw new RuntimeException("Response code " + code);
    }
}
