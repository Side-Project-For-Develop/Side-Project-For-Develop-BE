package com.h10.sideproject.poll.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PollRequestDto {
    private String title; //설문 제목
    private String category; //카테고리
    private String choice1; //첫번째 선택지
    private String choice1_img; //첫번째 선택지 이미지
    private String choice2; //두번째 선택지
    private String choice2_img; //두번째 선택지 이미지

}