package com.seoultech.dayo.domain.heart;

import com.seoultech.dayo.domain.member.Member;
import com.seoultech.dayo.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Heart.Key> {
}
