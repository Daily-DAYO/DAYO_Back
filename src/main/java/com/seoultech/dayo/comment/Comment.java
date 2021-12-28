package com.seoultech.dayo.comment;

import com.seoultech.dayo.BaseTimeEntity;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
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

    @ManyToOne(fetch = FetchType.EAGER)
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
