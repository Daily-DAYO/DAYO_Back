package com.seoultech.dayo.domain.Image;

import com.seoultech.dayo.domain.BaseTimeEntity;
import com.seoultech.dayo.domain.post.Post;
import lombok.Getter;

import javax.persistence.*;


@Entity
@Getter
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    private String saveFileName;

    private String uploadFileName;


}
