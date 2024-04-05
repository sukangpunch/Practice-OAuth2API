package me.foroauth2.member.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.foroauth2.common.exception.customs.CustomException;
import me.foroauth2.common.exception.customs.ExceptionCode;
import me.foroauth2.common.jwt.JwtProvider;
import me.foroauth2.member.data.domain.Member;
import me.foroauth2.member.data.dto.response.MemberResDto;
import me.foroauth2.member.data.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public MemberResDto getMemberInfo(Long memberId) {
        log.info("[getMemberInfo] 유저의 정보 조회");

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_EXIST));

        return MemberResDto.toDto(member);
    }

}
