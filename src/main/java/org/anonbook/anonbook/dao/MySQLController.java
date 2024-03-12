package org.anonbook.anonbook.dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import org.anonbook.anonbook.model.Post;
import org.anonbook.anonbook.request.AddPostRequest;
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
            jdbcConnector.getEntityManager().merge(post);

            jdbcConnector.getEntityTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (jdbcConnector.getEntityTransaction().isActive()) {
                jdbcConnector.getEntityTransaction().rollback();
            }
        }
    }

}
