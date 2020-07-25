package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.manager.RestApiManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author shivani_reddy
 */

@RunWith(SpringRunner.class)
public class MediaPostServiceTest {

    @InjectMocks
    MediaPostService mediaPostService;

    @Mock
    private RestApiManager restApiManager;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test(expected = Exception.class)
    public void getMediaPosts() throws Exception {
        mediaPostService.getMediaPosts();
    }
}