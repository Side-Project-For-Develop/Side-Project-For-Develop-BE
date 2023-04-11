package com.h10.sideproject.Result.controller;

import com.h10.sideproject.Result.dto.ResultRequestDto;
import com.h10.sideproject.Result.service.ResultService;
import com.h10.sideproject.common.response.ResponseMessage;
import com.h10.sideproject.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ResultController {

    private final ResultService resultService;

    @PostMapping("/result/{poll_id}")
    public ResponseMessage<?> createResult(@PathVariable Long poll_id, @RequestBody ResultRequestDto resultRequestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return resultService.createResult(poll_id,resultRequestDto,memberDetails.getMember());
    }

    @DeleteMapping("/result/{result_id}")
    public ResponseMessage<?> deleteResult(@PathVariable Long result_id,  @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return  resultService.deleteResult(result_id,memberDetails.getMember());
    }

}
