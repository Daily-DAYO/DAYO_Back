package com.seoultech.dayo.domain.post;


import com.seoultech.dayo.domain.BaseTimeEntity;
import com.seoultech.dayo.domain.Image.Image;
import com.seoultech.dayo.domain.comment.Comment;
import com.seoultech.dayo.domain.heart.Heart;
import com.seoultech.dayo.domain.member.Member;
import lombok.Getter;
import lombok.NonNull;

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

    @NonNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Image> images;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Heart> hearts = new ArrayList<>();

    public Post(Member member, String contents, String thumbnailImage, Category category, Privacy privacy, List<Image> images) {
        this.member = member;
        this.contents = contents;
        this.thumbnailImage = thumbnailImage;
        this.category = category;
        this.privacy = privacy;
        this.images = images;
    }

    protected Post() { }
}
