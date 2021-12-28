package com.seoultech.dayo.image.repository;


import com.seoultech.dayo.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
