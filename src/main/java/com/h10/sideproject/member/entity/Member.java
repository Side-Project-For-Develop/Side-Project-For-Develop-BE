package com.h10.sideproject.member.entity;

import com.h10.sideproject.comment.entity.Comment;
import com.h10.sideproject.poll.entity.Poll;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;

    private Long kakaoId;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String nickname;
    
    @Column
    private String profileImage;
    
    @OneToMany(mappedBy="member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Poll> pollList;
    
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    
    @Builder
    public Member(String email, String password, String nickname, Long kakaoId, String profileImage){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.kakaoId = kakaoId;
        this.profileImage = profileImage;
    }
    public Member(Long kakaoId, String password, String email) {
        this.kakaoId = kakaoId;
        this.password = password;
        this.email = email;
    }
    public void update(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}