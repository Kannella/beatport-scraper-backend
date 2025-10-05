package com.beatport.scraper.backend.demo.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.beatport.scraper.backend.demo.service.Track;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BeatportService {

    private static final String API_URL =
            "https://api.beatport.com/v4/catalog/tracks/top/100/?per_page=100&page=1";

    private static final String TOKEN = "eyJ0eXAiOiAiSldUIiwgImFsZyI6ICJSUzI1NiIsICJraWQiOiAiYldZY2FnNk1ERTdreTFteDgxTUdpbjd5OGJheWdLRWFwQ2hWWlJpcjkwQSJ9.eyJqdGkiOiAiODU4ZDQ0OTItN2FkNy00MTU0LWIxNmItNzM1MjM1YmY1MTQwIiwgInN1YiI6ICJDaW5uYW1vb25fMjEiLCAiY2xpZW50X2lkIjogImVIVG9ORDNsc3YxWGRwYTY0NURkRjR3d0JVY2VCbml1S1BUMmRVQjEiLCAiYXVkIjogInVybjpicDppZGVudGl0eS1zZXJ2aWNlIiwgImlzcyI6ICJ1cm46YnA6aWRlbnRpdHktc2VydmljZSIsICJpYXQiOiAxNzU5NzA1NTE2LCAiZXhwIjogMTc1OTcwNjExNiwgInNjb3BlIjogImFwcDpwcm9zdG9yZSB1c2VyOmRqIn0.HoyrzLdN55ToScHtgSGv1S4-PtPvf9EQdGkRwtO3Lfurnh6sm4v7uZBrt4VzB3uqFMZ2TyNotn-JYJsGaWOE8lmBYl-BcFlwzjvPw4kFYe0QUIdTb_aiiyUSkSu5BifaukQnQ3qDN0ANOP8h9TTkIvYMMNY46_zyn6P9Y_Btuh-iSQLVXKe1ad5yfz_R9WbBUS7eWwQl73ygdVARUYkkcQO7Jnc5PAeezqoMvzpZwDSZKih6_RYapnxIGcoKQftmxO4gog5UbHE-yZlSdBDdV1X5bfTI-K6thHQI8KMDkLon5VLXzTLRrhsnpqcw6UYpMM7_Xr2ShikIwAw1XX6SuWXkpvpZqJyRiG4lhvmBs8TZ2DS1wzbNInQVBBqBrLe742coALdTeiAtfuTPd-vwGaANeHHZRKKhHmpvxaaxTb-ve75MlL0DBEwROAQuw7Zv03ZuSA7X0Z4XGDFtatB0ASL7ZrhVrnfE14qzj0Clp3zEpAWm616kaXo_QAvwc_efvZUSSdWgShBECjVPKUQSbp7bwiEtMD_uwDQlF4k7dak2yNA6gJs38XsZqAJNfWmSLvP6xu_7I-3YZ4qau_hc3aqJUAGzQTVcVzXokXFaRzc1fCf54rEfs-Iiyl-DoFd3Jc1SePoVZPj-Ed8UFBxtHJYvhNufeAkufjG1qNI9BRs";
    public List<Track> getTopTracks() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + TOKEN)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            JSONArray results = json.getJSONArray("results");

            List<Track> tracks = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject item = results.getJSONObject(i);
                String name = item.getString("name");
                String mix = item.optString("mix_name", "");
                JSONArray artists = item.getJSONArray("artists");
                String artistName = artists.getJSONObject(0).getString("name");
                tracks.add(new Track(name, mix, artistName));
            }

            return tracks;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
