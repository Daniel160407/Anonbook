package org.anonbook.anonbook.dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.anonbook.anonbook.model.Comment;
import org.anonbook.anonbook.model.Post;
import org.anonbook.anonbook.model.PostComment;
import org.anonbook.anonbook.request.AddPostRequest;
import org.anonbook.anonbook.request.GetCommentsRequest;
import org.anonbook.anonbook.request.GetPostRequest;
import org.anonbook.anonbook.response.GetCommentsResponse;
import org.anonbook.anonbook.response.GetPostResponse;

import java.util.ArrayList;
import java.util.List;

public class MySQLController implements JDBCController {
    private CriteriaQuery<Post> select;
    private TypedQuery<Post> typedQuery;
    private final JDBCConnector jdbcConnector = JDBCConnector.getInstance();

    @Override
    public List<GetPostResponse> getPosts() {
        jdbcConnector.initializeCriteria();

        select = jdbcConnector.getCriteriaQuery().select(
                jdbcConnector.getPostRoot()
        );

        typedQuery = jdbcConnector.getEntityManager().createQuery(select);

        List<Post> posts = typedQuery.getResultList();
        List<GetPostResponse> postResponses = new ArrayList<>();
        posts.forEach(post -> postResponses.add(new GetPostResponse(post.getId(), post.getPostText(), post.getImgName(), post.getTime())));

        return postResponses;
    }

    @Override
    public void addPost(AddPostRequest postRequest) {
        jdbcConnector.initializeCriteria();

        try {
            jdbcConnector.getEntityTransaction().begin();

            Post post = new Post(postRequest.title(), postRequest.imgName(), postRequest.time());
            System.out.println(post);
            jdbcConnector.getEntityManager().persist(post);

            jdbcConnector.getEntityTransaction().commit();
        } catch (RuntimeException e) {
            if (jdbcConnector.getEntityTransaction().isActive()) {
                jdbcConnector.getEntityTransaction().rollback();
            }
        }
    }

    @Override
    public Post getPost(GetPostRequest postRequest) {
        jdbcConnector.initializeCriteria();

        select = jdbcConnector.getCriteriaQuery().select(
                jdbcConnector.getPostRoot()
        ).where(jdbcConnector.getCriteriaBuilder().equal(jdbcConnector.getPostRoot().get("id"), postRequest.postId()));

        typedQuery = jdbcConnector.getEntityManager().createQuery(select);

        return typedQuery.getSingleResult();
    }

    @Override
    public List<GetCommentsResponse> getComments(GetCommentsRequest getCommentsRequest) {
        jdbcConnector.initializePostCommentCriteria();

        CriteriaQuery<PostComment> postComments = jdbcConnector.getPostCommentCriteriaQuery().select(
                jdbcConnector.getPostCommentRoot()
        ).where(jdbcConnector.getCriteriaBuilder().equal(jdbcConnector.getPostCommentRoot().get("postId"), getCommentsRequest.postId()));

        TypedQuery<PostComment> postCommentTypedQuery = jdbcConnector.getEntityManager().createQuery(postComments);

        List<PostComment> postCommentList = postCommentTypedQuery.getResultList();

        jdbcConnector.initializeCommentCriteria();

        List<GetCommentsResponse> getCommentsResponses = new ArrayList<>();

        for (PostComment postComment : postCommentList) {
            CriteriaQuery<Comment> comments = jdbcConnector.getCommentCriteriaQuery().select(
                    jdbcConnector.getCommentRoot()
            ).where(jdbcConnector.getCriteriaBuilder().equal(jdbcConnector.getCommentRoot().get("id"), postComment.getCommentId()));

            TypedQuery<Comment> commentTypedQuery = jdbcConnector.getEntityManager().createQuery(comments);

            Comment comment = commentTypedQuery.getSingleResult();
            getCommentsResponses.add(new GetCommentsResponse(comment.getId(), comment.getComment()));
        }

        return getCommentsResponses;
    }

    @Override
    public void addComment(int postId, String comment) {
        //add comment to comment table
        jdbcConnector.initializeCommentCriteria();

        try {
            jdbcConnector.getEntityTransaction().begin();

            Comment commentObject = new Comment(comment);
            jdbcConnector.getEntityManager().merge(commentObject);

            jdbcConnector.getEntityTransaction().commit();
        } catch (RuntimeException e) {
            if (jdbcConnector.getEntityTransaction().isActive()) {
                jdbcConnector.getEntityTransaction().rollback();
            }
        }

        //get last comment id
        CriteriaQuery<Comment> selectComment = jdbcConnector.getCriteriaBuilder().createQuery(Comment.class);
        Root<Comment> commentRoot = selectComment.from(Comment.class);

        selectComment.select(commentRoot).orderBy(jdbcConnector.getCriteriaBuilder().desc(commentRoot.get("id")));

        TypedQuery<Comment> selectCommentTypedQuery = jdbcConnector.getEntityManager().createQuery(selectComment);
        selectCommentTypedQuery.setMaxResults(1);

        Comment lastComment = selectCommentTypedQuery.getSingleResult();


        //add post id and comment id to post_comment table
        jdbcConnector.initializePostCommentCriteria();
        try {
            jdbcConnector.getEntityTransaction().begin();

            PostComment postComment = new PostComment(postId, lastComment.getId());
            jdbcConnector.getEntityManager().persist(postComment);

            jdbcConnector.getEntityTransaction().commit();
        } catch (RuntimeException e) {
            if (jdbcConnector.getEntityTransaction().isActive()) {
                jdbcConnector.getEntityTransaction().rollback();
            }
        }
    }
}
