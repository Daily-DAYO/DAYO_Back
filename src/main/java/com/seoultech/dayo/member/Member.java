package com.seoultech.dayo.member;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.seoultech.dayo.folder.Folder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Member {

    @Id
    private String id;

    @NotBlank
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String profileImg;

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Folder> folders = new ArrayList<>();

    public void addFolder(Folder folder) {
        folders.add(folder);
    }

    public Member(String name, String email, String profileImg) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.profileImg = profileImg;
    }

    protected Member() {}
}
