package com.kiingdom.streamingsite.service.content;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kiingdom.streamingsite.model.content.Content;
import com.kiingdom.streamingsite.model.content.DifficultyLevel;
import com.kiingdom.streamingsite.repository.ContentRepository;
import com.kiingdom.streamingsite.service.aws.AWSServiceImpl;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    private final AWSServiceImpl awsService;

    public ContentServiceImpl(ContentRepository contentRepository, AWSServiceImpl awsService) {
        this.contentRepository = contentRepository;
        this.awsService = awsService;
    }

    @Override
    public Content saveContent(Content content, MultipartFile videoFile, MultipartFile thumbnailFile)
            throws IOException {
        String videoKey = awsService.uploadFile(videoFile);
        String thumbnailKey = awsService.uploadFile(thumbnailFile);

        String transcodedVideoKey = "transcoded-" + videoKey;
        String jobId = awsService.transcodeVideo(videoKey, transcodedVideoKey);

        content.setVideoUrl(transcodedVideoKey);
        content.setThumbnailUrl(thumbnailKey);
        content.setTranscodeJobId(jobId);

        content.setDateAdded(LocalDateTime.now());

        Content savedContent = contentRepository.save(content);

        awsService.invalidateCloudFrontCache(transcodedVideoKey);
        awsService.invalidateCloudFrontCache(thumbnailKey);

        return savedContent;
    }

    @Override
    public void deleteContent(Long id) {
        Optional<Content> contentOpt = contentRepository.findById(id);

        if (contentOpt.isPresent()) {
            Content content = contentOpt.get();

            awsService.deleteFile(content.getVideoUrl());

            String originalVideoKey = content.getVideoUrl().replace("transcoded-", "");
            awsService.deleteFile(originalVideoKey);

            awsService.deleteFile(content.getThumbnailUrl());

            contentRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Content> getContentById(Long id) {
        return contentRepository.findById(id);
    }

    @Override
    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    @Override
    public Content updateContent(Content content) {
        return contentRepository.save(content);
    }

    @Override
    public List<Content> getContentByCategory(Long categoryId) {
        return contentRepository.findByCategory_Id(categoryId);
    }

    @Override
    public List<Content> getContentByInstructor(String instructor) {
        return contentRepository.findByInstructor(instructor);
    }

    @Override
    public List<Content> getContentByDifficultyLevel(DifficultyLevel difficultyLevel) {
        return contentRepository.findByDifficultyLevel(difficultyLevel);
    }

    @Override
    public List<Content> searchContentByTitle(String title) {
        return contentRepository.findByTitleContainingIgnoreCase(title);
    }

    @Transactional(readOnly = true)
    public List<Content> findAllWithCategory() {
        return contentRepository.findAll();
    }
}
