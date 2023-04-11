package com.h10.sideproject.Result.repository;

import com.h10.sideproject.Result.entity.Result;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result,Long> {
    Boolean existsByPollAndMember(Poll poll, Member member);
    Double countAllByPollAndChoice(Poll poll, String choice);

    Double countAllByPoll(Poll poll);

}