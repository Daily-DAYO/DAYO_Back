package com.seoultech.dayo.heart.repository;

import com.seoultech.dayo.heart.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Heart.Key> {
}
