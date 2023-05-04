package com.h10.sideproject.Result.service;

import com.h10.sideproject.Result.dto.ResultRequestDto;
import com.h10.sideproject.Result.entity.Result;
import com.h10.sideproject.Result.mapper.ResultMapper;
import com.h10.sideproject.Result.repository.ResultRepository;
import com.h10.sideproject.common.exception.CustomException;
import com.h10.sideproject.common.response.ErrorCode;
import com.h10.sideproject.common.response.MessageCode;
import com.h10.sideproject.common.response.ResponseMessage;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.entity.Poll;
import com.h10.sideproject.poll.repository.PollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResultService {
     private final PollRepository pollRepository;
    private final ResultRepository resultRepository;

    private final ResultMapper resultMapper;
    @Transactional
    public ResponseMessage<?> createResult(Long poll_id, ResultRequestDto resultRequestDto, Member member) {
        Poll poll = pollRepository.findById(poll_id).orElseThrow(() -> new CustomException(ErrorCode.POLL_NOT_FOUND));
        boolean check = resultRepository.existsByPollAndMember(poll,member);
        if(check){
            return new ResponseMessage<>(ErrorCode.VOTE_DUPLICATE);
        }else{
            Result result= resultMapper.toResult(resultRequestDto,poll,member);
            resultRepository.save(result);
            return new ResponseMessage<>(MessageCode.VOTE_SUCCESS,null);
        }
    }

    @Transactional
    public ResponseMessage<?> deleteResult(Long result_id, Member member) {
        Result result = resultRepository.findById(result_id).orElseThrow(() -> new CustomException(ErrorCode.VOTE_NOT_FOUND));
        if(result.getMember().getId() == member.getId()){
            resultRepository.delete(result);
            return new ResponseMessage<>(MessageCode.VOTE_DELETE_SUCCESS,null);
        }else {
            return new ResponseMessage<>(ErrorCode.VOTE_NOT_PERMISSION);
        }
    }
}
