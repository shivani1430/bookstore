package com.bookstore.bookstore.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author shivani_reddy
 */

@Component
public class RestApiManager {

    private Logger log = LogManager.getLogger(RestApiManager.class);

    @Autowired
    private RestTemplate restTemplate;

    public <T> T exchange(String fullUrl, HttpMethod method, HttpEntity httpEntity, Class<T> responseClassType) throws Exception {
        log.info("RestApiManager:exchange - api call with url : " + fullUrl + ", httpEntity : " + httpEntity + ", method : " + method);

        ResponseEntity<T> responseEntity;
        try {
            responseEntity = restTemplate.exchange(fullUrl, method, httpEntity, responseClassType);
            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("RestApiManager:exchange - Exception while making api call with url : " + fullUrl + ", httpEntity : " + httpEntity + ", method : " + method + ", error :" + e);
            throw new Exception("internal server error");
        }
    }
}
