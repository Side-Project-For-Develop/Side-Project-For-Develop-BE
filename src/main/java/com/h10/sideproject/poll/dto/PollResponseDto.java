package com.h10.sideproject.poll.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PollResponseDto {
    private Long id;    //설문 아이디
    private String nickname; // 설문 작성자
    private String title; //설문 제목
    private String category; //카테고리
    private String choice1; //첫번째 선택지
    private String choice1_img; //첫번째 선택지 이미지
    private String choice2; //두번째 선택지
    private String choice2_img; //두번째 선택지 이미지
    private Long view;  //조회수
    private boolean vote; // 투표했는지 여부
    private String choice1_result; //1번 선택지 결과

    private String choice2_result; //2번 선택지 결과
}
