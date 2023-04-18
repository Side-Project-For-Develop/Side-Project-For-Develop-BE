package com.h10.sideproject.comment.repository;

import com.h10.sideproject.comment.entity.Comment;
import com.h10.sideproject.poll.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //내가 전달해준 Poll 을 기준으로 comment 전부를 찾아줄게 List 에 담아서
    List<Comment> findAllByPoll(Poll poll);

    @Modifying
    @Query("delete from Comment cm where cm.poll.poll_id = :id")
    void deleteAllByCommentId(@Param("id") Long poll_id);

    @Modifying
    @Query("delete from Comment cm where cm.member.id = :id")
    void deleteAllByCommentId2(@Param("id") Long memberId);
}
