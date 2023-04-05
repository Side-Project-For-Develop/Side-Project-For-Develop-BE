package com.h10.sideproject.poll.repository;

import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PollRepository extends JpaRepository<Poll,Long> {
    List<Poll> findAllByMemberNot(Member member);

    List<Poll> findAllByOrderByCreatedAtDesc();

    List<Poll> findAllByCategoryNameOrderByCreatedAtDesc(String category);

    List<Poll> findAllByCategoryNameOrderByViewDesc(String category);

    List<Poll> findAllByOrderByViewDesc();

    List<Poll> findByTitleContainingOrChoice1ContainingOrChoice2ContainingOrderByCreatedAtDesc(String words, String words1, String words2);

    List<Poll> findByTitleContainingOrChoice1ContainingOrChoice2ContainingOrderByViewDesc(String words, String words1, String words2);

    List<Poll> findAllByCategoryName(String category);

    List<Poll> findByCategoryName(String category);

    List<Poll> findByCategoryNameAndTitleContainingOrChoice1ContainingOrChoice2ContainingOrderByCreatedAtDesc(String category, String words, String words1, String words2);

    @Transactional
    @Modifying
    @Query("select p from Poll p where Function('replace', p.category.name, ' ', '') like %:name%")
    List<Poll> selectCategoryNameSQL(String name);
}
