package com.seoultech.dayo.mention;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.exception.NotExistMemberException;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import com.seoultech.dayo.mention.dto.MentionRequest;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.utils.notification.Notification;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MentionService {

  private final MentionRepository mentionRepository;
  private final MemberRepository memberRepository;
  private final Notification notification;


  public void saveMention(Member member, Comment comment, Post post, List<MentionRequest> request) {

    List<Mention> mentionList = request.stream()
        .map(req -> {
          Member receiver = memberRepository.findMemberByNickname(req.getNickname())
              .orElseThrow(NotExistMemberException::new);
          notification.sendMentionToPostOwner(member, post, receiver);
          Mention mention = new Mention(comment, receiver, req.getNickname(), req.getOrder());
          mention.addMention(comment);
          return mention;
        })
        .collect(Collectors.toList());
    mentionRepository.saveAll(mentionList);
  }
}
