package com.seoultech.dayo.domain.comment;

import com.seoultech.dayo.domain.BaseTimeEntity;
import com.seoultech.dayo.domain.member.Member;
import com.seoultech.dayo.domain.post.Post;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String contents;

    public void addPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    public Comment(Member member, String contents) {
        this.member = member;
        this.contents = contents;
    }

    protected Comment() {}


}
