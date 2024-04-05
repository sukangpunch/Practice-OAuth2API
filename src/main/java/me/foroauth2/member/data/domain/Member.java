package me.foroauth2.member.data.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.foroauth2.common.domain.BaseTimeEntity;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;


    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<String> role;

    @Builder
    public Member(String provider, String nickname, String email, List<String> role)
    {
        this.provider = provider;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }

}
