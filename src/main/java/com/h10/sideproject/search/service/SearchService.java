package com.h10.sideproject.search.service;

import com.h10.sideproject.Result.repository.ResultRepository;
import com.h10.sideproject.common.response.ResponseMessage;
import com.h10.sideproject.poll.entity.Poll;
import com.h10.sideproject.poll.repository.PollRepository;
import com.h10.sideproject.search.dto.SearchDtoList;
import com.h10.sideproject.search.dto.SearchResponseDto;
import com.h10.sideproject.search.entity.Search;
import com.h10.sideproject.search.mapper.SearchMapper;
import com.h10.sideproject.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final PollRepository pollRepository;
    private final SearchMapper searchMapper;
    private final SearchRepository searchRepository;
    private final ResultRepository resultRepository;

    @Transactional
    public ResponseMessage search(String words, String category, String filter) {
        List<Poll> pollList;
        List<Poll> returnPollList = new ArrayList<>();

        if (words.isEmpty()) {
            if(category.isEmpty() && filter.isEmpty()) {
                returnPollList = pollRepository.findAllByOrderByViewDesc();
            } else if (category.isEmpty() && filter != null){
                returnPollList = pollRepository.findAllByOrderByCreatedAtDesc();
            } else if (category != null && filter.isEmpty()) {
                pollList = pollRepository.findByCategoryName(category);
                for (Poll poll : pollList) {
                    if (category.equals(poll.getCategory().getName())) {
                        returnPollList = pollRepository.findAllByCategoryNameOrderByViewDesc(category);
                    }
                }
            } else if (category != null && filter != null) {
                pollList = pollRepository.findByCategoryName(category);
                for (Poll poll : pollList) {
                    if (category.equals(poll.getCategory().getName())) {
                        returnPollList = pollRepository.findAllByCategoryNameOrderByCreatedAtDesc(category);
                    }
                }
            }

        }else {
            boolean check = searchRepository.existsByWords(words);
            Search search;
            if(check){
                search = searchRepository.findByWords(words);
                search.Counting();
            }else{
                search = searchMapper.toWordsRequestDto(words);
                searchRepository.save(search);
            }

            if (category.isEmpty() && filter.isEmpty()) {
                returnPollList = pollRepository.findByTitleContainingOrChoice1ContainingOrChoice2ContainingOrderByViewDesc(words, words, words);
            } else if (category.isEmpty() && filter != null) {
                returnPollList = pollRepository.findByTitleContainingOrChoice1ContainingOrChoice2ContainingOrderByCreatedAtDesc(words, words, words);
            } else if (category != null && filter.isEmpty()) {
                pollList = pollRepository.selectCategoryNameSQL(category);
                returnPollList = pollRepository.findByTitleContainingOrChoice1ContainingOrChoice2ContainingOrderByViewDesc(words, words, words);
                for (Poll poll : pollList) {
                    if (category.equals(poll.getCategory().getName()) && returnPollList.equals(words)){
                        returnPollList.add(poll);
                    }
                }
            } else if (category != null && filter != null) {
                pollList = pollRepository.selectCategoryNameSQL(category);
                returnPollList = pollRepository.findByTitleContainingOrChoice1ContainingOrChoice2ContainingOrderByCreatedAtDesc(words, words, words);
                for (Poll poll : pollList) {
                    if (category.equals(poll.getCategory().getName()) && returnPollList.equals(words)) {
                        returnPollList.add(poll);
                    }
                }
            }
        }
        List<SearchResponseDto> searchResponseDtoList = new ArrayList<>();
        for (Poll poll : returnPollList) {
            SearchResponseDto searchResponseDto = searchMapper.toSearchResponseDto(poll, resultRepository.countAllByPoll(poll));
            searchResponseDtoList.add(searchResponseDto);
        }
        return new ResponseMessage("Success", 200, new SearchDtoList(searchResponseDtoList));




    }

}
