package com.h10.sideproject.comment.mapper;

import com.h10.sideproject.comment.dto.CommentListDto;
import com.h10.sideproject.comment.dto.CommentRequest;
import com.h10.sideproject.comment.entity.Comment;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.entity.Poll;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment toComment(CommentRequest commentRequest, Member member, Poll poll) {
        return Comment.builder()
                .comment(commentRequest.getComment())
                .member(member)
                .poll(poll)
                .build();
    }
    //CommentListDto 라는 박스에 List<Comment> commentList 라는 선물을 담아서 만든다.
    //준다는 생각 빼기
    public CommentListDto toCommentList(Comment comment) {
        return CommentListDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .nickname(comment.getMember().getNickname())
                .build();
        }
}
