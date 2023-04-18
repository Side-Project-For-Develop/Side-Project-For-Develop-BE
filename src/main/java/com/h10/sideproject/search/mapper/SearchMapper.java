package com.h10.sideproject.search.mapper;

import com.h10.sideproject.main.dto.KeywordResponseDto;
import com.h10.sideproject.poll.entity.Poll;
import com.h10.sideproject.search.dto.SearchResponseDto;
import com.h10.sideproject.search.entity.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchMapper {
    public SearchResponseDto toSearchResponseDto(Poll poll, Double count) {
        return SearchResponseDto.builder()
                .poll_id(poll.getPoll_id())
                .view(poll.getView())
                .title(poll.getTitle())
                .choice2(poll.getChoice2())
                .choice1(poll.getChoice1())
                .nickname(poll.getMember().getNickname())
                .category(poll.getCategory().getName())
                .count(count)
                .build();
    }

    public Search toWordsRequestDto(String words){
        return Search.builder()
                .words(words)
                .count(1L)
                .build();
    }

    public SearchResponseDto topicResponseDto(Poll poll, Double count) {
        return SearchResponseDto.builder()
                .poll_id(poll.getPoll_id())
                .view(poll.getView())
                .title(poll.getTitle())
                .choice2(poll.getChoice2())
                .choice1(poll.getChoice1())
                .nickname(poll.getMember().getNickname())
                .count(count)
                .category(poll.getCategory().getName())
                .build();
    }

    public KeywordResponseDto toWordsResponseDto(List<String> keywordList){
        return KeywordResponseDto.builder()
                .keywordList(keywordList)
                .build();
    }


}
