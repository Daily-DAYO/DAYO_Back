package com.seoultech.dayo.domain.heart;


import com.seoultech.dayo.domain.member.Member;
import com.seoultech.dayo.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
public class Heart {


    @EmbeddedId
    private Key key = new Key();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    private Post post;

    public void addPost(Post post) {
        this.post = post;
        post.getHearts().add(this);
    }

    public Heart(Member member) {
        this.member = member;
    }

    @Embeddable
    @AllArgsConstructor
    @Getter
    public static class Key implements Serializable {
        private String memberId;
        private Long postId;

        protected Key() {}
    }

    protected Heart() {}
}
