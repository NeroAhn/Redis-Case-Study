package com.example.redis.service;

import com.example.redis.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private ExternalApiService externalApiService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public UserProfile getUserProfile(String userId) {
        String userName = null;

        // Cache 직접 설정
        // Cache-Aside 방식 : 캐시를 확인 후 없다면 DB 에서 읽어와 캐시에 적용
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cacheName = ops.get("nameKey:" + userId);
        // 캐시에 데이터가 있다면 그대로 사용
        if (cacheName != null) {
            userName = cacheName;
        } else {
            // 캐시에 데이터가 없다면 DB 조회 후 캐시 세팅
            userName = externalApiService.getUserName(userId);
            ops.set("nameKey:" + userId, userName, 5, TimeUnit.SECONDS);
        }

        int userAge = externalApiService.getUserAge(userId);
        return new UserProfile(userName, userAge);
    }
}
