package com.h10.sideproject.Result.repository;

import com.h10.sideproject.Result.entity.Result;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result,Long> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Boolean existsByPollAndMember(Poll poll, Member member);
    Double countAllByPollAndChoice(Poll poll, String choice);

    Double countAllByPoll(Poll poll);

    Optional<Result> findByPollAndMember(Poll poll, Member member);
}
