package com.seoultech.dayo.notice.controller;

import com.seoultech.dayo.notice.controller.dto.response.DetailNoticeResponse;
import com.seoultech.dayo.notice.controller.dto.response.NoticeListResponse;
import com.seoultech.dayo.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
public class NoticeController {

  private final NoticeService noticeService;

  //  @PostMapping
//  public ResponseEntity create() {
//
//  }
//
  @GetMapping
  public ResponseEntity<NoticeListResponse> listAllNotice(@RequestParam(value = "end") String end) {
    return ResponseEntity.ok()
        .body(noticeService.listAll(Long.valueOf(end)));
  }

  @PostMapping
  public void upload(@RequestParam("image") MultipartFile image,
      @RequestParam("title") String title,
      @RequestParam("content") String content) {
    noticeService.save(title, content);
  }

  @GetMapping("/{noticeId}")
  public ResponseEntity<DetailNoticeResponse> detailNotice(@PathVariable Long noticeId) {
    return ResponseEntity.ok()
        .body(noticeService.detailNotice(noticeId));
  }

}
