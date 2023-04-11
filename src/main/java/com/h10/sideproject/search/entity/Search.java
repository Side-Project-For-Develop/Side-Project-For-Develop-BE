package com.h10.sideproject.search.entity;

import com.h10.sideproject.common.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Search extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String words;
    @Column()
    private Long count;


    @Builder
    public Search(String words, Long count){
        this.words = words;
        this.count = count;
    }

    public void Counting(){
        this.count++;
    }



}
