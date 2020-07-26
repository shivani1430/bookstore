package com.bookstore.bookstore.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;


/**
 * @author shivani_reddy
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class MediaPostServiceTest {

    @SpyBean
    MediaPostService mediaPostService;

    @Test
    public void testUpdateMediaPosts() throws Exception {
        await().atMost(Duration.ofHours(2)).untilAsserted(() -> {
            verify(mediaPostService, atLeast(2)).updateMediaPosts();
        });
    }
}