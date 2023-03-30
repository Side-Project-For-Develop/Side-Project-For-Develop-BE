package com.h10.sideproject.poll.repository;

import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PollRepository extends JpaRepository<Poll,Long> {
    List<Poll> findAllByMemberNot(Member member);
}
