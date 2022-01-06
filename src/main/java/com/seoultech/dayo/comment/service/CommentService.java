package com.seoultech.dayo.comment.service;


import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.comment.controller.response.ListAllCommentResponse;
import com.seoultech.dayo.comment.repository.CommentRepository;
import com.seoultech.dayo.comment.controller.request.CreateCommentRequest;
import com.seoultech.dayo.comment.controller.response.CreateCommentResponse;
import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.exception.NotExistMemberException;
import com.seoultech.dayo.exception.NotExistPostException;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public CreateCommentResponse createComment(String memberId, CreateCommentRequest request) {

        Post post = checkPost(request.getPostId());
        Member member = checkMember(memberId);

        Comment comment = request.toEntity(member);
        Comment savedComment = commentRepository.save(comment);
        savedComment.addPost(post);

        return new CreateCommentResponse(savedComment.getId());
    }

    @Transactional(readOnly = true)
    public ListAllCommentResponse listAllComment(Long postId) {

        Post post = checkPost(postId);
        List<ListAllCommentResponse.CommentDto> collect = post.getComments().stream()
                .map(comment -> ListAllCommentResponse.CommentDto.from(comment, comment.getMember()))
                .collect(toList());

        return ListAllCommentResponse.from(collect);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    private Member checkMember(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(NotExistMemberException::new);
    }

    private Post checkPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(NotExistPostException::new);
    }

}
