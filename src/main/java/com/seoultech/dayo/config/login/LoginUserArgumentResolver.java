package com.seoultech.dayo.config.login;

import com.seoultech.dayo.exception.NotExistMemberException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

  private final HttpServletRequest httpServletRequest;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {

    boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
    boolean isStringClass = String.class.equals(parameter.getParameterType());

    return isLoginUserAnnotation && isStringClass;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    Object memberId = httpServletRequest.getAttribute("memberId");
    if (memberId == null) {
      throw new NotExistMemberException();
    }
    return memberId.toString();
  }
}
