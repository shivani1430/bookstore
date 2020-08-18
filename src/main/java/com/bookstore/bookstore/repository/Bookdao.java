package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.NotFoundException;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.pojo.Status;
import com.bookstore.bookstore.pojo.search.SearchRequest;
import com.bookstore.bookstore.queries.MongoQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shivani_reddy
 */


@Repository
public class Bookdao extends StoreDao {

    private Logger log = LogManager.getLogger(Bookdao.class);

    @Autowired
    private MongoOperations mongoOperations;

    public Book get(String id) throws NotFoundException {
        Book book = super.get(id, Book.class);
        if (book.getStatus().equals(Status.ACTIVE)) {
            return book;
        } else {
            log.error("Bookdao:get - book is inactive :{}", book);
            throw new NotFoundException("book does not exist with id : " + id);
        }
    }

    public List<Book> bulkGet(List<String> ids) throws DbException {
        Query batchGetQuery = MongoQuery.buildQuery(ids);
        try {
            return mongoOperations.find(batchGetQuery, Book.class);
        } catch (Exception e) {
            log.error("Bookdao:bulkGet - failed for : {} , error : {}", ids, e.getMessage());
            throw new DbException("bulk get Filed");
        }
    }

    public List<Book> search(SearchRequest searchRequest) throws DbException {
        Query searchQuery = MongoQuery.buildQuery(searchRequest);
        try {
            return mongoOperations.find(searchQuery, Book.class);
        } catch (Exception e) {
            log.error("Bookdao:search - search failed for :{}, error : {}", searchRequest, e.getMessage());
            throw new DbException("search failed");
        }
    }


}
