package org.anonbook.anonbook.dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import org.anonbook.anonbook.model.Post;

public class MySQLController implements JDBCController{
    private CriteriaQuery<Post> select;
    private TypedQuery<Post> productTypedQuery;
    private final JDBCConnector jdbcConnector = JDBCConnector.getInstance();

    @Override
    public void addPost(Post post) {

    }
}
