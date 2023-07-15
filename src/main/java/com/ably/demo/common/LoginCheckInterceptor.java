package com.ably.demo.common;

import com.ably.demo.exception.ErrorCode;
import com.ably.demo.exception.InvalidAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.ably.demo.common.CommonConstant.LOGIN_MEMBER;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final String requestURI = request.getRequestURI();
        log.info("요청 API: {}", requestURI);

        final HttpSession session = request.getSession();
        if (session == null || session.getAttribute(LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");
            throw new InvalidAuthenticationException(ErrorCode.AUTHENTICATION_FAIL);
        }
        return true;
    }
}