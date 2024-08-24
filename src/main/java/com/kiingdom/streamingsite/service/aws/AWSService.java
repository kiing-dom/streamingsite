package com.kiingdom.streamingsite.service.aws;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface AWSService {
    String uploadFile(MultipartFile file) throws IOException;
    String transcodeVideo(String videoKey, String transcodedVideoKey);
    void invalidateCloudFrontCache(String key);
    void deleteFile(String fileKey);
}
