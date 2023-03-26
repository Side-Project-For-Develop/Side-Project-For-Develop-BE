package com.h10.sideproject.common.Image.controller;

import com.h10.sideproject.common.Image.dto.ImagerRequestDto;
import com.h10.sideproject.common.Image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(ImagerRequestDto imagerRequestDto) throws IOException {
        List<String> imagePathList = imageService.imageUpload(imagerRequestDto.getImageFile());
        return new ResponseEntity<Object>(imagePathList, HttpStatus.OK);
    }
}
