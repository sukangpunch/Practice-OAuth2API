package me.foroauth2.member.data.dto.response;

import lombok.Builder;

@Builder
public record GoogleOAuthResDto(
        String token_type,
        String id_token
){}
