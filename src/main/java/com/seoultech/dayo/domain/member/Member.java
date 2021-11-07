package com.seoultech.dayo.domain.member;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Member {

    @Id
    private String id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String profileImg;

    public Member(String name, String email, String profileImg) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.profileImg = profileImg;
    }

    protected Member() {}
}
