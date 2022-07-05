package com.seoultech.dayo.config;

import com.google.common.base.Joiner;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class LogConfig {

  @Around("within(com.seoultech.dayo..controller..*)")
  public Object logging(ProceedingJoinPoint pjp) throws Throwable {

    String params = "";
    HttpServletRequest request = // 5
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

    Map<String, String[]> paramMap = request.getParameterMap();
    if (!paramMap.isEmpty()) {
      params = "[" + paramMapToString(paramMap) + "]";
    }

    long startTime = System.currentTimeMillis();

    log.info("Request : {}({})", request.getRequestURI(),
        params);

    Object result = pjp.proceed();

    long endTime = System.currentTimeMillis();

    log.info("Response : {}({}) = {} ({}ms)", request.getRequestURI(),
        params, result, endTime - startTime);

    return result;
  }

  private String paramMapToString(Map<String, String[]> paramMap) {
    return paramMap.entrySet().stream()
        .map(entry -> String.format("%s -> (%s)", entry.getKey(),
            Joiner.on(",").join(entry.getValue())))
        .collect(Collectors.joining(", "));
  }

}
