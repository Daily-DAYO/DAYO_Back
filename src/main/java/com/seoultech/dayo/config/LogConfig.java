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

  @Around("within(com.seoultech.dayo..*)")
  public Object logging(ProceedingJoinPoint pjp) throws Throwable {

    String params = getRequestParams();

    long startTime = System.currentTimeMillis();

    log.info("Request : {}({}) = {}", pjp.getSignature().getDeclaringTypeName(),
        pjp.getSignature().getName(), params);

    Object result = pjp.proceed();

    long endTime = System.currentTimeMillis();

    log.info("Response : {}({}) = {} ({}ms)", pjp.getSignature().getDeclaringTypeName(),
        pjp.getSignature().getName(), result, endTime - startTime);

    return result;
  }

  private String getRequestParams() {
    String params = null;
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

    if (requestAttributes != null) {
      HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

      Map<String, String[]> paramMap = request.getParameterMap();
      if (!paramMap.isEmpty()) {
        params = "[" + paramMapToString(paramMap) + "]";
      }
    }

    return params;
  }

  private String paramMapToString(Map<String, String[]> paramMap) {
    return paramMap.entrySet().stream()
        .map(entry -> String.format("%s -> (%s)", entry.getKey(),
            Joiner.on(",").join(entry.getValue())))
        .collect(Collectors.joining(", "));
  }

}
