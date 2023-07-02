package com.seoultech.dayo.notice.NoticeRepository;

import com.seoultech.dayo.notice.Notice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

  @Query("select n from Notice n where n.isShow = true")
  List<Notice> findAllWhereShowIsTrue();

}
