package com.seoultech.dayo.domain.postHashtag;

import com.seoultech.dayo.domain.hashtag.Hashtag;
import com.seoultech.dayo.domain.hashtag.service.HashtagService;
import com.seoultech.dayo.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
public class PostHashtag {

    @EmbeddedId
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("hashtagId")
    private Hashtag hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    private Post post;

    public PostHashtag(Post post, Hashtag hashtag) {
        this.hashtag = hashtag;
        this.post = post;
        post.getPostHashtags().add(this);
        hashtag.getPostHashtags().add(this);
    }

    @Embeddable
    @AllArgsConstructor
    @Getter
    public static class Key implements Serializable {
        private Long hashtagId;
        private Long postId;

        protected Key() {}
    }

}
