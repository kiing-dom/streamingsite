package com.kiingdom.streamingsite.service.content;

import java.util.List;
import java.util.Optional;

import com.kiingdom.streamingsite.model.content.Content;
import com.kiingdom.streamingsite.model.content.DifficultyLevel;

public interface ContentService {
    Content saveContent(Content content);
    Optional<Content> getContentById(Long id);
    List<Content> getAllContent();
    Content updateContent(Content content);
    void deleteContent(Long id);
    List<Content> getContentByCategory(Long categoryId);
    List<Content> getContentByInstructor(String instructor);
    List<Content> getContentByDifficultyLevel(DifficultyLevel difficultyLevel);
    List<Content> searchContentByTitle(String title);
}
