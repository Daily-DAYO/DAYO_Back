package com.seoultech.dayo.follow.repository;

import com.seoultech.dayo.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Follow.Key> {
}
