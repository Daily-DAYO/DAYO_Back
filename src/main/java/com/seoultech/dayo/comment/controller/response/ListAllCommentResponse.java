package com.seoultech.dayo.comment.controller.response;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class ListAllCommentResponse {

    private int count;
    private List<CommentDto> data;

    public static ListAllCommentResponse from(List<CommentDto> data) {
        return new ListAllCommentResponse(data.size(), data);
    }

    @Data
    @AllArgsConstructor
    static public class CommentDto {

        private Long commentId;
        private String memberId;
        private String nickname;
        private String profileImg;
        private String contents;
        private LocalDateTime createTime;

        public static CommentDto from(Comment comment, Member member) {
            return new CommentDto(comment.getId(), member.getId(), member.getNickname(), member.getProfileImg().getStoreFileName(), comment.getContents(), comment.getCreatedDate());
        }

    }
}
