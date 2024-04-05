package me.foroauth2.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DecodingException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.foroauth2.common.dto.CommonResponse;
import me.foroauth2.common.exception.security.SecurityException;
import me.foroauth2.common.exception.security.MismatchTokenTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import io.jsonwebtoken.security.SignatureException;
@Slf4j
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (DecodingException e) {
            setErrorResponse(response, SecurityException.TOKEN_INVALID_FORMAT);
            log.info("[JwtExceptionHandlerFilter] error name = DecodingException");
        } catch (MalformedJwtException e) {
            setErrorResponse(response, SecurityException.TOKEN_UNAUTHENTICATED);
            log.info("[JwtExceptionHandlerFilter] error name = MalformedJwtException");
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, SecurityException.TOKEN_EXPIRED);
            log.info("[JwtExceptionHandlerFilter] error name = ExpiredJwtException");
        } catch (IllegalArgumentException e) {
            setErrorResponse(response, SecurityException.TOKEN_NOT_FOUND);
            log.info("[JwtExceptionHandlerFilter] error name = IllegalArgumentException");
        } catch (SignatureException e) {
            setErrorResponse(response, SecurityException.TOKEN_INVALID_FORMAT);
            log.info("[JwtExceptionHandlerFilter] error name = SignatureException");
        } catch (StringIndexOutOfBoundsException e) {
            setErrorResponse(response, SecurityException.TOKEN_INVALID_FORMAT);
            log.info("[JwtExceptionHandlerFilter] error name = StringIndexOutOfBoundsException");
        } catch (NullPointerException e) {
            setErrorResponse(response, SecurityException.TOKEN_NOT_FOUND);
            log.info("[JwtExceptionHandlerFilter] error name = NullPointerException");
        } catch (MismatchTokenTypeException e) {
            setErrorResponse(response, SecurityException.TOKEN_TYPE_INVALID);
        }
    }

    private void setErrorResponse(
            HttpServletResponse response,
            SecurityException exceptionMessage
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(exceptionMessage.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        CommonResponse commonResponse = new CommonResponse(exceptionMessage.getErrorCode(), exceptionMessage.getMessage(), null);
        try {
            response.getWriter().write(objectMapper.writeValueAsString(commonResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
