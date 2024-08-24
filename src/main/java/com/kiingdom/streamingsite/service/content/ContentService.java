package com.kiingdom.streamingsite.service.content;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.kiingdom.streamingsite.model.content.Content;
import com.kiingdom.streamingsite.model.content.DifficultyLevel;

public interface ContentService {
    Content saveContent(Content content, MultipartFile videoFile, MultipartFile thumbnailFile) throws IOException;
    Optional<Content> getContentById(Long id);
    List<Content> getAllContent();
    Content updateContent(Content content);
    void deleteContent(Long id);
    List<Content> getContentByCategory(Long categoryId);
    List<Content> getContentByInstructor(String instructor);
    List<Content> getContentByDifficultyLevel(DifficultyLevel difficultyLevel);
    List<Content> searchContentByTitle(String title);
}
