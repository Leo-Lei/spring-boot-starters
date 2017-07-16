package com.leibangzhu.sample.disconf.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.stereotype.Service;

@Service
@DisconfFile(filename = "ForgetLockRule.properties")
public class FooConfig {

    private Integer score;

    @DisconfFileItem(name = "score",associateField = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
