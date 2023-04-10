package com.seoultech.dayo.block.repository;

import com.seoultech.dayo.block.Block;
import com.seoultech.dayo.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Block.Key> {

  List<Block> findBlocksByMember(Member member);

  List<Block> findBlocksByTarget(Member member);

}
