package com.bookstore.bookstore.queries;

import com.bookstore.bookstore.constants.DataSourceConstants;
import com.bookstore.bookstore.model.pojo.Status;
import com.bookstore.bookstore.pojo.search.SearchRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

/**
 * @author shivani_reddy
 */

public class MongoQuery {

    public static Query buildQuery(SearchRequest searchRequest) {
        Query searchQuery = Query.query(Criteria.where(DataSourceConstants.BOOK_STATUS_FIELD_NAME).is(Status.ACTIVE.toString()));
        if (!CollectionUtils.isEmpty(searchRequest.getStringValueFilters())) {
            searchRequest.getStringValueFilters().forEach(stringValueFilter -> {
                searchQuery.addCriteria(Criteria.where(stringValueFilter.getFieldName().getValue()).in(stringValueFilter.getValues()));
            });
        }
        if (!CollectionUtils.isEmpty(searchRequest.getRegexFilters())) {
            searchRequest.getRegexFilters().forEach(regexFilter -> {
                searchQuery.addCriteria(Criteria.where(regexFilter.getFieldName().getValue()).regex(regexFilter.getToken()));
            });

        }
        return searchQuery;

    }
}
