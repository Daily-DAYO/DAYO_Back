package com.seoultech.dayo.notice.service;

import static java.util.stream.Collectors.toList;

import com.seoultech.dayo.notice.Notice;
import com.seoultech.dayo.notice.NoticeRepository.NoticeRepository;
import com.seoultech.dayo.notice.controller.dto.response.DetailNoticeResponse;
import com.seoultech.dayo.notice.controller.dto.response.NoticeListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
public class NoticeService {

  private final NoticeRepository noticeRepository;

  public void save(String title, String content) {
    noticeRepository.save(
        new Notice(HtmlUtils.htmlUnescape(title), HtmlUtils.htmlUnescape(content), true));
  }

  public DetailNoticeResponse detailNotice(Long noticeId) {
    Notice notice = noticeRepository.findById(noticeId).orElseThrow();
    return DetailNoticeResponse.from(notice);
  }

  public NoticeListResponse listAll(Long end) {

    List<Notice> noticeList = noticeRepository.findAllWhereShowIsTrue();

    boolean last = false;
    int size = noticeList.size();
    if (size <= end + 10) {
      last = true;
    }

    List<NoticeDto> collect = noticeList.stream()
        .map(NoticeDto::from)
        .sorted((a1, a2) -> a2.getCreatedDate().compareTo(a1.getCreatedDate()))
        .skip(end)
        .limit(10)
        .collect(toList());

    return NoticeListResponse.from(collect, last);
  }

}
