package com.seoultech.dayo.domain.folder;

import com.seoultech.dayo.domain.post.Post;
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

    @OneToMany
    @JoinColumn(name = "folder_id")
    private List<Post> post = new ArrayList<>();

}
