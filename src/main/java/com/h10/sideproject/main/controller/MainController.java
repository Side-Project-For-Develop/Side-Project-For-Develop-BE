package com.h10.sideproject.main.controller;


import com.h10.sideproject.common.response.ResponseMessage;
import com.h10.sideproject.main.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController {
    private final MainService mainService;

    @GetMapping("/topic")
    public ResponseMessage topic(){
        ResponseMessage responseMessage = mainService.topic();
        return responseMessage;
    }

    @GetMapping("/keyword")
    public ResponseMessage  keyword(){
        ResponseMessage responseMessage = mainService.keyword();
        return responseMessage;
    }


}
