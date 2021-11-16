package com.seoultech.dayo.domain.folder;

import com.seoultech.dayo.domain.Image.Image;
import com.seoultech.dayo.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    private String subheading;

    @OneToOne
    private Image thumbnailImage;

    @OneToMany(mappedBy = "folder")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Folder(@NonNull String name, String subheading, Image thumbnailImage) {
        this.name = name;
        this.subheading = subheading;
        this.thumbnailImage = thumbnailImage;
    }

    protected Folder() {}
}
