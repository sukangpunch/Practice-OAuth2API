package me.foroauth2.member.data.dto.response;

import lombok.Builder;
import me.foroauth2.member.data.domain.Member;


@Builder
public record MemberResDto(
        Long memberId,
        String email,
        String nickname
){
    public static MemberResDto toDto(Member member)
    {
        return MemberResDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }


}
