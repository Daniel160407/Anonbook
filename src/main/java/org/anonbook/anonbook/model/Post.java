package org.anonbook.anonbook.model;

import jakarta.persistence.*;

@Entity
@Table(name = "image")
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String imgName;
    @Column(name = "posttext")
    private String postText;

    public Post() {

    }

    public Post(String imgName, String postText) {
        this.imgName = imgName;
        this.postText = postText;
    }

    public Integer getId() {
        return id;
    }

    public String getImgName() {
        return imgName;
    }

    public String getPostText() {
        return postText;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }
}
