package com.h10.sideproject.common.Image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.h10.sideproject.common.response.ErrorCode;
import com.h10.sideproject.common.response.MessageCode;
import com.h10.sideproject.common.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String S3Bucket; // Bucket 이름

    private final AmazonS3Client amazonS3Client;
    public ResponseMessage<?> imageUpload(MultipartFile imageFile) throws IOException {
        String imagePath = "파일 없음";

        if(imageFile != null){
            String originalName = imageFile.getOriginalFilename(); // 파일 이름
            long size = imageFile.getSize(); // 파일 크기

            ObjectMetadata objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(imageFile.getContentType());
            objectMetaData.setContentLength(size);

            // S3에 업로드
            amazonS3Client.putObject(
                    new PutObjectRequest(S3Bucket, originalName, imageFile.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
            // 접근가능한 URL 가져오기
            imagePath = amazonS3Client.getUrl(S3Bucket, originalName).toString();
            return new ResponseMessage<>(MessageCode.IMAGE_UPLOAD_SUCCESS,imagePath);
        }else{
            return new ResponseMessage<>(ErrorCode.IMAGE_UPLOAD_FAIL);
        }
    }
}
