package com.seoultech.dayo.bookmark.service;

import com.seoultech.dayo.bookmark.Bookmark;
import com.seoultech.dayo.bookmark.controller.dto.request.DeleteBookmarkRequest;
import com.seoultech.dayo.bookmark.repository.BookmarkRepository;
import com.seoultech.dayo.exception.NotExistBookmarkException;
import com.seoultech.dayo.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkServiceV2 {

    private final BookmarkRepository bookmarkRepository;

    public void deleteBookmark(Member member, DeleteBookmarkRequest request) {
        List<Bookmark.Key> keyList = new ArrayList<>();
        request.getPostIdList().forEach(postId -> {
            Bookmark.Key key = new Bookmark.Key(member.getId(), postId);
            if (bookmarkRepository.existsBookmarkByKey(key)) {
                keyList.add(key);
            } else {
                throw new NotExistBookmarkException();
            }
        });
        bookmarkRepository.deleteAllByKeyList(keyList);
    }

}
