package com.seoultech.dayo.bookmark.repository;

import com.seoultech.dayo.bookmark.Bookmark;
import com.seoultech.dayo.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Bookmark.Key> {

  List<Bookmark> findAllByMember(Member member);

  boolean existsBookmarkByKey(Bookmark.Key key);

  void deleteAllByMember(Member member);

}
