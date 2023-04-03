package com.h10.sideproject.search.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchDtoList {
    private List<SearchResponseDto> searchResponseDtoList;

    public SearchDtoList(List<SearchResponseDto> searchResponseDtoList){
        this.searchResponseDtoList = searchResponseDtoList;
    }
}
