package org.anonbook.anonbook.dao;

import org.anonbook.anonbook.model.Post;
import org.anonbook.anonbook.request.AddPostRequest;
import org.anonbook.anonbook.request.GetCommentsRequest;
import org.anonbook.anonbook.request.GetPostRequest;
import org.anonbook.anonbook.response.GetCommentsResponse;
import org.anonbook.anonbook.response.GetPostResponse;

import java.util.List;

public interface JDBCController {
    List<GetPostResponse> getPosts();

    void addPost(AddPostRequest postRequest);

    Post getPost(GetPostRequest postRequest);

    List<GetCommentsResponse> getComments(GetCommentsRequest getCommentsRequest);

    void addComment(int postId, String comment);
}
