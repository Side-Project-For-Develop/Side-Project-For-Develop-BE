package com.h10.sideproject.common.Image.controller;

import com.h10.sideproject.common.Image.dto.ImagerRequestDto;
import com.h10.sideproject.common.Image.service.ImageService;
import com.h10.sideproject.common.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseMessage<?> upload(ImagerRequestDto imagerRequestDto) throws IOException {
        return imageService.imageUpload(imagerRequestDto.getImageFile());
    }
}
