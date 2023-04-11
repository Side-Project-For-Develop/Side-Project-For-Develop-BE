package com.h10.sideproject.main.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KeywordResponseDto {
    private List<String> keywordList;

    @Builder
    public KeywordResponseDto(List<String> keywordList){
        this.keywordList = keywordList;
    }

}
