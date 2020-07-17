package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.pojo.search.SearchRequest;

import java.util.List;

/**
 * @author shivani_reddy
 */

public interface Idao<T> {

    T insert(T object) throws DbException;

    T save(T object) throws DbException;

    T get(String id) throws NotFoundException;

    List<T> search(SearchRequest searchRequest) throws DbException;
}
