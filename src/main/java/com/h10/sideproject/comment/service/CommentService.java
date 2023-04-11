package com.h10.sideproject.comment.service;

import com.h10.sideproject.comment.dto.CommentListDto;
import com.h10.sideproject.comment.dto.CommentRequest;
import com.h10.sideproject.comment.entity.Comment;
import com.h10.sideproject.comment.mapper.CommentMapper;
import com.h10.sideproject.comment.repository.CommentRepository;
import com.h10.sideproject.common.exception.CustomException;
import com.h10.sideproject.common.response.ErrorCode;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.entity.Poll;
import com.h10.sideproject.poll.repository.PollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PollRepository pollRepository;

    private final CommentMapper commentMapper;
    //댓글 작성
    @Transactional
    public void createComment(Member member,
                              CommentRequest commentRequest,
                              Long pollId) {
        if(commentRequest == null || commentRequest.getComment() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_COMMENT);
        }
        Poll poll = pollRepository.findById(pollId).orElseThrow(()-> new CustomException(ErrorCode.POLL_NOT_FOUND));
        // mapper 는 객체를 만들어주는 의미
        Comment comment = commentMapper.toComment(commentRequest, member, poll);
        if(!comment.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.AUTHORIZATION_CANNOT_CREATE);
        }
        commentRepository.save(comment);
    }

    //댓글 조회
    @Transactional(readOnly = true)
    public ArrayList<CommentListDto> checkComments(Long pollId) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(()-> new CustomException(ErrorCode.POLL_NOT_FOUND));
        List<Comment> commentList = commentRepository.findAllByPoll(poll);
        ArrayList<CommentListDto> list = new ArrayList<>();
        for(int i = 0; i < commentList.size(); i++) {
            CommentListDto commentListDto = commentMapper.toCommentList(commentList.get(i));
            list.add(commentListDto);
        }
        return list;
    }
    //댓글 수정
    @Transactional
    public void update(Member member,
                       Long commentId,
                       CommentRequest commentRequest) {
        if(commentRequest == null || commentRequest.getComment() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_COMMENT);
        }
        //댓글 찾기
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        comment.update(commentRequest.getComment());
        //권한 부여
        if(!comment.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.AUTHORIZATION_CANNOT_UPDATE);
        }
        commentRepository.save(comment);

    }
    //댓글 삭제
    @Transactional
    public void deleteComment(Member member, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
        if(!comment.getMember().getId().equals(member.getId())) {
                throw new CustomException(ErrorCode.AUTHORIZATION_CANNOT_DELETE);
        }
        commentRepository.deleteById(commentId);
    }
}
