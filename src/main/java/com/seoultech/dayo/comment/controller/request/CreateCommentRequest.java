package com.seoultech.dayo.comment.controller.request;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class CreateCommentRequest {

    @NotNull
    private String memberId;

    @NotBlank
    private String contents;

    @NotNull
    private Long postId;

    public Comment toEntity(Member member, Post post) {
        Comment comment = new Comment(member, this.contents);
        comment.addPost(post);
        return comment;
    }
}
