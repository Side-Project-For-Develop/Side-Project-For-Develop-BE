package com.h10.sideproject.poll.controller;

import com.h10.sideproject.common.response.ResponseMessage;
import com.h10.sideproject.poll.dto.PollRequestDto;
import com.h10.sideproject.poll.service.PollService;
import com.h10.sideproject.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PollController {

    private final PollService pollService;

    @PostMapping("/poll")
    public ResponseMessage<?> createPoll(@RequestBody PollRequestDto pollRequestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return pollService.createPoll(pollRequestDto,memberDetails.getMember());
    }

    @GetMapping("/poll/{poll_id}")
    public ResponseMessage<?> readPoll(@PathVariable Long poll_id, @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return  pollService.readPoll(poll_id,memberDetails.getMember());
    }

    @PatchMapping("/poll/{poll_id}")
    public ResponseMessage<?> updatePoll(@PathVariable Long poll_id, @RequestBody PollRequestDto pollRequestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return  pollService.updatePoll(pollRequestDto,poll_id,memberDetails.getMember());
    }

    @DeleteMapping("/poll/{poll_id}")
    public ResponseEntity<?> deletePoll(@PathVariable Long poll_id, @AuthenticationPrincipal UserDetails user){
        return  pollService.deletePoll(poll_id,user);
    }
    @GetMapping("/toks")
    public ResponseEntity<?> toks(@AuthenticationPrincipal UserDetails user){
        return  pollService.toks(user);
    }

}
