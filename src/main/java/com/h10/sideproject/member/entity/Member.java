package com.h10.sideproject.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column
    private String profileImage;

    @Builder
    public Member(String memberId, String nickname, String password, String email){
        this.memberId = memberId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }
}
