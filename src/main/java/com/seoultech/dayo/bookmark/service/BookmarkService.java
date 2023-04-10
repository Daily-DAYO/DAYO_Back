package com.seoultech.dayo.bookmark.service;

import static java.util.stream.Collectors.toList;

import com.seoultech.dayo.block.service.BlockService;
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
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

  private final BookmarkRepository bookmarkRepository;
  private final BlockService blockService;

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
  public ListAllBookmarkPostResponse listAllBookmarkPost(Member member, Set<String> blockList,
      Long end) {

    List<Bookmark> bookmarks = bookmarkRepository.findAllByMember(member);
    Set<String> blockedMemberList = blockService.getBlockedMemberList(member);

    boolean last = false;
    if (bookmarks.size() <= end + 10) {
      last = true;
    }

    List<BookmarkPostDto> collect = bookmarks.stream()
        .filter(bookmark -> !blockList.contains(bookmark.getPost().getMember().getId()))
        .filter(bookmark -> !blockedMemberList.contains(bookmark.getPost().getMember().getId()))
        .skip(end)
        .limit(10)
        .map(BookmarkPostDto::from)
        .collect(toList());

    return ListAllBookmarkPostResponse.from(collect, last);
  }

  @Transactional(readOnly = true)
  public ListAllMyBookmarkPostResponse listAllMyBookmarkPost(Member member, Set<String> blockList,
      Long end) {

    List<Bookmark> bookmarks = bookmarkRepository.findAllByMember(member);
    Set<String> blockedMemberList = blockService.getBlockedMemberList(member);

    boolean last = false;
    if (bookmarks.size() <= end + 10) {
      last = true;
    }

    List<MyBookmarkPostDto> collect = bookmarks.stream()
        .filter(bookmark -> !blockList.contains(bookmark.getPost().getMember().getId()))
        .filter(bookmark -> !blockedMemberList.contains(bookmark.getPost().getMember().getId()))
        .skip(end)
        .limit(10)
        .map(MyBookmarkPostDto::from)
        .collect(toList());

    return ListAllMyBookmarkPostResponse.from(collect, last);
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
