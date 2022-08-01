package com.seoultech.dayo.post.repository;

import static com.seoultech.dayo.image.Category.PROFILE;
import static org.assertj.core.api.Assertions.assertThat;

import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.cache.DayoPickRedis;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.controller.dto.DayoPick;
import com.seoultech.dayo.post.controller.dto.DayoPickDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;

@DataRedisTest
class DayoPickRepositoryTests {

  @Autowired
  DayoPickRepository dayoPickRepository;

  @Test
  void test1() {
    Image image = new Image("test.jpg", "test.jpg", PROFILE);
    Member member = new Member("조재영", "jdyj@naver.com", image);
    member.setNickname("재영");
    Post post1 = new Post(member, "테스트1", image, Category.SCHEDULER, null);
    Post post2 = new Post(member, "테스트1", image, Category.SCHEDULER, null);
    Post post3 = new Post(member, "테스트1", image, Category.SCHEDULER, null);
    Post post4 = new Post(member, "테스트1", image, Category.SCHEDULER, null);
    Post post5 = new Post(member, "테스트1", image, Category.SCHEDULER, null);
    Post post6 = new Post(member, "테스트1", image, Category.SCHEDULER, null);
    Post post7 = new Post(member, "테스트1", image, Category.SCHEDULER, null);
    Post post8 = new Post(member, "테스트1", image, Category.SCHEDULER, null);
    Post post9 = new Post(member, "테스트1", image, Category.SCHEDULER, null);

    List<Post> postList = new ArrayList<>();

    postList.add(post1);
    postList.add(post2);
    postList.add(post3);
    postList.add(post4);
    postList.add(post5);
    postList.add(post6);
    postList.add(post7);
    postList.add(post8);
    postList.add(post9);

    List<DayoPickDto> collect = postList.stream()
        .map(DayoPickDto::from)
        .collect(Collectors.toList());

    DayoPickRedis dayoPickRedis = new DayoPickRedis(1L, collect);

    DayoPickRedis savedDayoPick = dayoPickRepository.save(dayoPickRedis);
    assertThat(savedDayoPick.getId()).isEqualTo(1);

    DayoPickRedis dayoPickRedis1 = dayoPickRepository.findById(savedDayoPick.getId())
        .orElseThrow(IllegalStateException::new);

    assertThat(dayoPickRedis1.getData().size()).isEqualTo(9);

  }


}