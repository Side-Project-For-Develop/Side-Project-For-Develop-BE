package com.h10.sideproject.comment.entity;

import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.entity.Poll;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "pollId", nullable = false)
    private Poll poll;

    @Builder
    public Comment(Member member, Poll poll, String comment) {
        this.member = member;
        this.poll = poll;
    }
    public void update(String comment) {
        this.comment = comment;
    }
}