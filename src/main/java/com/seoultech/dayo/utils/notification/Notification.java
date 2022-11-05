package com.seoultech.dayo.utils.notification;

import com.seoultech.dayo.alarm.Topic;
import com.seoultech.dayo.alarm.service.AlarmService;
import com.seoultech.dayo.config.fcm.Note;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.utils.json.JsonData;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Notification {

  private final KafkaProducer kafkaProducer;
  private final AlarmService alarmService;

  public void sendCommentToPostOwner(Member sender, Post post) {
    if (isNotMyPost(sender, post)) {
      Map<String, String> data = makeCommentMessage(sender, post);
      Note note = Note.makeNote(data);

      alarmService.saveAlarmPost(note, post.getMember(), post, sender,
          Topic.COMMENT);

      if (canSendMessage(post)) {
        JsonData jsonData = new JsonData();
        String message = jsonData.make(data);
        kafkaProducer.sendMessage(Topic.COMMENT, message);
      }
    }
  }

  public void sendToFollower(Member member, Member follower) {
    Map<String, String> data = makeFollowMessage(member, follower);
    Note note = Note.makeNote(data);

    alarmService.saveAlarmFollow(note, follower, member, Topic.FOLLOW);

    if (canSendMessage(follower)) {
      JsonData jsonData = new JsonData();
      String message = jsonData.make(data);
      kafkaProducer.sendMessage(Topic.FOLLOW, message);
    }
  }

  public void sendHeartToPostOwner(Member member, Post post) {
    if (isNotMyPost(member, post)) {
      Map<String, String> data = makeLikeMessage(member, post);
      Note note = Note.makeNote(data);

      alarmService.saveAlarmPost(note, post.getMember(), post, member,
          Topic.HEART);

      if (canSendMessage(post)) {
        JsonData jsonData = new JsonData();
        String message = jsonData.make(data);
        kafkaProducer.sendMessage(Topic.HEART, message);
      }
    }
  }

  private Map<String, String> makeCommentMessage(Member member, Post post) {
    Map<String, String> data = new HashMap<>();
    data.put("subject", "DAYO");
    data.put("body", member.getNickname() + "님이 회원님의 게시글에 댓글을 남겼어요.");
    data.put("content", "님이 회원님의 게시글에 댓글을 남겼어요.");
    data.put("deviceToken", post.getMember().getDeviceToken());
    data.put("postId", post.getId().toString());
    data.put("topic", Topic.COMMENT.toString());
    data.put("image", post.getThumbnailImage().getStoreFileName());
    return data;
  }

  private Map<String, String> makeFollowMessage(Member member, Member follower) {
    Map<String, String> data = new HashMap<>();
    data.put("subject", "DAYO");
    data.put("body", member.getNickname() + "님이 회원님을 팔로우해요.");
    data.put("content", "님이 회원님을 팔로우해요.");
    data.put("deviceToken", follower.getDeviceToken());
    data.put("memberId", member.getId());
    data.put("topic", Topic.FOLLOW.toString());
    return data;
  }

  private Map<String, String> makeLikeMessage(Member member, Post post) {
    Map<String, String> data = new HashMap<>();
    data.put("subject", "DAYO");
    data.put("body", member.getNickname() + "님이 회원님의 게시글을 좋아해요.");
    data.put("content", "님이 회원님의 게시글을 좋아해요.");
    data.put("deviceToken", post.getMember().getDeviceToken());
    data.put("postId", post.getId().toString());
    data.put("topic", Topic.HEART.toString());
    data.put("image", post.getThumbnailImage().getStoreFileName());
    return data;
  }

  private boolean canSendMessage(Post post) {
    return post.getMember().getDeviceToken() != null && post.getMember().getOnReceiveAlarm();
  }

  private boolean isNotMyPost(Member member, Post post) {
    return !post.getMember().getId().equals(member.getId());
  }

  private boolean canSendMessage(Member member) {
    return member.getDeviceToken() != null && member.getOnReceiveAlarm();
  }


}
