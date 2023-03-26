package com.h10.sideproject.poll.entity;

import com.h10.sideproject.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Poll extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poll_id;

    @Column(nullable = false)
    private String title; //설문 제목

    @Column()
    private String choice1; //첫번째 선택지

    @Column()
    private String choice1_img; //첫번째 선택지 이미지

    @Column()
    private String choice2; //두번째 선택지

    @Column()
    private String choice2_img; //두번째 선택지 이미지

    @Column()
    private Long view;  //조회수

    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category; //카테고리
}
