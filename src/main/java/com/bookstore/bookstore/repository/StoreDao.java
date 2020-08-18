package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.exceptions.DbException;
import com.bookstore.bookstore.exceptions.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;


/**
 * @author shivani_reddy
 */

@Repository
public class StoreDao {

    private Logger log = LogManager.getLogger(StoreDao.class);

    @Autowired
    private MongoOperations mongoOperations;

    public <T> T insert(T doc) throws DbException {
        try {
            doc = mongoOperations.insert(doc);
            if (doc == null) {
                log.error("StoreDao:insert - insert operation failed for :{}", doc);
                throw new DbException("insert operation failed");
            }
            return doc;
        } catch (Exception e) {
            log.error("StoreDao:insert - insert operation failed for :{}, error : {}", doc, e.getMessage());
            throw new DbException("inert operation failed");
        }
    }

    public <T> T save(T doc) throws DbException {
        try {
            doc = mongoOperations.save(doc);
            if (doc == null) {
                log.error("StoreDao:save - update operation failed for :{}", doc);
                throw new DbException("update operation failed");
            }
            return doc;
        } catch (OptimisticLockingFailureException e) {
            log.error("StoreDao:save - version conflict for :{}, error : {}", doc, e.getMessage());
            throw new DbException("version conflict");
        } catch (Exception e) {
            log.error("StoreDao:save - update operation failed for :{}, error : {}", doc, e.getMessage());
            throw new DbException("update operation failed");
        }
    }

    public <T> T get(String id, Class<T> tClass) throws NotFoundException {
        try {
            T doc = mongoOperations.findById(id, tClass);
            if (doc == null) {
                log.error("StoreDao:get - doc does not exist with id : " + id);
                throw new NotFoundException("doc does not exist with id : " + id);
            }
            return doc;
        } catch (Exception e) {
            log.error("StoreDao:get - doc does not exist with id : " + id);
            throw new NotFoundException("doc does not exist with id : " + id);
        }
    }
}
