package com.seoultech.dayo.bookmark.service;

import static java.util.stream.Collectors.toList;

import com.seoultech.dayo.bookmark.Bookmark;
import com.seoultech.dayo.bookmark.controller.dto.BookmarkPostDto;
import com.seoultech.dayo.bookmark.controller.dto.MyBookmarkPostDto;
import com.seoultech.dayo.bookmark.controller.dto.request.CreateBookmarkRequest;
import com.seoultech.dayo.bookmark.controller.dto.response.CreateBookmarkResponse;
import com.seoultech.dayo.bookmark.controller.dto.response.ListAllBookmarkPostResponse;
import com.seoultech.dayo.bookmark.controller.dto.response.ListAllMyBookmarkPostResponse;
import com.seoultech.dayo.bookmark.repository.BookmarkRepository;
import com.seoultech.dayo.exception.NotExistBookmarkException;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

  private final BookmarkRepository bookmarkRepository;

  public CreateBookmarkResponse createBookmark(Member member, Post post,
      CreateBookmarkRequest request) {

    Bookmark Bookmark = request.toEntity(member, post);
    Bookmark savedBookmark = bookmarkRepository.save(Bookmark);

    return CreateBookmarkResponse.from(savedBookmark);
  }

  public void deleteBookmark(Member member, Post post) {

    Bookmark bookmark = bookmarkRepository.findBookmarkByMemberAndPost(member, post)
        .orElseThrow(NotExistBookmarkException::new);

    post.deleteBookmark(bookmark);
    bookmarkRepository.delete(bookmark);
  }

  @Transactional(readOnly = true)
  public ListAllBookmarkPostResponse listAllBookmarkPost(Member member) {

    List<Bookmark> Bookmarks = bookmarkRepository.findAllByMember(member);
    List<BookmarkPostDto> collect = Bookmarks.stream()
        .map(BookmarkPostDto::from)
        .collect(toList());

    return ListAllBookmarkPostResponse.from(collect);
  }

  @Transactional(readOnly = true)
  public ListAllMyBookmarkPostResponse listAllMyBookmarkPost(Member member) {

    List<Bookmark> Bookmarks = bookmarkRepository.findAllByMember(member);
    List<MyBookmarkPostDto> collect = Bookmarks.stream()
        .map(MyBookmarkPostDto::from)
        .collect(toList());

    return ListAllMyBookmarkPostResponse.from(collect);
  }

  public void deleteAllByMember(Member member) {
    bookmarkRepository.deleteAllByMember(member);
  }

  public List<Bookmark> listBookmarksByMember(Member member) {
    return bookmarkRepository.findAllByMember(member);
  }

  public boolean isBookmark(String memberId, Long postId) {
    return bookmarkRepository.existsBookmarkByKey(new Bookmark.Key(memberId, postId));
  }

}
