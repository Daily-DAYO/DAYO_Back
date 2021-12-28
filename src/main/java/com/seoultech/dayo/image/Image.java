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

    public Image(String originalFilename, String storeFileName) {
        this.originalFilename = originalFilename;
        this.storeFileName = storeFileName;
    }

    protected Image() {}
}
