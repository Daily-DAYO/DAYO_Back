package com.seoultech.dayo.alarm.service;

import com.seoultech.dayo.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}
