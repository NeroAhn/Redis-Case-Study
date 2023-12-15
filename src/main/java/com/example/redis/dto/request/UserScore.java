package com.example.redis.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserScore {

    private String userId;
    private int score;
}
