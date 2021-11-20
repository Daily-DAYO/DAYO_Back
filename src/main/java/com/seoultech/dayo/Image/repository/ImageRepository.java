package com.seoultech.dayo.Image.repository;


import com.seoultech.dayo.Image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
