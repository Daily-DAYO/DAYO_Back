package com.seoultech.dayo.heart.service;

import com.seoultech.dayo.exception.NotExistMemberException;
import com.seoultech.dayo.exception.NotExistPostException;
import com.seoultech.dayo.heart.Heart;
import com.seoultech.dayo.heart.controller.dto.HeartPostDto;
import com.seoultech.dayo.heart.controller.dto.MyHeartPostDto;
import com.seoultech.dayo.heart.controller.dto.request.CreateHeartRequest;
import com.seoultech.dayo.heart.controller.dto.response.CreateHeartResponse;
import com.seoultech.dayo.heart.controller.dto.response.ListAllHeartPostResponse;
import com.seoultech.dayo.heart.controller.dto.response.ListAllMyHeartPostResponse;
import com.seoultech.dayo.heart.repository.HeartRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.repository.PostRepository;
import com.seoultech.dayo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class HeartService {

    private final HeartRepository heartRepository;
    private final MemberService memberService;
    private final PostRepository postRepository;

    public CreateHeartResponse createHeart(String memberId, CreateHeartRequest request) {

        Member member = memberService.findMemberById(memberId);
        Post post = findPostById(request.getPostId());

        Heart heart = request.toEntity(member, post);
        Heart savedHeart = heartRepository.save(heart);

        return CreateHeartResponse.from(savedHeart);
    }

    public void deleteHeart(String memberId, Long postId) {
        heartRepository.deleteById(new Heart.Key(memberId, postId));
    }

    @Transactional(readOnly = true)
    public ListAllHeartPostResponse listAllHeartPost(String memberId) {

        Member member = memberService.findMemberById(memberId);

        List<Heart> hearts = heartRepository.findAllByMember(member);
        List<HeartPostDto> collect = hearts.stream()
                .map(HeartPostDto::from)
                .collect(toList());

        return ListAllHeartPostResponse.from(collect);
    }

    @Transactional(readOnly = true)
    public ListAllMyHeartPostResponse listAllMyHeartPost(String memberId) {

        Member member = memberService.findMemberById(memberId);

        List<Heart> hearts = heartRepository.findAllByMember(member);
        List<MyHeartPostDto> collect = hearts.stream()
                .map(MyHeartPostDto::from)
                .collect(toList());

        return ListAllMyHeartPostResponse.from(collect);
    }

    public boolean isHeart(String memberId, Long postId) {
        return heartRepository.existsHeartByKey(new Heart.Key(memberId, postId));
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(NotExistPostException::new);
    }

}
