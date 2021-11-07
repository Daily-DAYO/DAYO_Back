package com.seoultech.dayo.domain.comment;


import com.seoultech.dayo.domain.comment.controller.request.CreateCommentRequest;
import com.seoultech.dayo.domain.comment.controller.response.CreateCommentResponse;
import com.seoultech.dayo.domain.member.Member;
import com.seoultech.dayo.domain.member.MemberRepository;
import com.seoultech.dayo.domain.post.Post;
import com.seoultech.dayo.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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



}
