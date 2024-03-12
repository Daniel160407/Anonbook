package org.anonbook.anonbook.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.anonbook.anonbook.model.Post;

public class JDBCConnector {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("anonbook");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private final EntityTransaction entityTransaction = entityManager.getTransaction();

    private final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    private CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
    private Root<Post> postRoot = criteriaQuery.from(Post.class);

    public static JDBCConnector instance;

    public static JDBCConnector getInstance() {
        if (instance == null) {
            instance = new JDBCConnector();
        }
        return instance;
    }

    public void initializeCriteria() {
        criteriaQuery = criteriaBuilder.createQuery(Post.class);
        postRoot = criteriaQuery.from(Post.class);
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityTransaction getEntityTransaction() {
        return entityTransaction;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    public CriteriaQuery<Post> getCriteriaQuery() {
        return criteriaQuery;
    }

    public Root<Post> getPostRoot() {
        return postRoot;
    }
}
