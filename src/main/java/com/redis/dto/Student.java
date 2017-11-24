package com.redis.dto;

import com.redis.annotation.Id;

/**
 * Created by enHui.Chen on 2017/11/21 0021.
 */
public class Student extends BaseDto {
    @Id
    private String stuId;
    private String score;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
