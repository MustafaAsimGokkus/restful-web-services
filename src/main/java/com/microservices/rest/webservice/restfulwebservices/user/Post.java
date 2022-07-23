package com.microservices.rest.webservice.restfulwebservices.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Integer id;
    private String description;

    @ManyToOne(fetch= FetchType.LAZY)//IT WONT FETCH UNLESS U CALL IT
    @JsonIgnore
    private UserSocialMedia userSocialMedia;

    public Post() {
    }

    public Post(Integer id, String description, UserSocialMedia userSocialMedia) {
        this.id = id;
        this.description = description;
        this.userSocialMedia = userSocialMedia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserSocialMedia getUserSocialMedia() {
        return userSocialMedia;
    }

    public void setUserSocialMedia(UserSocialMedia userSocialMedia) {
        this.userSocialMedia = userSocialMedia;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
