package com.h10.sideproject.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentListDto {
    private Long id;
    private String comment;
    private String nickname;
}
