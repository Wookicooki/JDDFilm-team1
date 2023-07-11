package com.example.jangdocdaefilm.service;


import com.example.jangdocdaefilm.dto.*;
import com.example.jangdocdaefilm.mapper.MyMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class MyServiceImpl implements MyService {

    @Autowired
    private MyMapper myMapper;

    @Value("${jangDocDae.tmdb.json.Authorization}")
    private String serviceAuthor;

    @Override
    public List<FreeDto> myFree(String id) throws Exception {
        return myMapper.myFree(id);
    }

    @Override
    public List<NowDto> myNow(String id) throws Exception {
        return myMapper.myNow(id);
    }

    @Override
    public List<DisDto> myDis(String id) throws Exception {
        return myMapper.myDis(id);
    }

    @Override
    public List<RecomDto> getRecoms(String userName) throws Exception {
        return myMapper.selectRecoms(userName);
    }

    @Override
    public List<String> getRecomsLike(String userName) throws Exception {
        return myMapper.selectRecomsLike(userName);
    }

    @Override
    public String setPosterPath(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", serviceAuthor)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        PosterDto poster = gson.fromJson(response.body(), PosterDto.class);
        return poster.getPoster_path();
    }

    @Override
    public List<RecomDto> getLikeRecom() throws Exception {
        return myMapper.selectAllRecom();
    }
}
