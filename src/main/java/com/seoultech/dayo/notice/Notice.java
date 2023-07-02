package com.seoultech.dayo.notice;

import com.seoultech.dayo.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Notice extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Column(length = 50000)
  private String contents;

  private Boolean showYn;

  public Notice(String title, String contents, Boolean showYn) {
    this.title = title;
    this.contents = contents;
    this.showYn = showYn;
  }
}
