package com.h10.sideproject.search.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchResponseFourDto {
    private List<SearchResponseDto> category1ResponseDtos;
    private List<SearchResponseDto> category2ResponseDtos;
    private List<SearchResponseDto> category3ResponseDtos;
    private List<SearchResponseDto> category4ResponseDtos;


    public SearchResponseFourDto(List<List<SearchResponseDto>> searchResponseFourDtos) {

        this.category1ResponseDtos = searchResponseFourDtos.get(0);
        this.category2ResponseDtos = searchResponseFourDtos.get(1);
        this.category3ResponseDtos = searchResponseFourDtos.get(2);
        this.category4ResponseDtos = searchResponseFourDtos.get(3);

    }

}
