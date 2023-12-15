package com.example.redis;

import com.example.redis.service.RankingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class PerformanceTest {

    @Autowired
    RankingService rankingService;

    @Test
    void inMemorySortPerformance() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            int score = (int) (Math.random() * 1000000); // 0 ~ 999999 까지의 랜덤
            list.add(score);
        }

        Instant before = Instant.now();
        // 정렬 > 시간 복잡도 : O(NlogN)
        Collections.sort(list);
        Duration elapsed = Duration.between(before, Instant.now());
        System.out.println((elapsed.getNano() / 1000000) + " ms");
    }

    @Test
    void insertScore() {
        for (int i = 0; i < 1000000; i++) {
            int score = (int) (Math.random() * 1000000); // 0 ~ 999999 까지의 랜덤
            String userId = "user_" + i;
            rankingService.setUserScore(userId, score);
        }
    }

    @Test
    void getRanks() {
        // warmup
        rankingService.getTopRank(1);

        // 특정 유저 랭킹 조회
        Instant before = Instant.now();
        Long userRank = rankingService.getUserRanking("user_100");
        Duration elapsed = Duration.between(before, Instant.now());
        System.out.println(String.format("Rank(%d) - Took %d ms", userRank, elapsed.getNano() / 1000000));

        // 탑 랭킹 조회
        before = Instant.now();
        List<String> topRankers = rankingService.getTopRank(10);
        elapsed = Duration.between(before, Instant.now());
        System.out.println(String.format("Range - Took %d ms", elapsed.getNano() / 1000000));
    }
}
