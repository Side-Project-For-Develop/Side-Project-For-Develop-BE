package com.h10.sideproject.Result.controller;

import com.h10.sideproject.Result.dto.ResultRequestDto;
import com.h10.sideproject.Result.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ResultController {

    private final ResultService resultService;

    @PostMapping("/result/{poll_id}")
    public ResponseEntity<?> createResult(@PathVariable Long poll_id, @RequestBody ResultRequestDto resultRequestDto, @AuthenticationPrincipal UserDetails user) {
        return resultService.createResult(poll_id,resultRequestDto,user);
    }

    @PatchMapping("/result/{result_id}")
    public ResponseEntity<?> updateResult(@PathVariable Long result_id, @RequestBody ResultRequestDto resultRequestDto, @AuthenticationPrincipal UserDetails user){
        return  resultService.updateResult(resultRequestDto,result_id,user);
    }

}
