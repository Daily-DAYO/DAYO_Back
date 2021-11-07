package com.seoultech.dayo.domain.post;


import com.seoultech.dayo.domain.BaseTimeEntity;
import com.seoultech.dayo.domain.Image.Image;
import com.seoultech.dayo.domain.comment.Comment;
import com.seoultech.dayo.domain.heart.Heart;
import com.seoultech.dayo.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String contents;

    private String thumbnailImage;

    @OneToMany(mappedBy = "post")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Heart> hearts = new ArrayList<>();
}
