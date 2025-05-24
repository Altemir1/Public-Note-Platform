package com.example.publicnotes.service;

import com.example.publicnotes.model.Note;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoteService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public NoteService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public Note createNote(Note note) {
        note.setId(UUID.randomUUID().toString());
        note.setCreatedAt(new Date());
        try {
            String noteJson = objectMapper.writeValueAsString(note);
            String noteKey = "note:" + note.getId();
            
            // Store the note
            redisTemplate.opsForValue().set(noteKey, noteJson);
            
            // Store email -> note mapping
            redisTemplate.opsForList().rightPush("email:" + note.getEmail(), note.getId());
            
            // Store tag -> note mappings
            for (String tag : note.getTags()) {
                redisTemplate.opsForList().rightPush("tag:" + tag, note.getId());
            }
            
            return note;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error creating note", e);
        }
    }

    public List<Note> findByTag(String tag) {
        List<Object> noteIds = redisTemplate.opsForList().range("tag:" + tag, 0, -1);
        return getNotesByIds(noteIds);
    }

    public List<Note> findByEmail(String email) {
        List<Object> noteIds = redisTemplate.opsForList().range("email:" + email, 0, -1);
        return getNotesByIds(noteIds);
    }

    private List<Note> getNotesByIds(List<Object> noteIds) {
        if (noteIds == null || noteIds.isEmpty()) {
            return Collections.emptyList();
        }

        return noteIds.stream()
            .map(id -> {
                String noteJson = (String) redisTemplate.opsForValue().get("note:" + id);
                try {
                    return objectMapper.readValue(noteJson, Note.class);
                } catch (JsonProcessingException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public Note findById(String id) {
        String noteJson = (String) redisTemplate.opsForValue().get("note:" + id);
        try {
            return noteJson != null ? objectMapper.readValue(noteJson, Note.class) : null;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public void deleteNote(String id) {
        Note note = findById(id);
        if (note != null) {
            // Delete the note itself
            redisTemplate.delete("note:" + id);
            
            // Remove from email list
            redisTemplate.opsForList().remove("email:" + note.getEmail(), 0, id);
            
            // Remove from tag lists
            for (String tag : note.getTags()) {
                redisTemplate.opsForList().remove("tag:" + tag, 0, id);
            }
        }
    }
} 