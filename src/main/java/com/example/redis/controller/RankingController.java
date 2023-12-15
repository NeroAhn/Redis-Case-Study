package com.example.redis.controller;

import com.example.redis.dto.request.UserScore;
import com.example.redis.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RankingController {

    @Autowired
    RankingService rankingService;

    @PostMapping("/score")
    public boolean setScore(@RequestBody UserScore userScore) {
        return rankingService.setUserScore(userScore.getUserId(), userScore.getScore());
    }

    @GetMapping("/user/{userId}/rank")
    public Long getUserRank(@PathVariable String userId) {
        return rankingService.getUserRanking(userId);
    }

    @GetMapping("/ranks/top")
    public List<String> getTopRanks() {
        return rankingService.getTopRank(3);
    }
}
