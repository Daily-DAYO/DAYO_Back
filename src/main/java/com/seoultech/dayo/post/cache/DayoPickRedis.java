package com.seoultech.dayo.post.cache;

import com.seoultech.dayo.post.controller.dto.DayoPick;
import com.seoultech.dayo.post.controller.dto.DayoPickDto;
import java.util.List;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("dayopick")
@AllArgsConstructor
@Getter
public class DayoPickRedis {

  @Id
  private Long id;

  private List<DayoPickDto> data;


}
