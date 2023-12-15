package com.example.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RankingService {

    @Autowired
    StringRedisTemplate redisTemplate;

    private static final String LEADER_BOARD_KEY = "leaderBoard";

    // 데이터 세팅
    public boolean setUserScore(String userId, int score) {
        // Sorted-Set 을 위한 operation
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(LEADER_BOARD_KEY, userId, score);

        return true;
    }

    // 특정 유저의 랭킹 조회
    public Long getUserRanking(String userId) {
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        // 내림차순 기반 랭크 조회
        return zSetOps.reverseRank(LEADER_BOARD_KEY, userId) + 1;
    }

    // 범위 기반 조회
    public List<String> getTopRank(int limit) {
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        // 내림차순으로 범위 기반 조회
        Set<String> rangeSet = zSetOps.reverseRange(LEADER_BOARD_KEY, 0, limit - 1);

        return new ArrayList<>(rangeSet);
    }
}
