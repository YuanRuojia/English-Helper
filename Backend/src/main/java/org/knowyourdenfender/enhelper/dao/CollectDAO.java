package org.knowyourdenfender.enhelper.dao;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("user_collect_table")
public class CollectDAO {
    Integer userId;  //用户id user_id
    String englishWords;  //单词内容 english_words
    Date changeTime;

    //各个get(),set()

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWord() {
        return englishWords;
    }

    public void setWord(String word) {
        this.englishWords = word;
    }

    public String getEnglishWords() {
        return englishWords;
    }

    public void setEnglishWords(String englishWords) {
        this.englishWords = englishWords;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
}
