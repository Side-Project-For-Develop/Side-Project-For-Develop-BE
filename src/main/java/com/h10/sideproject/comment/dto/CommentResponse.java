package com.h10.sideproject.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {
    private Long commentId;
    private String comment;
    private boolean status;
}
