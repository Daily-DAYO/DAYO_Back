package com.seoultech.dayo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class StringParseTest {

  @Test
  void parseTest() {

    String input = "'SIX_DIARY'";
    String substring = input.substring(1, input.length() - 1);

    assertThat(substring).isEqualTo("SIX_DIARY");


  }

}
