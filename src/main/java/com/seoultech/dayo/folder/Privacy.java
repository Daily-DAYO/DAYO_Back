package com.seoultech.dayo.folder;

public enum Privacy {

  ALL,
  FOLLOWING,
  ONLY_ME;

  public static boolean find(String value) {

    for (Privacy privacy : values()) {
      if (privacy.name().equals(value)) {
        return true;
      }
    }
    return false;
  }

}
