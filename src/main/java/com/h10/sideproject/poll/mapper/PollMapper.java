package com.h10.sideproject.poll.mapper;

import com.h10.sideproject.Result.dto.ResultResponseDto;
import com.h10.sideproject.category.entity.Category;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.dto.PollRequestDto;
import com.h10.sideproject.poll.dto.PollResponseDto;
import com.h10.sideproject.poll.entity.Poll;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PollMapper {

        public Poll toPoll(PollRequestDto pollRequestDto, Category category, Member member) {
            return  Poll.builder()
                    .title(pollRequestDto.getTitle())
                    .category(category)
                    .choice1(pollRequestDto.getChoice1())
                    .choice1_img(pollRequestDto.getChoice1_img())
                    .choice2(pollRequestDto.getChoice2())
                    .choice2_img(pollRequestDto.getChoice2_img())
                    .view(0L)
                    .member(member)
                    .build();
        }

        public PollResponseDto toPollResponseDto(Poll poll,Boolean check, String percent1, String percent2){
            return PollResponseDto.builder()
                    .id(poll.getPoll_id())
                    .nickname(poll.getMember().getNickname())
                    .category(poll.getCategory().getName())
                    .title(poll.getTitle())
                    .choice1(poll.getChoice1())
                    .choice1_img(poll.getChoice1_img())
                    .choice2(poll.getChoice2())
                    .choice2_img(poll.getChoice2_img())
                    .view(poll.getView())
                    .vote(check)
                    .choice1_result(percent1)
                    .choice2_result(percent2)
                    .build();
        }

    public ResultResponseDto toResultResponseDto(Poll poll, Boolean check, String percent1, String percent2, String myChoice){
        return ResultResponseDto.builder()
                .id(poll.getPoll_id())
                .nickname(poll.getMember().getNickname())
                .category(poll.getCategory().getName())
                .title(poll.getTitle())
                .choice1(poll.getChoice1())
                .choice1_img(poll.getChoice1_img())
                .choice2(poll.getChoice2())
                .choice2_img(poll.getChoice2_img())
                .view(poll.getView())
                .vote(check)
                .choice1_result(percent1)
                .choice2_result(percent2)
                .myChoice(myChoice)
                .build();
    }
    }
