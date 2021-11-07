package com.seoultech.dayo.domain.comment.controller.request;

import com.seoultech.dayo.domain.comment.Comment;
import com.seoultech.dayo.domain.member.Member;
import com.seoultech.dayo.domain.post.Post;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    private String memberId;

    private String contents;

    private Long postId;

    public Comment toEntity(Member member, Post post) {

        Comment comment = new Comment(member, this.contents);
        comment.addPost(post);
        return comment;

    }
}
