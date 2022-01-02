package com.seoultech.dayo.hashtag;

import com.seoultech.dayo.postHashtag.PostHashtag;
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

    public Hashtag(Long id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    protected Hashtag() {
    }
}
