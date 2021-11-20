package com.seoultech.dayo.post.controller.dto.request;


import com.seoultech.dayo.Image.Image;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.Privacy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreatePostRequest {

    private String contents;

    private String memberId;

    private Long folderId;

    private String privacy;

    private String category;

    private List<String> tags;

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
