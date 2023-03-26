package com.h10.sideproject.poll.controller;

import com.h10.sideproject.poll.dto.PollRequestDto;
import com.h10.sideproject.poll.service.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PollController {

    private final PollService pollService;

    @PostMapping("/poll")
    public ResponseEntity<?> createPoll(@RequestBody PollRequestDto pollRequestDto) {
        pollService.createPoll(pollRequestDto);
        return new ResponseEntity<>("설문 작성 성공",HttpStatus.OK);
    }

    @PatchMapping("/poll/{poll_id}")
    public ResponseEntity<?> updatePoll(@PathVariable Long poll_id, @RequestBody PollRequestDto pollRequestDto){
        return  pollService.updatePoll(pollRequestDto,poll_id);
    }
}
