package com.h10.sideproject.common.Image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ImagerRequestDto {
    private MultipartFile imageFile;
}
