package com.h10.sideproject.search.repository;

import com.h10.sideproject.search.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SearchRepository  extends JpaRepository<Search, Long> {

    Boolean existsByWords(String words);

    Search findByWords(String words);


    List<Search> findTop10ByCreatedAtBetweenOrderByCountDescModifiedAtDesc(LocalDateTime start, LocalDateTime end);
}

