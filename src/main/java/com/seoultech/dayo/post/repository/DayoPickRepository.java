package com.seoultech.dayo.post.repository;

import com.seoultech.dayo.post.cache.DayoPickRedis;
import org.springframework.data.repository.CrudRepository;

public interface DayoPickRepository extends CrudRepository<DayoPickRedis, Long> {
}
