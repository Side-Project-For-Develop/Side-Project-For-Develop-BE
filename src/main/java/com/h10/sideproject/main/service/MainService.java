package com.h10.sideproject.main.service;

import com.h10.sideproject.Result.repository.ResultRepository;
import com.h10.sideproject.category.entity.Category;
import com.h10.sideproject.category.repository.CategoryRepository;
import com.h10.sideproject.common.response.ResponseMessage;
import com.h10.sideproject.main.dto.KeywordResponseDto;
import com.h10.sideproject.poll.entity.Poll;
import com.h10.sideproject.poll.repository.PollRepository;
import com.h10.sideproject.search.dto.SearchResponseDto;
import com.h10.sideproject.search.dto.SearchResponseFourDto;
import com.h10.sideproject.search.entity.Search;
import com.h10.sideproject.search.mapper.SearchMapper;
import com.h10.sideproject.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainService {
    private final PollRepository pollRepository;
    private final SearchMapper searchMapper;
    private final ResultRepository resultRepository;
    private final CategoryRepository categoryRepository;
    private final SearchRepository searchRepository;

    public ResponseMessage topic() {
        String[] kind = new String[]{"유머", "취미", "IT", "건강"};

        List<List<SearchResponseDto>> searchResponseFourDtos = new ArrayList<>();

        for (String name : kind) {
            LocalDateTime start = LocalDateTime.now().minusDays(1);
            LocalDateTime end = LocalDateTime.now();

            Optional<Category> category = categoryRepository.findByName(name);

            List<Poll> pollList = new ArrayList<>();

            if (category.isPresent()) {
                pollList = pollRepository.findByCategoryAndCreatedAtBetweenOrderByViewDesc(category.get(), start, end);
            }

            List<SearchResponseDto> responseDtos = new ArrayList<>();

            for (Poll poll : pollList) {
                if (responseDtos.size() >= 4) break;
                responseDtos.add(searchMapper.topicResponseDto(poll, resultRepository.countAllByPoll(poll)));
            }
            searchResponseFourDtos.add(responseDtos);
        }

        SearchResponseFourDto searchResponseFourDto = new SearchResponseFourDto(searchResponseFourDtos);
        return new ResponseMessage("Success", 200, searchResponseFourDto);
    }

    public ResponseMessage keyword() {

        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();

        List<Search> searchList = searchRepository.findTop10ByCreatedAtBetweenOrderByCountDescModifiedAtDesc(start, end);

        List<String> keywordList = new ArrayList<>();

        String[] kindList = new String[]{"유머", "취미", "IT", "건강"};
        List<String> randomKeyword = new ArrayList<>(Arrays.asList(kindList));
        for (Search search : searchList) {
            if(keywordList.isEmpty() || !keywordList.contains(search)){
                keywordList.add(search.getWords());
            }
        }

        while (randomKeyword.size() > 0){
            if(keywordList.size() >= 10){
                break;
            }
            int idx = (int)(Math.random() * randomKeyword.size());
            String kind = randomKeyword.get(idx);
            if(keywordList.isEmpty() || !keywordList.contains(kind)){
                keywordList.add(kind);
            }
            randomKeyword.remove(idx);
        }

        KeywordResponseDto keywordResponseDtos = searchMapper.toWordsResponseDto(keywordList);


        return new ResponseMessage("Success", 200, keywordResponseDtos);


    }


}


