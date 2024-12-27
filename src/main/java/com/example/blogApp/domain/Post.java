package com.example.blogApp.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "postId")
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postId;

    @Column(name = "post_title", nullable = false, length = 50)
    private String title;

    @Column(name = "post_content", nullable = false, length = 2000)
    private String content;

    private String imageName;

    @Column(name = "post_date", nullable = false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable = false)
    private Date lastUpdatedDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    //@JsonBackReference(value = "category")
    private Category category;

    @ManyToOne
    //@JsonBackReference(value = "user")
    private User user;


}
