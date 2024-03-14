package org.anonbook.anonbook.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.anonbook.anonbook.model.Comment;
import org.anonbook.anonbook.model.Post;
import org.anonbook.anonbook.model.PostComment;

public class JDBCConnector {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("anonbook");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private final EntityTransaction entityTransaction = entityManager.getTransaction();

    private final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    private CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
    private CriteriaQuery<Comment> commentCriteriaQuery = criteriaBuilder.createQuery(Comment.class);
    private CriteriaQuery<PostComment> postCommentCriteriaQuery = criteriaBuilder.createQuery(PostComment.class);
    private Root<Post> postRoot = criteriaQuery.from(Post.class);
    private Root<Comment> commentRoot = commentCriteriaQuery.from(Comment.class);
    private Root<PostComment> postCommentRoot = postCommentCriteriaQuery.from(PostComment.class);


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

    public void initializeCommentCriteria() {
        commentCriteriaQuery = criteriaBuilder.createQuery(Comment.class);
        commentRoot = commentCriteriaQuery.from(Comment.class);
    }

    public void initializePostCommentCriteria() {
        postCommentCriteriaQuery = criteriaBuilder.createQuery(PostComment.class);
        postCommentRoot = postCommentCriteriaQuery.from(PostComment.class);
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

    public CriteriaQuery<Comment> getCommentCriteriaQuery() {
        return commentCriteriaQuery;
    }

    public CriteriaQuery<PostComment> getPostCommentCriteriaQuery() {
        return postCommentCriteriaQuery;
    }

    public Root<Comment> getCommentRoot() {
        return commentRoot;
    }

    public Root<PostComment> getPostCommentRoot() {
        return postCommentRoot;
    }

    public Root<Post> getPostRoot() {
        return postRoot;
    }
}
