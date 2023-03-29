package com.h10.sideproject.like.entity;

import com.h10.sideproject.common.Timestamped;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.entity.Poll;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Likes extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long like_id;

    @JoinColumn(name = "poll_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Poll poll; //설문

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //멤버

}
