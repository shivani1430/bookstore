package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.manager.RestApiManager;
import com.bookstore.bookstore.pojo.MediaPost;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shivani_reddy
 */

@Service
public class MediaPostService {

    @Value("${media.coverage.url}")
    private String url;

    @Autowired
    private RestApiManager restApiManager;

    @Autowired
    private ObjectMapper objectMapper;

    public List<MediaPost> getMediaPosts() throws Exception {
        String response = restApiManager.exchange(url, HttpMethod.GET, null, String.class);
        return objectMapper.readValue(response, new TypeReference<List<MediaPost>>() {
        });
    }
}
