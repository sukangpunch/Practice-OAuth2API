package me.foroauth2.member.data.dto.request;

import lombok.Builder;

@Builder
public record GoogleOAuthReqDto(
        String redirectUri,
        String clientId,
        String clientSecret,
        String code,
        String grantType

)
{}
