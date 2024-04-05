package me.foroauth2.member.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.foroauth2.common.config.SwaggerApiResponses;
import me.foroauth2.common.dto.CommonResponse;
import me.foroauth2.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/v1/members/{memberId}")
    @Operation(summary = "멤버 정보 조회", description = "")
    @SwaggerApiResponses
    public ResponseEntity<CommonResponse> MemberInfo(@PathVariable Long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse(1, "성공", memberService.getMemberInfo(memberId)));
    }
}
