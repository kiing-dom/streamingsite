package com.kiingdom.streamingsite.controller.content;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiingdom.streamingsite.model.content.Content;
import com.kiingdom.streamingsite.model.content.DifficultyLevel;
import com.kiingdom.streamingsite.service.content.ContentService;

@RestController
@RequestMapping("/api/content")
public class ContentController {
    
    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @PostMapping
    public ResponseEntity<Content> createContent(@RequestBody Content content) {
        Content savedContent = contentService.saveContent(content);
        return new ResponseEntity<>(savedContent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> getContentById(@PathVariable Long id) {
        return contentService.getContentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Content>> getAllContent() {
        List<Content> content = contentService.getAllContent();
        return ResponseEntity.ok(content);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Content> updateContent(@PathVariable Long id, @RequestBody Content content) {
        if(!id.equals(content.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Content updatedContent = contentService.updateContent(content);
        return ResponseEntity.ok(updatedContent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Content>> getContentByCategory(@PathVariable Long categoryId) {
        List<Content> content = contentService.getContentByCategory(categoryId);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/instructor/{instructor}")
    public ResponseEntity<List<Content>> getContentByInstructor(@PathVariable String instructor) {
        List<Content> content = contentService.getContentByInstructor(instructor);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/difficulty/{level}")
    public ResponseEntity<List<Content>> getContentByDifficultyLevel(@PathVariable DifficultyLevel level) {
        List<Content> content = contentService.getContentByDifficultyLevel(level);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Content>> searchContent(@RequestParam String title) {
        List<Content> content = contentService.searchContentByTitle(title);
        return ResponseEntity.ok(content);
    }
}
