package com.h10.sideproject.comment.repository;

import com.h10.sideproject.comment.entity.Comment;
import com.h10.sideproject.poll.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //내가 전달해준 Poll 을 기준으로 comment 전부를 찾아줄게 List 에 담아서
    List<Comment> findAllByPoll(Poll poll);
}
