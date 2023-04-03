package com.h10.sideproject.search.repository;

import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.search.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository  extends JpaRepository<Search, Long> {
}
