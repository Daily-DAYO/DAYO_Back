package com.seoultech.dayo.post.repository;

import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.controller.dto.DayoPick;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayoPickRepository extends JpaRepository<DayoPick, Long> {

  List<DayoPick> findAllByCategory(Category category);

}
