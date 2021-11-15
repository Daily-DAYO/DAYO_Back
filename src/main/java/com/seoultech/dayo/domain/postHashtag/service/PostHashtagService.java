package com.seoultech.dayo.domain.postHashtag.service;

import com.seoultech.dayo.domain.postHashtag.PostHashtag;
import com.seoultech.dayo.domain.postHashtag.repository.PostHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostHashtagService {

    private final PostHashtagRepository postHashtagRepository;

    public List<PostHashtag> saveAll(List<PostHashtag> postHashtags) {
        return postHashtagRepository.saveAll(postHashtags);
    }

    public PostHashtag save(PostHashtag postHashtag) {
        return postHashtagRepository.save(postHashtag);
    }

}
