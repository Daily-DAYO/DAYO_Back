package com.seoultech.dayo.block;

import com.seoultech.dayo.member.Member;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
public class Block {

  @EmbeddedId
  private Block.Key key;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("memberId")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("targetId")
  private Member target;

  public Block(Member member, Member target) {
    this.member = member;
    this.target = target;
    key = new Block.Key(member.getId(), target.getId());
    member.getBlockList().add(this);
  }

  @Embeddable
  @AllArgsConstructor
  @Getter
  @EqualsAndHashCode
  public static class Key implements Serializable {

    private String memberId;
    private String targetId;

    protected Key() {
    }
  }

  protected Block() {
  }

}
