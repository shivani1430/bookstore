package com.bookstore.bookstore.repository.impl;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.pojo.search.SearchRequest;
import com.bookstore.bookstore.queries.MongoQuery;
import com.bookstore.bookstore.repository.Idao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shivani_reddy
 */


@Repository
public class Bookdao implements Idao<Book> {

    private Logger log = LogManager.getLogger(Bookdao.class);

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public Book insert(Book book) throws DbException {
        try {
            book = mongoOperations.insert(book);
            if (book == null) {
                log.error("Bookdao:insert - insert operation failed for :{}", book);
                throw new DbException("insert operation failed");
            }
            return book;
        } catch (Exception e) {
            log.error("Bookdao:insert - insert operation failed for :{}, error : {}", book, e.getMessage());
            throw new DbException("inert operation failed");
        }
    }

    @Override
    public Book save(Book book) throws DbException {
        try {
            book = mongoOperations.save(book);
            if (book == null) {
                log.error("Bookdao:save - update operation failed for :{}", book);
                throw new DbException("update operation failed");
            }
            return book;
        } catch (OptimisticLockingFailureException e) {
            log.error("Bookdao:save - version conflict for :{}, error : {}", book, e.getMessage());
            throw new DbException("version conflict");
        } catch (Exception e) {
            log.error("Bookdao:save - update operation failed for :{}, error : {}", book, e.getMessage());
            throw new DbException("update operation failed");
        }
    }

    @Override
    public Book get(String id) throws NotFoundException {
        Book book = mongoOperations.findById(id, Book.class);
        if (book != null) {
            return book;
        }
        log.error("Bookdao:get - Book does not exist with id : " + id);
        throw new NotFoundException("Book does not exist with id : " + id);
    }

    @Override
    public List<Book> search(SearchRequest searchRequest) throws DbException {
        Query searchQuery = MongoQuery.buildQuery(searchRequest);
        try {
            List<Book> books = mongoOperations.find(searchQuery, Book.class);
            return books;
        } catch (Exception e) {
            log.error("Bookdao:search - search failed for :{}, error : {}", searchRequest, e.getMessage());
            throw new DbException("search failed");
        }
    }


}
