package com.seoultech.dayo.block.repository;

import com.seoultech.dayo.block.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Block.Key> {

}
