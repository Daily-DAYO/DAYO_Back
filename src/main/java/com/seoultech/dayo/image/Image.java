package com.seoultech.dayo.image;

import com.seoultech.dayo.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;


@Entity
@Getter
public class Image extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String originalFilename;

  private String storeFileName;

  @Enumerated(EnumType.STRING)
  private Category category;

  public Image(String originalFilename, String storeFileName, Category category) {
    this.originalFilename = originalFilename;
    this.storeFileName = storeFileName;
    this.category = category;
  }

  protected Image() {
  }
}
