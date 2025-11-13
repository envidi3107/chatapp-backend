package com.group4.chatapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void saveValue(String key, Object value, Duration ttl) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, jsonValue, ttl);
            System.out.println("Saved key to Redis: " + key);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to save value to Redis: " + e.getMessage());
        }
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    public <T> T getValue(String key, TypeReference<T> valueType) {
        try {
            String cachedJson = (String) redisTemplate.opsForValue().get(key);
            System.out.println("Fetched key from Redis: " + cachedJson);
            if (cachedJson != null) {
                T object = objectMapper.readValue(cachedJson, valueType);
                System.out.println("Parsed object from Redis: " + object);
                return object;
            }
            return null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to get value from Redis: " + e.getMessage());
        }
    }
}
