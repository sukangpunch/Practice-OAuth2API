package me.foroauth2.common.exception.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import me.foroauth2.common.dto.CommonResponse;
import me.foroauth2.common.exception.customs.CustomException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    //@Valid 유효성검사 실패 시
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        log.error(errors.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse<>(0,"올바르지 않은 입력값입니다", errors));
    }

    // 역직렬화 과정에서 dto필드의 타입이 맞지 않아 발생하는 예외, PathVariable이나 RequestParam으로 받을 때 타입이 맞지않은 경우와 다른 예외를 던지기 떄문에 분리
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessage = ex.getMessage();
        if(errorMessage.contains("java.lang.Long"))
            errorMessage = ": Long타입 예외";
        else if(errorMessage.contains("Enum"))
            errorMessage = ": ENUM타입 예외";
        else
            errorMessage = ": 기타 타입예외";

        String message = "올바른 요청타입이 아닙니다.";
        log.error(message+errorMessage+" - "+ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse(0,message+errorMessage,null));
    }

    //requestParam값이 존재하지 않는 경우
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String msg = "입력 파라미터 공백";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse(0, ex.getParameterName()+":"+msg,null));
    }

    // custom예외
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse> handleCustomException(CustomException ex){
        log.error("예외가 발생했습니다.:"+ex.getExceptionCode().getMessage());
        return ResponseEntity.status(ex.getExceptionCode().getStatus()).body(new CommonResponse(0, ex.getExceptionCode().getMessage(),null));
    }

    // requestParam으로 입력받은 값의 유효성검사 실패
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse> handleContranintViolation(ConstraintViolationException ex){
        String msg = ex.getMessage();
        int index = msg.indexOf(":");

        if (index >= 0) {
            msg = msg.substring(index + 1).trim();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse(0,msg,null));
    }

    // @PathVariable로 입력받은 값의 타입이 올바르지 않을 때
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonResponse> handleMethodArgTypeException(MethodArgumentTypeMismatchException ex){

        String fieldName = ex.getName();
        String requiredType = ex.getRequiredType().getSimpleName();
        String message = fieldName+"이 "+requiredType+"타입이여야 합니다.";
        log.error("URI값이 올바르지 않습니다. - "+ message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse(0,message,null));
    }

}
