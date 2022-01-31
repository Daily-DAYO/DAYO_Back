package com.seoultech.dayo.post;

public enum Category {

  SCHEDULER,
  STUDY_PLANNER,
  POCKET_BOOK,
  SIX_DIARY,
  GOOD_NOTE,
  ETC;

  public static boolean find(String value) {

    for (Category category : values()) {
      if (category.name().equals(value)) {
        return true;
      }
    }
    return false;
  }

}
