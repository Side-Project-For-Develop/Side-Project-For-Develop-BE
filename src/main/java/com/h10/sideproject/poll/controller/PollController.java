package com.h10.sideproject.poll.controller;

import com.h10.sideproject.common.ResponseMessage;
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
    public ResponseMessage<?> createPoll(@RequestBody PollRequestDto pollRequestDto, @AuthenticationPrincipal MemberDetailsImpl member) {
        return pollService.createPoll(pollRequestDto,member.getMember());
    }

    @GetMapping("/poll/{poll_id}")
    public ResponseEntity<?> readPoll(@PathVariable Long poll_id, @AuthenticationPrincipal UserDetails user){
        return  pollService.readPoll(poll_id,user);
    }

    @PatchMapping("/poll/{poll_id}")
    public ResponseEntity<?> updatePoll(@PathVariable Long poll_id, @RequestBody PollRequestDto pollRequestDto, @AuthenticationPrincipal MemberDetailsImpl member){
        return  pollService.updatePoll(pollRequestDto,poll_id,member);
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
