package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.manager.RestApiManager;
import com.bookstore.bookstore.pojo.MediaPost;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shivani_reddy
 */

@Service
public class MediaPostService {

    private Logger log = LogManager.getLogger(MediaPostService.class);

    @Value("${media.coverage.url}")
    private String url;

    @Autowired
    private RestApiManager restApiManager;

    @Autowired
    private ObjectMapper objectMapper;

    private List<MediaPost> mediaPostList;

    public List<MediaPost> getMediaPosts() throws Exception {
        if (mediaPostList == null || mediaPostList.size() <= 0) {
            mediaPostList = initiateMediaPostsRequest();
        }
        return mediaPostList;

    }

    private List<MediaPost> initiateMediaPostsRequest() throws Exception {
        String response = restApiManager.exchange(url, HttpMethod.GET, null, String.class);
        if (response == null) {
            log.error("MediaPostService:initiateMediaPostsRequest - media post response is null");
            throw new Exception("internal server error");
        }
        return objectMapper.readValue(response, new TypeReference<List<MediaPost>>() {
        });
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void updateMediaPosts() {
        try {
            mediaPostList = initiateMediaPostsRequest();
        } catch (Exception e) {
            log.error("MediaPostService:updateMediaPosts - updating failed with error : {} ", e.getMessage());
        }
    }
}
