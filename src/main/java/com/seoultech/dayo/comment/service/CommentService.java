package com.seoultech.dayo.comment.service;


import com.seoultech.dayo.alarm.Topic;
import com.seoultech.dayo.alarm.service.AlarmService;
import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.comment.controller.dto.response.ListAllCommentResponse;
import com.seoultech.dayo.comment.repository.CommentRepository;
import com.seoultech.dayo.comment.controller.dto.request.CreateCommentRequest;
import com.seoultech.dayo.comment.controller.dto.response.CreateCommentResponse;
import com.seoultech.dayo.config.fcm.Note;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.service.PostService;
import com.seoultech.dayo.utils.KafkaProducer;
import com.seoultech.dayo.utils.json.JsonData;
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
  private final PostService postService;
  private final AlarmService alarmService;
  private final KafkaProducer kafkaProducer;

  public CreateCommentResponse createComment(Member member, CreateCommentRequest request) {

    Post post = postService.findPostById(request.getPostId());

    Comment comment = request.toEntity(member);
    Comment savedComment = commentRepository.save(comment);
    savedComment.addPost(post);
    sendAlarmToPostOwner(member, post);

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

  public void deleteAllByMember(Member member) {
    commentRepository.deleteAllByMember(member);
  }

  private void sendAlarmToPostOwner(Member member, Post post) {
    if (isNotMyPost(member, post)) {
      Map<String, String> data = makeMessage(member, post);
      Note note = Note.makeNote(data);

      alarmService.saveAlarmPost(note, post.getMember(), post.getId(), member,
          Topic.COMMENT);

      if (canSendMessage(post)) {
        JsonData jsonData = new JsonData();
        String message = jsonData.make(data);
        kafkaProducer.sendMessage(Topic.COMMENT, message);
      }
    }
  }

  private Map<String, String> makeMessage(Member member, Post post) {
    Map<String, String> data = new HashMap<>();
    data.put("subject", "DAYO");
    data.put("body", member.getNickname() + "님이 회원님의 게시글에 댓글을 남겼어요.");
    data.put("content", "님이 회원님의 게시글에 댓글을 남겼어요.");
    data.put("deviceToken", post.getMember().getDeviceToken());
    data.put("postId", post.getId().toString());
    data.put("topic", Topic.COMMENT.toString());
    return data;
  }

  private boolean canSendMessage(Post post) {
    return post.getMember().getDeviceToken() != null && post.getMember().getOnReceiveAlarm();
  }

  private boolean isNotMyPost(Member member, Post post) {
    return !post.getMember().getId().equals(member.getId());
  }

}
