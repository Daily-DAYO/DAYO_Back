package com.seoultech.dayo.bookmark.repository;

import com.seoultech.dayo.bookmark.Bookmark;
import com.seoultech.dayo.bookmark.Bookmark.Key;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import java.util.List;
import java.util.Optional;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepository extends JpaRepository<Bookmark, Bookmark.Key> {

  List<Bookmark> findAllByMember(Member member);

  boolean existsBookmarkByKey(Bookmark.Key key);

  void deleteAllByMember(Member member);

  Optional<Bookmark> findBookmarkByMemberAndPost(Member member, Post post);

  void deleteAllByPost(Post post);

  @Modifying
  @Query("delete from Bookmark b where b.key in :keyList")
  void deleteAllByKeyList(@Param("keyList") List<Bookmark.Key> keyList);
}
