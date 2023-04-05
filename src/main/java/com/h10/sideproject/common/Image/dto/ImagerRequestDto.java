package com.h10.sideproject.common.Image.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class ImagerRequestDto {
    private List<MultipartFile> imageFile;
}
