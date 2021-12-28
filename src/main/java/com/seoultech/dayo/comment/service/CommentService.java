package com.seoultech.dayo.comment.service;


import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.comment.controller.response.ListAllCommentResponse;
import com.seoultech.dayo.comment.repository.CommentRepository;
import com.seoultech.dayo.comment.controller.request.CreateCommentRequest;
import com.seoultech.dayo.comment.controller.response.CreateCommentResponse;
import com.seoultech.dayo.exception.NotExistPostException;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public CreateCommentResponse createComment(CreateCommentRequest request) {

        Optional<Post> postOptional = postRepository.findById(request.getPostId());
        Optional<Member> memberOptional = memberRepository.findById(request.getMemberId());

        Post post = postOptional.orElseThrow(IllegalStateException::new);
        Member member = memberOptional.orElseThrow(IllegalStateException::new);

        Comment comment = request.toEntity(member, post);
        Comment savedComment = commentRepository.save(comment);

        return new CreateCommentResponse(savedComment.getId());
    }

    public ListAllCommentResponse listAllComment(Long postId) {

        Optional<Post> postOptional = postRepository.findByIdWithComment(postId);

        Post post = postOptional.orElseThrow(NotExistPostException::new);
        List<ListAllCommentResponse.CommentDto> collect = post.getComments().stream()
                .map(comment -> ListAllCommentResponse.CommentDto.from(comment, comment.getMember()))
                .collect(toList());

        return ListAllCommentResponse.from(collect);
    }



}
