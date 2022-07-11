package org.knowyourdenfender.enhelper.pojo;

public class CollectVO {
    Integer userId;  //用户id user_id
    String englishWords;  //单词内容 english_words

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
}
