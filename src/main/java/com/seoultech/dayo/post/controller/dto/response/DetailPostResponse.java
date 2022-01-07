package com.seoultech.dayo.post.controller.dto.response;


import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.comment.controller.response.ListAllCommentResponse;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.postHashtag.PostHashtag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
public class DetailPostResponse {

    private String nickname;

    private String profileImg;

    private LocalDateTime createDateTime;

    private Category category;

    private List<String> images;

    private String contents;

    private List<String> hashtags;

    private int heartCount;

    private int commentCount;

    private List<CommentDto> comments;

    public static DetailPostResponse from(Post post, Member member) {
        List<String> collectImages = post.getImages().stream()
                .map(Image::getStoreFileName)
                .collect(toList());

        List<String> collectHashtags = post.getPostHashtags().stream()
                .map(postHashtag -> postHashtag.getHashtag().getTag())
                .collect(toList());

        List<CommentDto> collect = post.getComments()
                .stream()
                .map(CommentDto::from)
                .collect(toList());

        return new DetailPostResponse(member.getNickname(), member.getProfileImg(), post.getCreatedDate(), post.getCategory(), collectImages, post.getContents(), collectHashtags, post.getHearts().size(), post.getComments().size(), collect);
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

        public static CommentDto from(Comment comment) {
            return new CommentDto(comment.getId(), comment.getMember().getId(), comment.getMember().getNickname(), comment.getMember().getProfileImg(), comment.getContents(), comment.getCreatedDate());
        }

    }

}
