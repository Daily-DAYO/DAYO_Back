package com.seoultech.dayo.domain.hashtag;

import com.seoultech.dayo.domain.post.Post;
import com.seoultech.dayo.domain.postHashtag.PostHashtag;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(indexes = @Index(name = "i_tag", columnList = "tag"))
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String tag;

    @OneToMany(mappedBy = "hashtag")
    private List<PostHashtag> postHashtags = new ArrayList<>();

    public Hashtag(String tag) {
        this.tag = tag;
    }

    protected Hashtag() {
    }
}
