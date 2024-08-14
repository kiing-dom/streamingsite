package com.kiingdom.streamingsite.service.content;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kiingdom.streamingsite.model.content.Content;
import com.kiingdom.streamingsite.model.content.DifficultyLevel;
import com.kiingdom.streamingsite.repository.ContentRepository;

@Service
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    public ContentServiceImpl(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public Content saveContent(Content content) {
        return contentRepository.save(content);
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
    public void deleteContent(Long id) {
        contentRepository.deleteById(id);
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

}
