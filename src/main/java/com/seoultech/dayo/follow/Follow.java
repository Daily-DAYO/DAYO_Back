package com.seoultech.dayo.follow;

import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import javax.swing.text.StyledEditorKit;
import java.io.Serializable;

@Entity
@Getter
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "i_member", columnList = "member_id"),
        @Index(name = "i_follower", columnList = "follower_id")
})
public class Follow {

    @EmbeddedId
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followerId")
    private Member follower;

    private Boolean isAccept;

    public void setIsAccept(Boolean isAccept) {
        this.isAccept = isAccept;
    }

    @Embeddable
    @AllArgsConstructor
    @Getter
    public static class Key implements Serializable {
        private String memberId;
        private String followerId;

        protected Key() {}
    }

    protected Follow() {}

}
