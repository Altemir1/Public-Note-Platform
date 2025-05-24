package com.example.publicnotes.service;

import com.example.publicnotes.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public UserService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void registerUser(User user) throws JsonProcessingException {
        String userKey = "user:" + user.getEmail();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(userKey))) {
            throw new RuntimeException("User already exists");
        }
        String userJson = objectMapper.writeValueAsString(user);
        redisTemplate.opsForValue().set(userKey, userJson);
    }

    public User loginUser(String email, String password) throws JsonProcessingException {
        String userKey = "user:" + email;
        String userJson = (String) redisTemplate.opsForValue().get(userKey);
        
        if (userJson == null) {
            throw new RuntimeException("User not found");
        }

        User user = objectMapper.readValue(userJson, User.class);
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
} 