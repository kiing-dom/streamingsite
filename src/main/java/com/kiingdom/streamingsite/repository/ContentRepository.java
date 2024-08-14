package com.kiingdom.streamingsite.repository;

import com.kiingdom.streamingsite.model.content.Content;
import com.kiingdom.streamingsite.model.content.DifficultyLevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByCategory_Id(Long categoryId);
    List<Content> findByInstructor(String instructor);
    List<Content> findByDifficultyLevel(DifficultyLevel difficultyLevel);
    List<Content> findByTitleContainingIgnoreCase(String title);
}
