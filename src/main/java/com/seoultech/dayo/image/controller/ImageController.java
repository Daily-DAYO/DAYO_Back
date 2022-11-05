package com.seoultech.dayo.image.controller;

import com.seoultech.dayo.image.Category;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.service.ImageService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  @GetMapping(value = "/{filename}", produces = MediaType.IMAGE_PNG_VALUE)
  public Resource downloadImage(@PathVariable String filename)
      throws MalformedURLException {
    return new UrlResource("file:" + imageService.getFullPath(filename));
  }

//  @PostMapping
//  public ResponseEntity upload(MultipartHttpServletRequest servletRequest) throws IOException {
//    MultipartFile image = servletRequest.getFile("file");
//    Image savedImage = imageService.storeFile(image, Category.PROFILE);
//    imageService.resizeFile(savedImage.getStoreFileName(), 17, 17);
//    imageService.resizeFile(savedImage.getStoreFileName(), 37, 37);
//    imageService.resizeFile(savedImage.getStoreFileName(), 45, 45);
//    return new ResponseEntity(HttpStatus.OK);
//  }

  @PostMapping("/profile")
  public ResponseEntity uploadProfileImage(MultipartHttpServletRequest servletRequest)
      throws IOException {
    MultipartFile image = servletRequest.getFile("file");
    Image savedImage = imageService.storeFile(image, Category.PROFILE);
    imageService.resizeFile(savedImage.getStoreFileName(), 17, 17);
    imageService.resizeFile(savedImage.getStoreFileName(), 37, 37);
    imageService.resizeFile(savedImage.getStoreFileName(), 45, 45);
    return new ResponseEntity(HttpStatus.OK);
  }

  @PostMapping("/folder")
  public ResponseEntity uploadFolderImage(MultipartHttpServletRequest servletRequest)
      throws IOException {
    MultipartFile image = servletRequest.getFile("file");
    imageService.storeFile(image, Category.FOLDER);
    return new ResponseEntity(HttpStatus.OK);
  }

}
