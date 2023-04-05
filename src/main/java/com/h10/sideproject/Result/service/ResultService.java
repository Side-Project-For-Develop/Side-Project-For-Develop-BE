package com.h10.sideproject.Result.service;

import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.member.repository.MemberRepository;
import com.h10.sideproject.poll.entity.Poll;
import com.h10.sideproject.poll.repository.PollRepository;
import com.h10.sideproject.Result.dto.ResultRequestDto;
import com.h10.sideproject.Result.entity.Result;
import com.h10.sideproject.Result.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final MemberRepository memberRepository;
    private final PollRepository pollRepository;

    private final ResultRepository resultRepository;
    public ResponseEntity<?> createResult(Long poll_id, ResultRequestDto voteRequestDto, UserDetails user) {
        Poll poll = pollRepository.findById(poll_id).orElse(null);
        Member member = memberRepository.findByEmail(user.getUsername()).orElse(null);
        resultRepository.save(
                Result.builder()
                        .choice(voteRequestDto.getChoice())
                        .poll(poll)
                        .member(member)
                        .build()
        );
        return new ResponseEntity<>("투표 완료", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteResult(Long result_id, UserDetails user) {
        Result result = resultRepository.findById(result_id).orElse(null);
        Member member = memberRepository.findByEmail(user.getUsername()).orElse(null);
        if(result != null && result.getMember().getId() == member.getId()){
            resultRepository.delete(result);
            return new ResponseEntity<>("투표 삭제 완료", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("권한 없음", HttpStatus.OK);
        }
    }
}
