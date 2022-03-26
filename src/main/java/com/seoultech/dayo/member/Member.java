package com.seoultech.dayo.member;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.seoultech.dayo.BaseTimeEntity;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.post.Post;
import lombok.Getter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Member extends BaseTimeEntity {

  @Id
  private String id;

  private String name;

  @Pattern(regexp = "\\S+$")
  private String nickname;

  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @OneToOne
  private Image profileImg;

  @OneToMany(mappedBy = "member")
  private List<Folder> folders = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<Post> posts = new ArrayList<>();

  private String deviceToken;

  @Formula("(select count(1) from follow f where f.member_id = id)")
  private int followingCount;

  @Formula("(select count(1) from follow f where f.follower_id = id)")
  private int followerCount;

  @Formula("(select count(1) from post p where p.member_id = id)")
  private int postCount;

//    @OneToMany(mappedBy = "member")
//    private List<Follow> followings = new ArrayList<>();
//
//    @OneToMany(mappedBy = "follower")
//    private List<Follow> followers = new ArrayList<>();

  public void addFolder(Folder folder) {
    folders.add(folder);
    folder.setMember(this);
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public void setProfileImg(Image profileImg) {
    this.profileImg = profileImg;
  }

  public void setDeviceToken(String deviceToken) {
    this.deviceToken = deviceToken;
  }

  public Member(String name, String email, Image profileImg) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.email = email;
    this.profileImg = profileImg;
  }

  public Member(String nickname, String email, String password,
      Image profileImg) {
    this.id = UUID.randomUUID().toString();
    this.nickname = nickname;
    this.email = email;
    this.password = password;
    this.profileImg = profileImg;
  }

  protected Member() {
  }
}
