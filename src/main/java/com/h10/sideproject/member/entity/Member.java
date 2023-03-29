package com.h10.sideproject.member.entity;

import com.h10.sideproject.poll.entity.Poll;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy="member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Poll> pollList;

    @Builder
    public Member(String memberId, String nickname, String password, String email){
        this.memberId = memberId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }
}
