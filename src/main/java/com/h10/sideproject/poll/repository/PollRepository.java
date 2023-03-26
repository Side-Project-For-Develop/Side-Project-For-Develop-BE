package com.h10.sideproject.poll.repository;

import com.h10.sideproject.poll.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll,Long> {
}
