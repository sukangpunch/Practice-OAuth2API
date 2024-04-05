package me.foroauth2.common.config;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*Swagger 문서에 대한 API 응답 정이를 위한 커스텀 어노테이션(SwaggerApiResponses)을 정의*/

//어노테이션이 적용 될 대상을 지정합니다. 클래스와 메서드에 적용될 수 있도록 지정함
@Target({ElementType.TYPE, ElementType.METHOD})
// 어노테이션 정보가 유지되는 범위를 지정합니다. Runtime 시에도 어노테이션 정보를 유지하도록 지정
@Retention(RetentionPolicy.RUNTIME)
// Swagger 문서에 대한 작업 에 대한 정보를 나타내는 어노테이션
@Operation()
//각각의 응답에 대한 정보를 나타내는 어노테이션, '@ApiResponse' 어노테이션을 배열 형태로 받아 각 응답에 대한 정보를 정의한다.
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "201", description = "성공"),
        @ApiResponse(responseCode = "400", description =
                "예외 설명: <br><br>" +
        "1. 토큰 타입이 틀렸습니다. <br><br>" +
        "2. 멤버가 존재하지 않습니다."),
        @ApiResponse(responseCode = "401", description =
                "예외 설명: <br><br>" +
                        "1. 인증이 실패했습니다. => 토큰을 공백으로 보냈을 확률이 높습니다.<br><br>" +
                        "2. 토큰이 만료되었습니다.<br><br>3. 토큰이 비었거나 null입니다.<br><br>" +
                        "4. 잘못된 형식의 토큰입니다.<br><br>" +
                        "5. 인증되지 않은 토큰입니다."),
        @ApiResponse(responseCode = "403", description = "권한이 없습니다.")

})

//@interface 란, 자바에서 어노테이션을 정의할 때 사용,따라서 SwaggerApiResponses 라는 어노테이션을 작성 한 것
public @interface SwaggerApiResponses {
}
