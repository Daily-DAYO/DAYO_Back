package com.seoultech.dayo.follow;

import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
public class Follow {

    @EmbeddedId
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private Member user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followerId")
    private Member follower;

    @Embeddable
    @AllArgsConstructor
    @Getter
    public static class Key implements Serializable {
        private String userId;
        private String followerId;

        protected Key() {}
    }

    protected Follow() {}

}
