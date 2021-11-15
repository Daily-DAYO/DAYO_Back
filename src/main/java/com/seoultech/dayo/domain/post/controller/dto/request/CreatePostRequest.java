package com.seoultech.dayo.domain.post.controller.dto.request;


import com.seoultech.dayo.domain.Image.Image;
import com.seoultech.dayo.domain.folder.Folder;
import com.seoultech.dayo.domain.member.Member;
import com.seoultech.dayo.domain.post.Category;
import com.seoultech.dayo.domain.post.Post;
import com.seoultech.dayo.domain.post.Privacy;
import com.seoultech.dayo.domain.postHashtag.PostHashtag;
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
