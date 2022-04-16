package com.seoultech.dayo.comment.service;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.seoultech.dayo.alarm.repository.AlarmService;
import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.comment.controller.dto.response.ListAllCommentResponse;
import com.seoultech.dayo.comment.repository.CommentRepository;
import com.seoultech.dayo.comment.controller.dto.request.CreateCommentRequest;
import com.seoultech.dayo.comment.controller.dto.response.CreateCommentResponse;
import com.seoultech.dayo.config.fcm.FcmMessageService;
import com.seoultech.dayo.config.fcm.Note;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.service.PostService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

  private final CommentRepository commentRepository;
  private final MemberService memberService;
  private final PostService postService;
  private final FcmMessageService messageService;
  private final AlarmService alarmService;

  public CreateCommentResponse createComment(String memberId, CreateCommentRequest request)
      throws FirebaseMessagingException {

    Post post = postService.findPostById(request.getPostId());
    Member member = memberService.findMemberById(memberId);

    Comment comment = request.toEntity(member);
    Comment savedComment = commentRepository.save(comment);
    savedComment.addPost(post);

    // TODO: refactoring
    if (post.getMember().getDeviceToken() != null) {
      Map<String, String> data = new HashMap<>();
      data.put("body", member.getNickname() + "님이 회원님의 게시글에 댓글을 남겼어요.");
      Note note = new Note(
          "DAYO",
          null,
          data,
          null
      );

      alarmService.save(note, post.getMember());
      messageService.sendMessage(note, post.getMember().getDeviceToken());
    }

    return new CreateCommentResponse(savedComment.getId());
  }

  @Transactional(readOnly = true)
  public ListAllCommentResponse listAllComment(Long postId) {

    Post post = postService.findPostById(postId);
    List<ListAllCommentResponse.CommentDto> collect = post.getComments().stream()
        .map(ListAllCommentResponse.CommentDto::from)
        .collect(toList());

    return ListAllCommentResponse.from(collect);
  }

  public void deleteComment(Long commentId) {
    commentRepository.deleteById(commentId);
  }

}
