package com.h10.sideproject.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchResponseDto {
    private Long poll_id;
    private String nickname; // 설문 작성자
    private String title; //설문 제목
    private String category; //카테고리
    private String choice1; //첫번째 선택지
    private String choice2; //두번째 선택지
    private Long view;  //조회수
    private Double count; // 투표한 사람 수

    @Builder
    public SearchResponseDto(Long poll_id, String nickname, String title, String category, String choice1, String choice2, Long view, Double count){
        this.category = category;
        this.count = count;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.view = view;
        this.poll_id = poll_id;
        this.nickname = nickname;
        this.title = title;
    }


}
