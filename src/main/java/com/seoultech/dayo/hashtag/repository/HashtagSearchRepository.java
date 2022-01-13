package com.seoultech.dayo.hashtag.repository;

import com.seoultech.dayo.hashtag.Hashtag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HashtagSearchRepository extends ElasticsearchRepository<Hashtag, Long> {

    @Query("{\"query\": {\"match_phrase_prefix\":{\"word\": \"?0\"}}}")
    List<Hashtag> findHashtagsByTag(String tag);

}
