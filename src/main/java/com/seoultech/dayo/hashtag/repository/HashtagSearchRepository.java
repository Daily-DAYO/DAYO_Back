package com.seoultech.dayo.hashtag.repository;

import com.seoultech.dayo.hashtag.Hashtag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface HashtagSearchRepository extends ElasticsearchRepository<Hashtag, Long> {

}
