package com.seoultech.dayo.block.service;

import com.seoultech.dayo.block.Block;
import com.seoultech.dayo.block.Block.Key;
import com.seoultech.dayo.block.repository.BlockRepository;
import com.seoultech.dayo.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BlockService {

  private final BlockRepository blockRepository;

  public void blockMember(Member member, Member target) {
    blockRepository.save(new Block(member, target));
  }

  public void cancel(Member member, Member target) {
    Key key = new Key(member.getId(), target.getId());
    if (blockRepository.existsById(key)) {
      blockRepository.deleteById(key);
    }
  }

}
