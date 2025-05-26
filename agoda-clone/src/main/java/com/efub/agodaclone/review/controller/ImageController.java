package com.efub.agodaclone.review.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.efub.agodaclone.review.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;
    private  final AmazonS3 s3Client;

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestPart(value = "image", required = false)MultipartFile image) throws IOException {
        String savedImage = imageService.saveFile(image);
        return ResponseEntity.ok(savedImage);
    }

    public ResponseEntity<UrlResource> downloadImage(String originalFileName){
        UrlResource urlResource = new UrlResource(s3Client.getUrl(imageService.getBucket(), originalFileName));
        String contentDisposition = "attachment; filename=\"" +  originalFileName + "\"";

        // header에 CONTENT_DISPOSITION 설정을 통해 다운로드 진행
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
