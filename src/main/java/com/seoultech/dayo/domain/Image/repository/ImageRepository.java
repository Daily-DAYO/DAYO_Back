package com.seoultech.dayo.domain.Image.repository;


import com.seoultech.dayo.domain.Image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
