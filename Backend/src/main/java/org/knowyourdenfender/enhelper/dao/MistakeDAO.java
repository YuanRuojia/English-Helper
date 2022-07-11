package org.knowyourdenfender.enhelper.dao;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("user_mistake_table")
public class MistakeDAO {
    Integer userId;
    String englishWords;
    Integer mistakeCount;
    Date changeTime;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEnglishWords() {
        return englishWords;
    }

    public void setEnglishWords(String englishWords) {
        this.englishWords = englishWords;
    }

    public Integer getMistakeCount() {
        return mistakeCount;
    }

    public void setMistakeCount(Integer mistakeCount) {
        this.mistakeCount = mistakeCount;
    }

    public Date getChangeTime() { return changeTime; }

    public void setChangeTime(Date changeTime) { this.changeTime = changeTime; }
}
