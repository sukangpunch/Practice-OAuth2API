package me.foroauth2.common.exception.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.foroauth2.common.dto.CommonResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
public class MemberAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("[ClientAccessDeniedException] 접근 권한이 없습니다.");

        CommonResponse commonResponse = new CommonResponse(0, SecurityException.TOKEN_NOT_AUTHORIZED.getMessage(),null );

        response.setStatus(403);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //getWriter = PrintWriter를 가져옴,  PrintWriter는 문자열을 HTTP 응답의 본문에 쓸 수 있는 출력 스트림
        //objectMapper객체를 사용하여 Java 객체(commonResponse) 를 JSON 형식의 문자열로 변환합니다.
        //ObjectMapper는 Jackson 라이브러리에서 제공되며, Json과 Java 객체간의 변화를 지원한다.
        response.getWriter().write(objectMapper.writeValueAsString(commonResponse));
    }
}
