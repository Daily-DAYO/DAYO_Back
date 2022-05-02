package com.seoultech.dayo.alarm.controller.dto.response;

import com.seoultech.dayo.alarm.controller.dto.AlarmDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListAllAlarmResponse {

  private int count;

  private List<AlarmDto> data;

  public static ListAllAlarmResponse from(List<AlarmDto> data) {
    return new ListAllAlarmResponse(data.size(), data);
  }

}
