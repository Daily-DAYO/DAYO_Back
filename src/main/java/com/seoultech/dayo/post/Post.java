package com.seoultech.dayo.post;


import com.seoultech.dayo.BaseTimeEntity;
import com.seoultech.dayo.Image.Image;
import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.heart.Heart;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.postHashtag.PostHashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank
    private String contents;

    @NotNull
    private String thumbnailImage;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @ManyToOne(fetch = FetchType.LAZY)
    private Folder folder;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Image> images;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostHashtag> postHashtags = new ArrayList<>();

    public void addFolder(Folder folder) {
        this.folder = folder;
        folder.getPosts().add(this);
    }

    @Builder
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
