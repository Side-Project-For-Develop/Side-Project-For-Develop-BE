package com.h10.sideproject.category.mapper;

import com.h10.sideproject.category.entity.Category;
import com.h10.sideproject.poll.dto.PollRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatrgoryMapper {
    public Category toCatrgory(PollRequestDto pollRequestDto) {
        return  Category.builder().name(pollRequestDto.getCategory()).build();
    }
}
