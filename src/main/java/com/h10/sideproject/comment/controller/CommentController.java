package com.h10.sideproject.comment.controller;

import com.h10.sideproject.comment.dto.CommentListDto;
import com.h10.sideproject.comment.dto.CommentRequest;
import com.h10.sideproject.comment.service.CommentService;
import com.h10.sideproject.common.response.MessageCode;
import com.h10.sideproject.common.response.ResponseMessage;
import com.h10.sideproject.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    //댓글 작성 
    @PostMapping("/{pollId}")
    public ResponseMessage<?> createComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                            @RequestBody CommentRequest commentRequest,
                                            @PathVariable Long pollId) {
        commentService.createComment(memberDetails.getMember(), commentRequest, pollId);
        return new ResponseMessage<>(MessageCode.COMMEMT_CREATE_SUCCESS, null);
    }
    //특정 게시글 댓글 조회
    //게시글에서 댓글을 조회하도록 해야하기 때문에 게시글과 매핑 -> 해당 게시글의 댓글을 가져와야하기 때문에 pollId로 매핑하기
    @GetMapping("/{pollId}")
    public ResponseMessage<?> getComments(@PathVariable Long pollId) {
        ArrayList<CommentListDto> commentListDto = commentService.checkComments(pollId);
        return new ResponseMessage<>(MessageCode.COMMENT_CHECK_SUCCESS, commentListDto);
    }
    //댓글 수정
    @PutMapping ("/{commentId}")
    public ResponseMessage<?> updateComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                            @PathVariable Long commentId,
                                            @RequestBody CommentRequest commentRequest) {
        commentService.update(memberDetails.getMember(),commentId, commentRequest);
        return new ResponseMessage<>(MessageCode.COMMENT_UPDATE_SUCCESS, null);
    }
    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseMessage<?> deleteComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                            @PathVariable Long commentId) {
        commentService.deleteComment(memberDetails.getMember(), commentId);
        return new ResponseMessage<>(MessageCode.COMMENT_DELETE_SUCCESS, null);
    }
}