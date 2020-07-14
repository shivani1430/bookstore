package com.bookstore.bookstore.pojo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author shivani_reddy
 */
public class BaseResponse extends ResponseEntity {
    public BaseResponse(Object body) {
        super(body, HttpStatus.OK);
    }

    public BaseResponse(Object body, HttpStatus status) {
        super(body, status);
    }
}
