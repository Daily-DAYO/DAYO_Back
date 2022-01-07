package com.seoultech.dayo.post.controller.dto.request;


import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.Privacy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class CreatePostRequest {

    @NotBlank
    private String contents;

    @NotNull
    private Long folderId;

    @NotNull
    private String privacy;

    @NotNull
    private String category;

    private List<String> tags;

    @NotNull
    private List<MultipartFile> files;

    public Post toEntity(Folder folder, Member member, List<Image> images) {
        Post post = Post.builder()
                .member(member)
                .contents(contents)
                .thumbnailImage(images.get(0).getStoreFileName())
                .category(Category.valueOf(category))
                .privacy(Privacy.valueOf(privacy))
                .images(images)
                .build();
        post.addFolder(folder);
        return post;
    }

}
