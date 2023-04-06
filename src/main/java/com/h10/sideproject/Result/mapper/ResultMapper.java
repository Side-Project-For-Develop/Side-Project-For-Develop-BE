package com.h10.sideproject.Result.mapper;

import com.h10.sideproject.Result.dto.ResultRequestDto;
import com.h10.sideproject.Result.entity.Result;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.dto.PollResponseDto;
import com.h10.sideproject.poll.entity.Poll;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResultMapper {

        public Result toResult(ResultRequestDto resultRequestDto, Poll poll, Member member) {
            return   Result.builder()
                    .choice(resultRequestDto.getChoice())
                    .poll(poll)
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
    }
