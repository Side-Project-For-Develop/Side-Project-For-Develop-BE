package com.h10.sideproject.like.repository;

import com.h10.sideproject.like.entity.Likes;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes,Long> {

    Optional<Likes> findByPollAndAndMember(Poll poll, Member member);
}
