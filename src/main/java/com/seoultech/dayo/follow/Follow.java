package com.seoultech.dayo.follow;

import com.seoultech.dayo.member.Member;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
  @EqualsAndHashCode
  public static class Key implements Serializable {

    private String memberId;
    private String followerId;

    protected Key() {
    }
  }

  protected Follow() {
  }

}
