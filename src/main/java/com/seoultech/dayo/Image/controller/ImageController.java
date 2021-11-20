package com.seoultech.dayo.Image.controller;

import com.seoultech.dayo.Image.service.ImageService;
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

    @GetMapping(value = "/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:"+imageService.getFullPath(filename));
    }

    @PostMapping
    public ResponseEntity uploadImage(MultipartHttpServletRequest servletRequest) throws IOException {
        List<MultipartFile> images = servletRequest.getFiles("files");
        imageService.storeFiles(images);
        return new ResponseEntity(HttpStatus.OK);
    }


}
