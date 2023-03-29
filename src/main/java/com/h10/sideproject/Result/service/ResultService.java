package com.h10.sideproject.Result.service;

import com.h10.sideproject.poll.entity.Poll;
import com.h10.sideproject.poll.repository.PollRepository;
import com.h10.sideproject.Result.dto.ResultRequestDto;
import com.h10.sideproject.Result.entity.Result;
import com.h10.sideproject.Result.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final PollRepository pollRepository;

    private final ResultRepository resultRepository;
    public ResponseEntity<?> createResult(Long poll_id, ResultRequestDto voteRequestDto) {
        Poll poll = pollRepository.findById(poll_id).orElse(null);
        resultRepository.save(
                Result.builder()
                        .choice(voteRequestDto.getChoice())
                        .poll(poll)
                        .build()
        );
        return new ResponseEntity<>("투표 완료", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateResult(ResultRequestDto resultRequestDto, Long result_id) {
        Result result = resultRepository.findById(result_id).orElse(null);
        result.update(resultRequestDto);
        return new ResponseEntity<>("투표 수정 완료", HttpStatus.OK);
    }
}
