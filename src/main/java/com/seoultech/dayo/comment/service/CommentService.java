package com.seoultech.dayo.comment.service;


import com.seoultech.dayo.alarm.service.AlarmService;
import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.comment.controller.dto.response.ListAllCommentResponse;
import com.seoultech.dayo.comment.repository.CommentRepository;
import com.seoultech.dayo.comment.controller.dto.request.CreateCommentRequest;
import com.seoultech.dayo.comment.controller.dto.response.CreateCommentResponse;
import com.seoultech.dayo.exception.NotExistCommentException;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.service.PostService;
import com.seoultech.dayo.utils.notification.Notification;
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
  private final Notification notification;

  public CreateCommentResponse createComment(Member member, CreateCommentRequest request) {

    Post post = postService.findPostById(request.getPostId());

    Comment comment = request.toEntity(member);
    Comment savedComment = commentRepository.save(comment);
    savedComment.addPost(post);
    notification.sendCommentToPostOwner(member, post);

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

  public void deleteComment(Member member, Long commentId) {

    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(NotExistCommentException::new);

    Post post = comment.getPost();

    comment.delete();
    commentRepository.delete(comment);
    alarmService.deleteComment(member, post);
  }

  public void deleteAllByMember(Member member) {
    commentRepository.deleteAllByMember(member);
  }


}
