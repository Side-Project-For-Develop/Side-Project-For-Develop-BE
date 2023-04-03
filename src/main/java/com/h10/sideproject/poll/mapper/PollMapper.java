package com.h10.sideproject.poll.mapper;

import com.h10.sideproject.category.entity.Category;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.dto.PollRequestDto;
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
    }
