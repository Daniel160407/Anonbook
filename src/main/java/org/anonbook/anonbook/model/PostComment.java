package org.anonbook.anonbook.model;

import jakarta.persistence.*;

@Entity
@Table(name = "post_comment")
public class PostComment {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @Column(name = "comment_id")
    private Integer commentId;

    public PostComment(Integer postId, Integer commentId) {
        this.postId = postId;
        this.commentId = commentId;
    }

    public PostComment() {

    }

    public Integer getPostId() {
        return postId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
}
