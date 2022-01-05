package com.seoultech.dayo.member;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.seoultech.dayo.BaseTimeEntity;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.follow.Follow;
import lombok.Getter;

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

    @NotBlank
    private String name;

    @Pattern(regexp = "\\S+$")
    private String nickname;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String profileImg;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Folder> folders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<Follow> followers = new ArrayList<>();

    public void addFolder(Folder folder) {
        folders.add(folder);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Member(String name, String email, String profileImg, Authority authority) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.authority = authority;
        this.profileImg = profileImg;
    }

    protected Member() {}
}
