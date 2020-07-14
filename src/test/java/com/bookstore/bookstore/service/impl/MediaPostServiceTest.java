package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.pojo.MediaPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author shivani_reddy
 */

@SpringBootTest
class MediaPostServiceTest {

    @Autowired
    MediaPostService mediaPostService;

    @Test
    void getMediaPosts() throws Exception {
        List<MediaPost> mediaPostList = mediaPostService.getMediaPosts();
        System.out.println(mediaPostList);
    }
}