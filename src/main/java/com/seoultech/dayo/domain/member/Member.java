package com.seoultech.dayo.domain.member;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
public class Member {

    @Id
    private String id;

    private String name;

    private String email;

    private String profileImg;

    public Member(String name, String email, String profileImg) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.profileImg = profileImg;
    }

    protected Member() {}
}
