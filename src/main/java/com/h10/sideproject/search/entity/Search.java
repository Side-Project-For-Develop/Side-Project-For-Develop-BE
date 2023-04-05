package com.h10.sideproject.search.entity;

import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.entity.Poll;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String words;

    @Builder
    public Search(String words){
        this.words = words;
    }

}
