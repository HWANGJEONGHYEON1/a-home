package com.homework.demo.user;

import com.homework.demo.config.SecurityUtil;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OwnSecurityService {

    public boolean isOwner(String email) {
        // 현재 인증된 사용자 정보 가져오기
        String currentUsername =
                SecurityUtil.getCurrentUsername()
                        .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("권한이 없습니다."));

        // 현재 인증된 사용자와 요청된 사용자의 계정 비교
        return email.equals(currentUsername);
    }
}
