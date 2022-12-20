package com.seoultech.dayo.scheduler;

import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.controller.dto.DayoPickDto;
import com.seoultech.dayo.post.service.PostService;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

  private final PostService postService;
  private final RedisConnectionFactory redisConnectionFactory;

  @Scheduled(cron = "10 * * * * *", zone = "Asia/Seoul")
  public void dayoPick() {
//    RedisConnection connection = redisConnectionFactory.getConnection();
//
//    connection.del("dayoPick::1".getBytes(StandardCharsets.UTF_8));
//
//    log.info("스케줄러 작동");
  }


}
